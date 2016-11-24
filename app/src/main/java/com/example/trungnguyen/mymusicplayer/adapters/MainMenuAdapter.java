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

import com.example.trungnguyen.mymusicplayer.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung Nguyen on 11/24/2016.
 */
public class MainMenuAdapter extends ArrayAdapter {
    ArrayList<String> arrayList;

    public MainMenuAdapter(Context context, @LayoutRes int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        arrayList = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_menu, viewGroup, false);
        }
        ImageView imgMenu = (ImageView) view.findViewById(R.id.imgMenu);
        switch (arrayList.get(position)) {
            case "Songs":
                imgMenu.setImageResource(R.drawable.music_circle_white);
                break;
            case "Albums":
                imgMenu.setImageResource(R.drawable.album_white);
                break;
            case "Artists":
                imgMenu.setImageResource(R.drawable.account_white);
                break;
            case "Favorites":
                imgMenu.setImageResource(R.drawable.heart_white);
        }
        TextView tvMenuTitle = (TextView) view.findViewById(R.id.tvMenuTitleItem);
        tvMenuTitle.setText(arrayList.get(position));
        TextView tvAccount = (TextView) view.findViewById(R.id.tvMenuAcountItem);
        tvAccount.setText("100");
        return view;
    }
}
