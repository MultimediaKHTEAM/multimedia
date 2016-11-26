package com.example.trungnguyen.mymusicplayer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.trungnguyen.mymusicplayer.adapters.PlaylistAdapter;
import com.example.trungnguyen.mymusicplayer.fragments.LauchingFragment;
import com.example.trungnguyen.mymusicplayer.fragments.NowPlayingFragment;
import com.example.trungnguyen.mymusicplayer.fragments.SongListFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.util.ArrayList;

import io.gresse.hugo.vumeterlibrary.VuMeterView;


public class MainActivity extends AppCompatActivity
        implements SongListFragment.OnSongItemSelectedInterface,
        NavigationView.OnNavigationItemSelectedListener {
    public static final String SONG_NAME = "song_name";
    public static final String LIST_POSITION = "list_pos";
    public static final String IS_FAVORITE = "is_favorite";
    public static final String KEY_SONG = "song";
    public static final String BROADCAST_SEEKBAR = "com.example.trungnguyen.mymusicplayer.sendseekbar";
    private static final String SAVING_MINISCREEN = "SAVING_MINISCREEN";
    public static final String FRAGMENT_NOW_PLAYING = "fragment_playing_song";
    private RelativeLayout rootActivityMainLayout;
    protected static SlidingUpPanelLayout mLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static ProgressDialog dialogDownload;
    public static TextView miniTitle, miniArtis;
    public static ImageView miniSongPic;
    private Toolbar toolbar;
    DrawerLayout drawer;
    //ArrayList<String> arrayList;
//    private ListView leftDrawer;
//    private PlaylistAdapter mAdapter;
//    private Button btDownload;
//    public static VuMeterView mVuMeterView;
    public static final String FRAGMENT_SONG_LIST = "fragment_song_list";
    public static final String LAUCHING_FRAGMENT = "lauching_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate - MainActivity");
        LauchingFragment savedFragment = (LauchingFragment) getSupportFragmentManager().findFragmentByTag(LAUCHING_FRAGMENT);
        if (savedFragment == null) {
            LauchingFragment lauchingFragment = new LauchingFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.placeHolder, lauchingFragment, LAUCHING_FRAGMENT);
            fragmentTransaction.commit();
        }
        NowPlayingFragment nowPlayingFragment1 = (NowPlayingFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_NOW_PLAYING);
        if (nowPlayingFragment1 == null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(MainActivity.SONG_NAME, null);
            NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
            nowPlayingFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.childPlaceHolder, nowPlayingFragment, MainActivity.FRAGMENT_NOW_PLAYING);
            fragmentTransaction.commit();
        }
        addControls();
//        arrayList = new ArrayList<>();
//        arrayList.add("Library");
//        arrayList.add("All Songs");
//        arrayList.add("Love");
//        LeftDrawerAdapter leftDrawerAdapter = new LeftDrawerAdapter(MainActivity.this, R.layout.item_left_drawer, arrayList);
//        leftDrawer.setAdapter(leftDrawerAdapter);
        mLayout.addPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
//                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, PanelState previousState, PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(PanelState.COLLAPSED);
            }
        });
    }

    public void getLocation() {
        // Implicit intent
        Intent intentLocation = new Intent(Intent.ACTION_VIEW);
        Uri geoLocation = Uri.parse("geo:0,0?q=10.869852, 106.803376(University of Information Technology)");
        intentLocation.setData(geoLocation);
        if (intentLocation.resolveActivity(getPackageManager()) == null) {// Checking for existence of connecting app
            // Handle exception
            // First parameter of Snack bar is Parent view where Snack bar will be displayed
            Snackbar.make(rootActivityMainLayout, "Cannot found ac Activity to handle", Snackbar.LENGTH_LONG).show();
            // We also use Toast notification
//            Toast.makeText(MainActivity.this, "Cannot found ac Activity to handle", Toast.LENGTH_LONG).show();
        } else
            startActivity(intentLocation);
    }

    private void addControls() {
//        btDownload = (Button) findViewById(R.id.btDownload);
//        dialogDownload = new ProgressDialog(MainActivity.this);
//        dialogDownload.setTitle("Notification");
//        dialogDownload.setMessage("DownLoading in progress, please wait for a minute..!!");
//        dialogDownload.setCanceledOnTouchOutside(false);
//        dialogDownload.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
        rootActivityMainLayout = (RelativeLayout) findViewById(R.id.rootActivityMainLayout);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        miniTitle = (TextView) findViewById(R.id.tvMiniTitle);
        miniArtis = (TextView) findViewById(R.id.tvMiniArtis);
        miniSongPic = (ImageView) findViewById(R.id.imgSongPic);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        leftDrawer = (ListView) findViewById(R.id.left_drawer);
//        mVuMeterView = (VuMeterView) findViewById(R.id.vumeter);
//        mVuMeterView.pause();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            case R.id.nowPlaying:
                Toast.makeText(MainActivity.this, "You selected share song menu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                Toast.makeText(MainActivity.this, "You selected share song menu", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setToFullScreen() {
        ViewGroup rootLayout = (ViewGroup) findViewById(R.id.sliding_layout);
        rootLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == PanelState.EXPANDED || mLayout.getPanelState() == PanelState.ANCHORED)) {
            mLayout.setPanelState(PanelState.COLLAPSED);
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onListSongItemSelected(int index, View v) {
        NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
        Animation effectOnclickAnimation = new AlphaAnimation(0.3f, 1.0f);
        effectOnclickAnimation.setDuration(500);
        v.startAnimation(effectOnclickAnimation);
        Log.d(TAG, "onItemClick");
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.SONG_NAME, Playlist.songs[index]);
        bundle.putInt("LIST_INDEX", index);
        Log.d(TAG, index + "");
        bundle.putInt(MainActivity.LIST_POSITION, index);
        nowPlayingFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.childPlaceHolder, nowPlayingFragment, MainActivity.FRAGMENT_NOW_PLAYING);
        fragmentTransaction.commit();    }

    @Override
    protected void onResume() {
        super.onResume();
        setToFullScreen();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
