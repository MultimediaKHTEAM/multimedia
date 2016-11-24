package com.example.trungnguyen.mymusicplayer.managers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by Trung Nguyen on 11/23/2016.
 */
public class LastSongPreference {
    private static final String LAST_PLAYING_SONG_URL = "last_playing_song_url";
    private static final String LAST_PLAYING_SONG_TITLE = "last_playing_song_title";
    private static final String LAST_PLAYING_SONG_ARTICS = "last_playing_song_artics";
    private static final String LAST_PLAYING_SONG_REPEATCOUNT = "last_playing_song_repeatcount";

    public static SharedPreferences getLastSongPreference(Context context) {
        return context.getSharedPreferences("MyMusicPlayer", Activity.MODE_PRIVATE);
    }

    public static void saveLastSongPlayingUrl(Context context, String url) {
        SharedPreferences.Editor editor = getLastSongPreference(context).edit();
        String lastSongUrl = url;
        editor.putString(LAST_PLAYING_SONG_URL, lastSongUrl);
        editor.commit();
    }

    public static String getLastSongPlayingUrl(Context context) {
        SharedPreferences sharedPreferences = getLastSongPreference(context);
        String lastSongUrl = sharedPreferences.getString(LAST_PLAYING_SONG_URL, "");
        return lastSongUrl;
    }

    public static void saveLastSongPlayingTitle(Context context, String title) {
        SharedPreferences.Editor editor = getLastSongPreference(context).edit();
        String lastSongTitle = title;
        editor.putString(LAST_PLAYING_SONG_TITLE, lastSongTitle);
        editor.commit();
    }

    public static String getLastSongPlayingTitle(Context context) {
        SharedPreferences sharedPerferences = getLastSongPreference(context);
        String lastSongTitle = sharedPerferences.getString(LAST_PLAYING_SONG_TITLE, "");
        return lastSongTitle;
    }

    public static void saveLastSongPlayingArtics(Context context, String url) {
        SharedPreferences.Editor editor = getLastSongPreference(context).edit();
        String lastSongArtist = url;
        editor.putString(LAST_PLAYING_SONG_ARTICS, lastSongArtist);
        editor.commit();
    }

    public static String getLastSongPlayingArtics(Context context) {
        SharedPreferences sharedPreferences = getLastSongPreference(context);
        String lastSongArtist = sharedPreferences.getString(LAST_PLAYING_SONG_ARTICS, "");
        return lastSongArtist;
    }

    public static void saveLastRepeatCount(Context context, int last) {
        SharedPreferences.Editor editor = getLastSongPreference(context).edit();
        int lastRepeatCount = last;
        editor.putInt(LAST_PLAYING_SONG_REPEATCOUNT, lastRepeatCount);
        editor.commit();
    }

    public static int getLastRepeatCount(Context context) {
        SharedPreferences sharedPreferences = getLastSongPreference(context);
        int lastRepeatCount = sharedPreferences.getInt(LAST_PLAYING_SONG_REPEATCOUNT, 0);
        return lastRepeatCount;
    }
}
