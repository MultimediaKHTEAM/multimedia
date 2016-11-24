package com.example.trungnguyen.mymusicplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.adapters.MainMenuAdapter;

import java.util.ArrayList;

/**
 * Created by Trung Nguyen on 11/24/2016.
 */
public class LauchingFragment extends Fragment {
    ArrayList<String> arrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();
        arrayList.add("Songs");
        arrayList.add("Albums");
        arrayList.add("Artists");
        arrayList.add("Favorites");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.lauching_fragment, container, false);
        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(getActivity(), R.layout.item_menu, arrayList);
        ListView lvLauchingMenu = (ListView) mView.findViewById(R.id.lvLauchingMenu);
        lvLauchingMenu.setAdapter(mainMenuAdapter);
        lvLauchingMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                //jump to this fragment
            }
        });
        return mView;
    }
}
