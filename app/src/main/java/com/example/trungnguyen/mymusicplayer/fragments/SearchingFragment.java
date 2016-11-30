package com.example.trungnguyen.mymusicplayer.fragments;

import android.app.SearchManager;
import android.content.Intent;
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
 * Created by Trung Nguyen on 11/29/2016.
 */
public class SearchingFragment extends Fragment {
    ListView lvSearchResult;
    TextView txt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.searching_fragment, container, false);
        lvSearchResult = (ListView) mView.findViewById(R.id.lvSearchResults);
        txt = (TextView) mView.findViewById(R.id.txtTrackSuggestion);
        SearchingListAdapter adapter = new SearchingListAdapter(
                getActivity(), R.layout.top_track_item, Playlist.searchingTracks);
        lvSearchResult.setAdapter(adapter);
//        Intent intent = getActivity().getIntent();
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            SearchingListAdapter adapter = new SearchingListAdapter(
//                    getActivity(), R.layout.top_track_item, Playlist.searchingTracks);
//            lvSearchResult.setAdapter(adapter);
//
//        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
//            String uri = intent.getDataString();
//            StringBuilder stringBuilder = new StringBuilder(uri);
//            stringBuilder.deleteCharAt(0);
//            int index = Integer.parseInt(stringBuilder.toString());
//            txt.setText("Suggestion: " + Playlist.searchingTracks.get(index).getTopTrackName());
//        }
        return mView;
    }
}
