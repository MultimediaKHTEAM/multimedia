package com.example.trungnguyen.mymusicplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trungnguyen.mymusicplayer.Playlist;
import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.adapters.PlaylistAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * Created by Trung Nguyen on 11/21/2016.
 */
public class SongListFragment extends Fragment {
    private static final String TAG = SongListFragment.class.getSimpleName();

    public interface OnSongItemSelectedInterface {
        void onListSongItemSelected(int index, View v);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "SongListFragment onCreateView");
        View mReturnView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_song_list, container, false);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(),
                FragmentPagerItems.with(getActivity())
                        .add("TRACKS", TrackListFragment.class)
                        .add("ALBUMS", LyrisFragment.class)
                        .add("ARTISTS", LyrisFragment.class)
                        .add("FAVORITE", LyrisFragment.class)
                        .create());
        Log.d(TAG, getArguments().getInt(LauchingFragment.PAGE_POSiTION) + "");
        ViewPager viewPager = (ViewPager) mReturnView.findViewById(R.id.viewpager1);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(getArguments().getInt(LauchingFragment.PAGE_POSiTION));
        SmartTabLayout viewPagerTab = (SmartTabLayout) mReturnView.findViewById(R.id.viewpagertab1);
        viewPagerTab.setViewPager(viewPager);

        return mReturnView;
    }
}
