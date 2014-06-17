package com.example.imagesearch.imagesearch;

import java.io.Serializable;

/**
 * Created by yzhang29 on 6/16/14.
 */
public class ImageSetting implements Serializable{
    private String color;
    private String size;
    private String type;
    private String site;

    public ImageSetting(String color, String size, String type, String site) {
        this.color = color;
        this.size = size;
        this.type = type;
        this.site = site;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getSite() {
        return site;
    }
}
