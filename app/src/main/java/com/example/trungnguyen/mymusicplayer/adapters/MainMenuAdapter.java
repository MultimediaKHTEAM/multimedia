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
        MenuViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_menu, viewGroup, false);
            holder = new MenuViewHolder();
            holder.tvMenuTitle = (TextView) view.findViewById(R.id.tvMenuTitleItem);
            holder.imgMenu = (ImageView) view.findViewById(R.id.imgMenu);
            holder.tvAccount = (TextView) view.findViewById(R.id.tvMenuAcountItem);
            view.setTag(holder);
        } else
            holder = (MenuViewHolder) view.getTag();
        switch (arrayList.get(position)) {
            case "Songs":
                holder.imgMenu.setImageResource(R.drawable.music_circle_white);
                break;
            case "Albums":
                holder.imgMenu.setImageResource(R.drawable.album_white);
                break;
            case "Artists":
                holder.imgMenu.setImageResource(R.drawable.account_white);
                break;
            case "Favorites":
                holder.imgMenu.setImageResource(R.drawable.heart_white);
        }
        holder.tvMenuTitle.setText(arrayList.get(position));
        holder.tvAccount.setText("100");
        return view;
    }

    public class MenuViewHolder {
        public ImageView imgMenu;
        public TextView tvMenuTitle, tvAccount;
    }
}
