package com.example.trungnguyen.mymusicplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.models.Artist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung Nguyen on 12/2/2016.
 */
public class SearchingArtisAdapter extends ArrayAdapter<Artist> {
    List<Artist> artists;
    Context mContext;

    public SearchingArtisAdapter(Context context, int resource, List<Artist> objects) {
        super(context, resource, objects);
        mContext = context;
        artists = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchingHolder holder;
        if (convertView == null) {
            holder = new SearchingHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist_item, parent, false);
            holder.avarta = (ImageView) convertView.findViewById(R.id.imgArtist);
            holder.name = (TextView) convertView.findViewById(R.id.tvName);
            holder.listener = (TextView) convertView.findViewById(R.id.tvListner);
            holder.btDetail = (ImageView) convertView.findViewById(R.id.btArtistDetail);
            convertView.setTag(holder);
        } else {
            holder = (SearchingHolder) convertView.getTag();
        }
        holder.name.setText(artists.get(position).getName());
        holder.listener.setText(artists.get(position).getListener());
        if (!artists.get(position).getImageUrl().isEmpty()) {
            Picasso.with(mContext).load(artists.
                    get(position).getImageUrl()).into(holder.avarta);
        }
        return convertView;
    }

    public class SearchingHolder {
        ImageView avarta;
        TextView name, listener;
        ImageView btDetail;
    }
}
