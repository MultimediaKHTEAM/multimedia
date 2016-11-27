package com.example.trungnguyen.mymusicplayer.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.trungnguyen.mymusicplayer.fragments.TopTracksFragment;

/**
 * Created by Trung Nguyen on 11/27/2016.
 */
public class LastFMWebAPITask extends AsyncTask<String, Integer, String> {
    ProgressDialog progressDialog;
    TopTracksFragment topTracksFragment;

    public LastFMWebAPITask(TopTracksFragment fragment) {
        super();
        topTracksFragment = fragment;
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(topTracksFragment.getActivity(), "Search", "Looking for Top Tracks", true, false);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
