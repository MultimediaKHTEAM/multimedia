package com.example.trungnguyen.mymusicplayer.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Trung Nguyen on 11/27/2016.
 */
public class TopTracks implements Parcelable {
    private String name;
    private String artist;
    private String imageUrl;
    private String trackUrl;
    private boolean isLike;

    public TopTracks(String trackUrl, String name, String artist, String imageUrl, boolean like) {
        this.name = name;
        this.artist = artist;
        this.imageUrl = imageUrl;
        this.trackUrl = trackUrl;
        this.isLike = like;
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

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    @Override
    public int describeContents() {
        return 0; // ignore
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(artist);
        dest.writeString(imageUrl);
        dest.writeString(trackUrl);
    }

    private TopTracks(Parcel in) {
        name = in.readString();
        artist = in.readString();
        imageUrl = in.readString();
        trackUrl = in.readString();

    }

    public static final Parcelable.Creator<TopTracks> CREATOR = new Parcelable.Creator<TopTracks>() {
        @Override
        public TopTracks createFromParcel(Parcel source) {
            return new TopTracks(source);
        }

        @Override
        public TopTracks[] newArray(int size) {
            return new TopTracks[size];
        }
    };
}
