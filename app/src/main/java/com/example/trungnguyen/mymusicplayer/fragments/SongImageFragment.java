package com.example.trungnguyen.mymusicplayer.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.helpers.DrawCircleImage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
        String link = getArguments().getString("COVER");
        View mReturnView = LayoutInflater.from(getActivity()).inflate(R.layout.song_img, container, false);
        imgSongs = (ImageView) mReturnView.findViewById(R.id.imgSong);
        new DownloadTask().execute(link);
        return mReturnView;
    }

    public class DownloadTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                String link = strings[0];
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(link).getContent());
                return bitmap;
            } catch (Exception e) {
                Log.e("ERROR", e.toString());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null)
                imgSongs.setImageBitmap(DrawCircleImage.getCroppedBitmap(bitmap, 800));
            else {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cylinders_chris_zabriskie);
                imgSongs.setImageBitmap(DrawCircleImage.getCroppedBitmap(bitmap, 800));
            }
        }
    }
}