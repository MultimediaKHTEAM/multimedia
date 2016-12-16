package com.example.trungnguyen.mymusicplayer.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trungnguyen.mymusicplayer.MainActivity;
import com.example.trungnguyen.mymusicplayer.Playlist;
import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.fragments.NowPlayingFragment;
import com.example.trungnguyen.mymusicplayer.fragments.SearchingFragment;
import com.example.trungnguyen.mymusicplayer.models.AllSong;
import com.example.trungnguyen.mymusicplayer.models.Song;
import com.example.trungnguyen.mymusicplayer.models.TopTracks;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Trung Nguyen on 11/27/2016.
 */
public class SearchingListAdapter extends ArrayAdapter<AllSong> {
    private static final String TAG = SearchingListAdapter.class.getSimpleName();
    List<AllSong> mSongs;
    Activity mContext;
    List<TopTracks> tracks;
    int mPos;
    SearchingHolder holder;

    public SearchingListAdapter(Context context,
                                @LayoutRes int resource, @NonNull List<AllSong>
                                        objects) {
        super(context, resource, objects);
        mSongs = objects;
        mContext = (Activity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new SearchingHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.top_track_item, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.tvTopTrackTitle);
            holder.btPlay = (ImageView) convertView.findViewById(R.id.playButton);
            convertView.setTag(holder);
        } else {
            holder = (SearchingHolder) convertView.getTag();
        }
        holder.title.setText(mSongs.get(position).getTitle());
//        if (mTopTrack.get(position).getImageUrl() != null) {
//            Picasso.with(mContext).load(mTopTrack.
//                    get(position).getImageUrl()).into(holder.avarta);
//        }
        mPos = position;
        return convertView;
    }

    public class SearchingHolder {
        TextView title;
        ImageView btPlay;
    }
}
