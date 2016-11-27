package com.example.trungnguyen.mymusicplayer.helpers;

/**
 * Created by Trung Nguyen on 11/27/2016.
 */
public class LastFMHelper {
    private String mCountry;
    public static final String LastFMTopTrackAPIUrlCountry = "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=spain&api_key=eb45a5debb542128737f9c10028ddbaf&format=json";
    // The world chart
    public static final String LastFMTopTrackAPIUrlWorld = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=eb45a5debb542128737f9c10028ddbaf&format=json";
    public LastFMHelper(String country){
        mCountry = country;
    }

    public String getUrl(){
        String url = LastFMTopTrackAPIUrlCountry + "&metro=" + mCountry;
        return url;
    }
}

