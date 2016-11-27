package com.example.trungnguyen.mymusicplayer.models;

/**
 * Created by Trung Nguyen on 11/27/2016.
 */
public class TopTracks {
    private String name;
    private String artist;
    private String imageUrl;
    private String trackUrl;

    public TopTracks(String name, String artist, String imageUrl, String trackUrl) {
        this.name = name;
        this.artist = artist;
        this.imageUrl = imageUrl;
        this.trackUrl = trackUrl;
    }

    public String getTopTrackName() {
        return name;
    }

    public void setTopTrackName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTrackUrl() {
        return trackUrl;
    }

    public void setTrackUrl(String trackUrl) {
        this.trackUrl = trackUrl;
    }


}
