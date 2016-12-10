package com.example.trungnguyen.mymusicplayer.fragments;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.trungnguyen.mymusicplayer.Playlist;
import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.adapters.SearchingListAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * Created by Trung Nguyen on 11/29/2016.
 */
public class SearchingFragment extends Fragment {
    ListView lvSearchResult;
    TextView txt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mReturnView = LayoutInflater.from(getActivity()).inflate(R.layout.searching_fragment, container, false);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(),
                FragmentPagerItems.with(getActivity())
                        .add("TRACKS", SearchTrackFragment.class)
                        .add("ARTISTS", SearchArtistFragment.class)
                        .create());
        ViewPager viewPager = (ViewPager) mReturnView.findViewById(R.id.viewpager3);
        viewPager.setAdapter(adapter);
        SmartTabLayout viewPagerTab = (SmartTabLayout) mReturnView.findViewById(R.id.viewpagertab3);
        viewPagerTab.setViewPager(viewPager);


        return mReturnView;
    }
}
