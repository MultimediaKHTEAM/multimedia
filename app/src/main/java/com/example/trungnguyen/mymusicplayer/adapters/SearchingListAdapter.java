package com.example.trungnguyen.mymusicplayer.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trungnguyen.mymusicplayer.Playlist;
import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.models.Song;
import com.example.trungnguyen.mymusicplayer.models.TopTracks;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung Nguyen on 11/27/2016.
 */
public class SearchingListAdapter extends ArrayAdapter<TopTracks> {
    List<TopTracks> mTopTrack;
    Context mContext;

    public SearchingListAdapter(Context context, @LayoutRes int resource, @NonNull List<TopTracks> objects) {
        super(context, resource, objects);
        mContext = context;
        mTopTrack = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchingHolder holder;
        if (convertView == null) {
            holder = new SearchingHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.top_track_item, parent, false);
            holder.avarta = (ImageView) convertView.findViewById(R.id.avatar);
            holder.title = (TextView) convertView.findViewById(R.id.tvTopTrackTitle);
            holder.artist = (TextView) convertView.findViewById(R.id.tvTopTrackArtist);
            holder.btPlay = (ImageView) convertView.findViewById(R.id.playButton);
            convertView.setTag(holder);
        } else {
            holder = (SearchingHolder) convertView.getTag();
        }
        holder.title.setText(mTopTrack.get(position).getTopTrackName());
        holder.artist.setText(mTopTrack.get(position).getArtist());
        Picasso.with(mContext).load(mTopTrack.
                get(position).getImageUrl()).into(holder.avarta);
        return convertView;
    }

    public class SearchingHolder {
        ImageView avarta;
        TextView title, artist;
        ImageView btPlay;
    }
}
