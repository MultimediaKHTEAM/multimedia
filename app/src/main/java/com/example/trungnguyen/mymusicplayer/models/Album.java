package com.example.trungnguyen.mymusicplayer.models;

/**
 * Created by Trung Nguyen on 12/2/2016.
 */
public class Album {
    String name;
    String listener;
    String url;
    String imageUrl;

    public Album(String imageUrl, String url, String listener, String name) {
        this.imageUrl = imageUrl;
        this.url = url;
        this.listener = listener;
        this.name = name;
    }

    public String getListener() {
        return listener;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setListener(String listener) {
        this.listener = listener;
    }
}
