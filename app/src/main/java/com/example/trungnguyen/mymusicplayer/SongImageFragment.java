package com.example.trungnguyen.mymusicplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Trung Nguyen on 11/18/2016.
 */
public class SongImageFragment extends Fragment {
    private static final String TAG = SongImageFragment.class.getSimpleName();
    public static ImageView imgSongs;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "CALL onCreateView SongImgFragment");
        View mReturnView = LayoutInflater.from(getActivity()).inflate(R.layout.song_img, container, false);
        imgSongs = (ImageView) mReturnView.findViewById(R.id.imgSong);
        return mReturnView;
    }
}
