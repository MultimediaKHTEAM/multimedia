package com.example.trungnguyen.mymusicplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trungnguyen.mymusicplayer.MainActivity;
import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.adapters.MainMenuAdapter;

import java.util.ArrayList;

/**
 * Created by Trung Nguyen on 11/24/2016.
 */
public class LauchingFragment extends Fragment {
    private static final String TAG = LauchingFragment.class.getSimpleName();
    public static final String PAGE_POSiTION = "page_position";
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
                Bundle bundle = new Bundle();
                bundle.putInt(PAGE_POSiTION, pos);
                SongListFragment songListFragment = new SongListFragment();
                songListFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.placeHolder, songListFragment, MainActivity.FRAGMENT_SONG_LIST);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return mView;
    }
}
