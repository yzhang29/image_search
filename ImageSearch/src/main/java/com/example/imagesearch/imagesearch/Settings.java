package com.example.imagesearch.imagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class Settings extends Activity {
    Spinner spColor;
    Spinner spType;
    Spinner spSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        spColor = (Spinner) findViewById(R.id.spColor);
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(this,
                R.array.imgcolor, android.R.layout.simple_spinner_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColor.setAdapter(colorAdapter);
        spType = (Spinner) findViewById(R.id.spType);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.imgtype, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(typeAdapter);
        spSize = (Spinner) findViewById(R.id.spSize);
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.imgsz, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSize.setAdapter(sizeAdapter);
    }


    @Override
    public void onBackPressed() {
        EditText site = (EditText) findViewById(R.id.tvSite);
        ImageSetting imageSetting = new ImageSetting(spColor.getSelectedItem().toString(), spSize.getSelectedItem().toString(), spType.getSelectedItem().toString(), site.getText().toString() );
        Intent data = new Intent();
        data.putExtra("settings", imageSetting);
        setResult(RESULT_OK, data);
        Toast.makeText(this, "Settings have been saved.", Toast.LENGTH_SHORT).show();
        finish();
    }

}
