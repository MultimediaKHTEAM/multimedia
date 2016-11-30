package com.example.trungnguyen.mymusicplayer.providers;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.trungnguyen.mymusicplayer.Playlist;
import com.example.trungnguyen.mymusicplayer.helpers.LastFMHelper;
import com.example.trungnguyen.mymusicplayer.models.TopTracks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Trung Nguyen on 11/28/2016.
 */
public class SearchingTrackProvider extends ContentProvider {

    private static final String TAG = SearchingTrackProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(
                new String[]{
                        BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
                }
        );
        String enterString = uri.getLastPathSegment().toUpperCase();
        String query;
        String enterStringWithoutPlus;
        if (enterString.contains(" ")) {
            enterString = enterString.trim();
            while (enterString.indexOf("  ") != -1)
                enterString = enterString.replaceAll("  ", " ");
            enterStringWithoutPlus = enterString;
            while (enterString.indexOf(" ") != -1)
                enterString = enterString.replaceAll(" ", "+");
            query = enterString;
        } else {
            query = enterString;
            enterStringWithoutPlus = query;
        }
        if (query.length() > 1) {
            LastFMHelper helper = new LastFMHelper(query);
            String completeUrl = helper.getSearchingUrl();
            Log.d(TAG, query);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(completeUrl).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "You have error about this action");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            Playlist.searchingTracks = new ArrayList<>();
                            String jsonString = response.body().string();
                            JSONObject responseJson = new JSONObject(jsonString);
                            JSONObject resultsObj = responseJson.getJSONObject("results");
                            JSONObject trackmatchesObj = resultsObj.getJSONObject("trackmatches");
                            JSONArray trackArr = trackmatchesObj.getJSONArray("track");
                            for (int i = 0; i < trackArr.length(); i++) {
                                JSONObject track = trackArr.getJSONObject(i);
                                String trackName = track.getString("name");
                                String trackArtis = track.getString("artist");
                                String trackUrl = track.getString("url");
                                String imageUrl = null;
                                JSONArray imageUrls = track.getJSONArray("image");
                                for (int j = 0; j < imageUrls.length(); j++) {
                                    JSONObject imageObj = imageUrls.getJSONObject(j);
                                    if (imageObj.getString("size").equals("small")) {
                                        imageUrl = imageObj.getString("#text");
                                        break;
                                    }
                                }
                                Playlist.searchingTracks.add(new TopTracks(trackName, trackArtis, imageUrl, trackUrl));
                            }
                        } catch (JSONException e) {
                            Log.d(TAG, "JSonException Error");
                        }
                    }
                }
            });
            if (Playlist.searchingTracks.size() > 0) {
                for (int i = 0; i < Playlist.searchingTracks.size() && cursor.getCount() < 50; i++) {
                    String trackInfo = Playlist.searchingTracks.get(i).getTopTrackName() + " - " + Playlist.searchingTracks.get(i).getArtist();
                    if (trackInfo.toUpperCase().contains(enterStringWithoutPlus)) {
                        cursor.addRow(new Object[]{i, trackInfo, i});
                    }
                }
            }
            Log.d(TAG, " CALL QUERY IN PROVIDER");
        } else {
            Log.d(TAG, "Entering more input");
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}