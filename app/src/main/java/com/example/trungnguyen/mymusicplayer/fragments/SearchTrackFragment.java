package com.example.trungnguyen.mymusicplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.trungnguyen.mymusicplayer.Playlist;
import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.adapters.SearchingListAdapter;

/**
 * Created by Trung Nguyen on 12/2/2016.
 */
public class SearchTrackFragment extends Fragment {
    ListView lvSearchTrackResult;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.search_track_fragment, container, false);
        lvSearchTrackResult = (ListView) mView.findViewById(R.id.lvSearchTrackResult);
        SearchingListAdapter adapter = new SearchingListAdapter(
                getActivity(), R.layout.top_track_item, Playlist.searchingTracks);
        lvSearchTrackResult.setAdapter(adapter);
        return mView;
    }
}
