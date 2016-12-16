package com.example.trungnguyen.mymusicplayer.managers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.trungnguyen.mymusicplayer.models.Song;
import com.example.trungnguyen.mymusicplayer.models.TopTracks;
import com.google.gson.Gson;

/**
 * Created by Trung Nguyen on 11/23/2016.
 */
public class LastSongPreference {
    private static final String LAST_PLAYING_SONG_INDEX = "last_playing_song_index";
//    private static final String LAST_PLAYING_SONG_TITLE = "last_playing_song_title";
//    private static final String LAST_PLAYING_SONG_ARTICS = "last_playing_song_artics";
    private static final String LAST_PLAYING_SONG_REPEATCOUNT = "last_playing_song_repeatcount";
    public static TopTracks playingSong;
    public static int mIndex;

    public static SharedPreferences getLastSongPreference(Context context) {
        return context.getSharedPreferences("MyMusicPlayer", Activity.MODE_PRIVATE);
    }

    public static void saveLastSong(Context context, TopTracks mSong) {
        SharedPreferences.Editor editor = getLastSongPreference(context).edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(mSong);
        editor.putString("JSON_STRING", jsonString);
        editor.commit();
    }

    public static TopTracks getLastSong(Context context) {
        SharedPreferences mSharedPreferences = getLastSongPreference(context);
        String lastplaySong = mSharedPreferences.getString("JSON_STRING", "");
        Gson mGson = new Gson();
        playingSong = mGson.fromJson(lastplaySong, TopTracks.class);
        return playingSong;
    }

    public static void saveLastSongPlayingIndex(Context context, int mIndex) {
        SharedPreferences.Editor editor = getLastSongPreference(context).edit();
        editor.putInt(LAST_PLAYING_SONG_INDEX, mIndex);
        editor.commit();
    }

    public static int getLastSongPlayingIndex(Context context) {
        SharedPreferences sharedPreferences = getLastSongPreference(context);
        mIndex = sharedPreferences.getInt(LAST_PLAYING_SONG_INDEX, 0);
        return mIndex;
    }

//    public static void saveLastSongPlayingTitle(Context context, String title) {
//        SharedPreferences.Editor editor = getLastSongPreference(context).edit();
//        String lastSongTitle = title;
//        editor.putString(LAST_PLAYING_SONG_TITLE, lastSongTitle);
//        editor.commit();
//    }
//
//    public static String getLastSongPlayingTitle(Context context) {
//        SharedPreferences sharedPerferences = getLastSongPreference(context);
//        String lastSongTitle = sharedPerferences.getString(LAST_PLAYING_SONG_TITLE, "");
//        return lastSongTitle;
//    }
//
//    public static void saveLastSongPlayingArtics(Context context, String url) {
//        SharedPreferences.Editor editor = getLastSongPreference(context).edit();
//        String lastSongArtist = url;
//        editor.putString(LAST_PLAYING_SONG_ARTICS, lastSongArtist);
//        editor.commit();
//    }
//
//    public static String getLastSongPlayingArtics(Context context) {
//        SharedPreferences sharedPreferences = getLastSongPreference(context);
//        String lastSongArtist = sharedPreferences.getString(LAST_PLAYING_SONG_ARTICS, "");
//        return lastSongArtist;
//    }

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
