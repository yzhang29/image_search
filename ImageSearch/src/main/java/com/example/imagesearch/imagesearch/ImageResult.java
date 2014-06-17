package com.example.imagesearch.imagesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yzhang29 on 6/12/14.
 */
public class ImageResult {
    private String fullUrl;
    private String thumbUrl;

    public ImageResult(JSONObject json){
        try{
            this.fullUrl = json.getString("url");
            this.thumbUrl=json.getString("tbUrl");
        }catch (JSONException e){
            this.fullUrl=null;
            this.thumbUrl=null;

        }
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public static ArrayList<ImageResult> fromJsonArray(JSONArray array){
        ArrayList<ImageResult> results = new ArrayList<>();
        for(int x = 0 ; x<array.length(); x++){
            try{
                results.add(new ImageResult(array.getJSONObject(x)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }
}
