package com.example.trungnguyen.mymusicplayer.fragments;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trungnguyen.mymusicplayer.MainActivity;
import com.example.trungnguyen.mymusicplayer.Playlist;
import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.adapters.TopTrackListAdapter;
import com.example.trungnguyen.mymusicplayer.helpers.LastFMHelper;
import com.example.trungnguyen.mymusicplayer.models.TopTracks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Trung Nguyen on 11/27/2016.
 */
public class TopTracksFragment extends Fragment {
    private static final String TAG = TopTracksFragment.class.getSimpleName();
    Button btnGetTrack;
    ListView lvTopTrack;
    Spinner spinnerCountry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.top_online_songs, container, false);
        addControls(mView);
        btnGetTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Playlist.topTracks = new ArrayList<>();
                TextView tvCountry = (TextView) spinnerCountry.getSelectedView();
                String country = tvCountry.getText().toString();
                LastFMHelper lastFMHelper = new LastFMHelper(country);
                String urlTopTrack = lastFMHelper.getTopTrackUrl();
                getTracks(urlTopTrack);
            }
        });

        return mView;
    }

    private void getJsonData(String JsonData) throws JSONException {
        JSONObject responseJson = new JSONObject(JsonData);
        JSONObject topTracks = responseJson.getJSONObject("tracks");
        JSONArray tracks = topTracks.getJSONArray("track");
        for (int i = 0; i < tracks.length(); i++) {
            JSONObject track = tracks.getJSONObject(i);
            String trackName = track.getString("name");
            String trackUrl = track.getString("url");
            JSONObject artistObject = track.getJSONObject("artist");
            String artist = artistObject.getString("name");
            String imageUrl = null;
            JSONArray imageUrls = track.getJSONArray("image");
            for (int j = 0; j < imageUrls.length(); j++) {
                JSONObject imageObj = imageUrls.getJSONObject(j);
                if (imageObj.getString("size").equals("small")) {
                    imageUrl = imageObj.getString("#text");
                    break;
                }
            }
            Playlist.topTracks.add(new TopTracks(trackName, artist, imageUrl, trackUrl));
        }
    }

    private void getTracks(String urlTopTrack) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(urlTopTrack).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getActivity(), "Error!!!! Getting Data from Server", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String JSonData = response.body().string();
                        getJsonData(JSonData);
                    } catch (JSONException e) {
                        Log.d(TAG, "Response error !!!!");
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TopTrackListAdapter
                                    topTrackListAdapter = new TopTrackListAdapter(getActivity(),
                                    R.layout.top_track_item,
                                    Playlist.topTracks);
                            lvTopTrack.setAdapter(topTrackListAdapter);
                        }
                    });
                }
            }
        });
    }

    private void addControls(View mView) {
        btnGetTrack = (Button) mView.findViewById(R.id.btnGetTrack);
        lvTopTrack = (ListView) mView.findViewById(R.id.lvTopTrack);
        spinnerCountry = (Spinner) mView.findViewById(R.id.spinnerCountry);
    }
}
