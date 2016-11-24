package com.example.trungnguyen.mymusicplayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.trungnguyen.mymusicplayer.adapters.PlaylistAdapter;
import com.example.trungnguyen.mymusicplayer.fragments.LauchingFragment;
import com.example.trungnguyen.mymusicplayer.fragments.SongListFragment;
import com.example.trungnguyen.mymusicplayer.models.Song;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import io.gresse.hugo.vumeterlibrary.VuMeterView;


public class MainActivity extends ActionBarActivity implements SongListFragment.OnSongItemSelectedInterface {
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
    private Button btDownload;
    public static VuMeterView mVuMeterView;
    public static ProgressDialog dialogDownload;
    private PlaylistAdapter mAdapter;
    public static TextView miniTitle, miniArtis;
    public static ImageView miniSongPic;
    public static final String FRAGMENT_SONG_LIST = "fragment_song_list";
    public static final String LAUCHING_FRAGMENT = "lauching_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate - MainActivity");
//        SongListFragment savedFragment = (SongListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_NOW_PLAYING);
//        if (savedFragment == null) {
//            SongListFragment songListFragment = new SongListFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.placeHolder, songListFragment, FRAGMENT_SONG_LIST);
//            fragmentTransaction.commit();
//        }
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
//        stopMediaBroadcastReceive = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intentStopMedia) {
//                Log.d(TAG, "CALL onReceive - MainActivity");
//                final boolean isStopped = intentStopMedia.getBooleanExtra(PlayerService.COPA_MESSAGE, false);
//                ChangeButton(isStopped);
//            }
//        };
//        intent = new Intent(this.BROADCAST_SEEKBAR);
//        updateUIFromService();
//        final DownloadThread downloadThread = new DownloadThread();
//        downloadThread.setName("Download Thread");
//        downloadThread.start();
//        btDownload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                downloadSongs();
////                getLocation();
//
//            }
//        });

//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mAdapter = new PlaylistAdapter(MainActivity.this, Playlist.songs);
//        recyclerView.setAdapter(mAdapter);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setBackgroundResource(R.drawable.click_effet);
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

    //    //get location
    private void getLocation() {
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

//    private void downloadSongs() {
//        dialogDownload.show();
//        // Send Messages to Handler for processing
//        for (Song song : Playlist.songs) {
//            Intent bindIntent = new Intent(MainActivity.this, DownloadIntentService.class);
//            bindIntent.putExtra(KEY_SONG, song);
//            startService(bindIntent);
//            Log.d(TAG, "startService() is called");
//        }
//    }

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
//        mVuMeterView = (VuMeterView) findViewById(R.id.vumeter);
//        mVuMeterView.pause();
        setToFullScreen();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 99 && resultCode == 100) {
//            boolean isFavorite = data.getBooleanExtra(MainActivity.IS_FAVORITE, false);
//            Playlist.songs[data.getIntExtra(MainActivity.LIST_POSITION, 0)].
//                    setIsFavorite(isFavorite);
//            mAdapter.notifyDataSetChanged();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
//        if (itemID == R.id.nowPlaying) {
//            getLocation();
//        } else if (itemID == R.id.action_hide) {
//            if (mLayout.getPanelState() == PanelState.COLLAPSED) {
//                mLayout.setPanelState(PanelState.HIDDEN);
//                item.setTitle("Open");
//            } else {
//                mLayout.setPanelState(PanelState.COLLAPSED);
//                item.setTitle("Hide");
//            }
//        } else if{
//
//        }
        switch (itemID) {
            case R.id.nowPlaying:
                getLocation();
                break;
            case R.id.action_hide:
                if (mLayout.getPanelState() == PanelState.COLLAPSED) {
                    mLayout.setPanelState(PanelState.HIDDEN);
                    item.setTitle("Open");
                } else {
                    mLayout.setPanelState(PanelState.COLLAPSED);
                    item.setTitle("Hide");
                }
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
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onListSongItemSelected(int index, View v) {
        Animation effectOnclickAnimation = new AlphaAnimation(0.3f, 1.0f);
        effectOnclickAnimation.setDuration(500);
        v.startAnimation(effectOnclickAnimation);
//        NowPlayingFragment savedNowPlayingFragment = (NowPlayingFragment) getSupportFragmentManager().findFragmentByTag(MainActivity.FRAGMENT_NOW_PLAYING);
//        if (savedNowPlayingFragment == null) {
//            NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(MainActivity.SONG_NAME, Playlist.songs[index]);
//            bundle.putInt(MainActivity.LIST_POSITION, index);
//            nowPlayingFragment.setArguments(bundle);
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.childPlaceHolder, nowPlayingFragment, MainActivity.FRAGMENT_NOW_PLAYING);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//        } else {
//            NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(MainActivity.SONG_NAME, Playlist.songs[index]);
//            bundle.putInt(MainActivity.LIST_POSITION, index);
//            nowPlayingFragment.setArguments(bundle);
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.childPlaceHolder, nowPlayingFragment, MainActivity.FRAGMENT_NOW_PLAYING);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//        }
        Log.d(TAG, "onItemClick");
        NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.SONG_NAME, Playlist.songs[index]);
        bundle.putInt(MainActivity.LIST_POSITION, index);
        nowPlayingFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.childPlaceHolder, nowPlayingFragment, MainActivity.FRAGMENT_NOW_PLAYING);
        fragmentTransaction.commit();
    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        SharedPreferences preferences = getSharedPreferences(SAVING_MINISCREEN, Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("title", miniTitle.getText().toString());
//        editor.putString("artis", miniArtis.getText().toString());
//        editor.commit();
//    }
//
    @Override
    protected void onResume() {
        super.onResume();
        setToFullScreen();
    }
}
