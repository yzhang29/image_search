package com.example.imagesearch.imagesearch;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity {
    EditText etQuery;
    GridView gvResults;
    Button btnSearch;
    ArrayList<ImageResult> imageResults = new ArrayList<>();
    ImageResultArrayAdapter imageAdapter;
    public static final int REQUEST_CODE = 20;
    ImageSetting settings = new ImageSetting("", "", "", "");
    EndlessScrollListener endlessScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
                Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                ImageResult imageResult = imageResults.get(position);
                i.putExtra("url", imageResult.getFullUrl());
                startActivity(i);
            }
        });
        endlessScrollListener = new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        };

        gvResults.setOnScrollListener(endlessScrollListener);

    }

    public void customLoadMoreDataFromApi(int offset) {
        String query = etQuery.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        String requestURL = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=" + (offset*8) + "&v=1.0&q=" + Uri.encode(query) + "&imgcolor=" + settings.getColor() + "&imgsz=" + settings.getSize() + "&imgtype=" + settings.getType() + "&as_sitesearch=" + settings.getSite();
        client.get( requestURL,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        JSONArray imageJsonResults = null;
                        try {
                            imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                            imageAdapter.addAll(ImageResult.fromJsonArray(imageJsonResults));
                            Log.d("DEBUG", imageResults.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

    }

    public void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
    }

    public void onImageSearch(View view) {
        endlessScrollListener.reset();
        String query = etQuery.getText().toString();
        String site = "";
        String settingsString = "";
        if (!settings.getSize().isEmpty())
            settingsString += settings.getSize();
        if (!settings.getColor().isEmpty())
            settingsString += ", " + settings.getColor();
        if (!settings.getType().isEmpty())
            settingsString += ", " + settings.getType();
        if (!settingsString.isEmpty())
            settingsString += " ";
        if (!settings.getSite().isEmpty())
            site = " on " + settings.getSite();
        Toast.makeText(this, "Searching " + settingsString + "images for " + query + site, Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        String requestURL = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=" + 0 + "&v=1.0&q=" + Uri.encode(query) + "&imgcolor=" + settings.getColor() + "&imgsz=" + settings.getSize() + "&imgtype=" + settings.getType() + "&as_sitesearch=" + settings.getSite();
        client.get( requestURL,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        JSONArray imageJsonResults = null;
                        try {
                            imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                            imageResults.clear();
                            imageAdapter.addAll(ImageResult.fromJsonArray(imageJsonResults));
                            Log.d("DEBUG", imageResults.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_settings, menu);
        return true;
    }

    public void onSettingsAction(MenuItem mi) {
        Intent i = new Intent(MainActivity.this, Settings.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            settings = (ImageSetting) data.getExtras().getSerializable("settings");
        }
    }

}

















