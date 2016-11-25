package com.example.trungnguyen.mymusicplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trungnguyen.mymusicplayer.R;

/**
 * Created by Trung Nguyen on 11/18/2016.
 */
public class LyrisFragment extends Fragment {
    TextView tvLyrics;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.lyrics_layout, container, false);
        tvLyrics = (TextView) mView.findViewById(R.id.tvLyrics);
        String lyrics = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(lyrics);
        }
        tvLyrics.setText(stringBuilder.toString());
        return mView;
    }
}
