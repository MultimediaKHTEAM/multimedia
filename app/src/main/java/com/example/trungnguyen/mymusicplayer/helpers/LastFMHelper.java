package com.example.trungnguyen.mymusicplayer.helpers;

/**
 * Created by Trung Nguyen on 11/27/2016.
 */
public class LastFMHelper {
    private String mString;
    public static final String LastFMTopTrackAPIUrlCountry = "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=spain&api_key=eb45a5debb542128737f9c10028ddbaf&format=json";
    // The world chart
    public static final String LastFMTopTrackAPIUrlWorld = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=eb45a5debb542128737f9c10028ddbaf&format=json";

    public static final String prefix = "http://ws.audioscrobbler.com/2.0/?method=track.search&track=";

    public static final String suffix = "&api_key=eb45a5debb542128737f9c10028ddbaf&format=json";

    public LastFMHelper(String string) {
        mString = string;
    }

    public String getTopTrackUrl() {
        String url = LastFMTopTrackAPIUrlCountry + "&metro=" + mString;
        return url;
    }

    public String getSearchingUrl() {
        String url =  prefix + mString + suffix;
        return url;
    }

}

