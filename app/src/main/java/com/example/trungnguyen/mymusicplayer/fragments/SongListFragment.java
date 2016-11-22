package com.example.trungnguyen.mymusicplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trungnguyen.mymusicplayer.Playlist;
import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.adapters.PlaylistAdapter;

/**
 * Created by Trung Nguyen on 11/21/2016.
 */
public class SongListFragment extends Fragment {
    public interface OnSongItemSelectedInterface {
        void onListSongItemSelected(int index, View v);
    }

    private PlaylistAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        OnSongItemSelectedInterface listener = (OnSongItemSelectedInterface) getActivity();
        View mReturnView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_song_list, container, false);
        RecyclerView recyclerView = (RecyclerView) mReturnView.findViewById(R.id.recyclerView);
        mAdapter = new PlaylistAdapter(getActivity(), Playlist.songs, listener);
        recyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setBackgroundResource(R.drawable.click_effet);
        return mReturnView;
    }
}
