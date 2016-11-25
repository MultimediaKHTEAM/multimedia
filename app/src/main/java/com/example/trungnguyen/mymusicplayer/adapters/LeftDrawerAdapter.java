package com.example.trungnguyen.mymusicplayer.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trungnguyen.mymusicplayer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung Nguyen on 11/24/2016.
 */
public class LeftDrawerAdapter extends ArrayAdapter {
    ArrayList<String> arrayList;

    public LeftDrawerAdapter(Context context, @LayoutRes int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        arrayList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_left_drawer, parent, false);
        }
        ImageView imgLeftDrawer = (ImageView) convertView.findViewById(R.id.imgLeftDrawer);
        switch (arrayList.get(position)) {
            case "Library":
                imgLeftDrawer.setImageResource(R.drawable.albums_light);
                break;
            case "All Songs":
                imgLeftDrawer.setImageResource(R.drawable.now_playing);
                break;
            case "Love":
                imgLeftDrawer.setImageResource(R.drawable.heart_white);
        }
        TextView tvLeft = (TextView) convertView.findViewById(R.id.tvLeftDrawerTitle);
        tvLeft.setText(arrayList.get(position));
        return convertView;
    }
}
