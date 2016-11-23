package com.example.trungnguyen.mymusicplayer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.trungnguyen.mymusicplayer.models.Song;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import java.util.concurrent.TimeUnit;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

public class DetailActivity extends AppCompatActivity  {

//    private static final String TAG = DetailActivity.class.getSimpleName();
//    private Song mSong;
//    SeekBar seekBar;
//    private boolean mBound = false;
//    private RelativeLayout rootLayoutActivityDetail;
//    public static final String SHARE_SONG = "com.example.trungnguyen.intent.action.SHARE_SONG";
//    private ImageView btnRepeat, imgPlayPause, imgLike, imgbtExit;
//    private TextView tvMaxTime, tvCurPos, tvSongTitle;
////    private Messenger activityMessenger = new Messenger(new ActivityHandler(this));
//    private Messenger playerMessenger;
//    BroadcastReceiver getMediaInfoReceive;
//    private int repeatCount = 0;
//    private boolean isLiked = false;
//    //    private boolean isAnimationWorking = true;
//    ImageView imageView;
//    PauseAbleAnimation pauseAbleAnimation;
//    ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            mBound = true;
//            Intent startServiceIntent = new Intent(DetailActivity.this, PlayerService.class);
//            startService(startServiceIntent);
//            pauseAbleAnimation = new PauseAbleAnimation(SongImageFragment.imgSongs, DetailActivity.this);
//            Intent songUrlIntent = new Intent("SEND_SONG_URL");
////            pauseAbleAnimation.setAnimation();
//            songUrlIntent.putExtra("SONG_URL", mSong.getmSongUrl());
//            sendBroadcast(songUrlIntent);
//            if (isNetworkAvailable()) {
//                playerMessenger = new Messenger(iBinder);
//                Message message = Message.obtain();
//                message.arg1 = 2;
//                message.arg2 = 2;
//                message.replyTo = activityMessenger;
//                try {
//                    playerMessenger.send(message);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            } else
//                alertUserAboutError();
//            Log.d(TAG, "onServiceConnected");
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            mBound = false;
//        }
//    };
//    private boolean isRepeatAll = false;
//    private boolean isRepeatOne = false;
//    private Intent seekBarIntent;
//    public static final String BROADCAST_SEEKBAR = "com.example.trungnguyen.mymusicplayer.sendseekbar";
//    public static final String SaveStatus = "SAVE_STATUS";
//    public static final String SaveBySharedPreferences = "SAVE_SHAREDPREFERENCES";
//
//    @Override
//    protected void onCreate(final Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
//        addControls();
//        seekBarIntent = new Intent(this.BROADCAST_SEEKBAR);
//        updateUIFromService();
//        Intent fromMainActivityIntent = getIntent();
//        if (Intent.ACTION_SEND.equals(fromMainActivityIntent.getAction())) {
//            handleSendIntent(fromMainActivityIntent);
//        } else {
//            if (fromMainActivityIntent.getParcelableExtra(MainActivity.SONG_NAME) != null) {
//                mSong = fromMainActivityIntent.getParcelableExtra(MainActivity.SONG_NAME);
//            }
//            tvSongTitle.setText(mSong.getTitle());
//            final int recyclerViewPosition = fromMainActivityIntent.getIntExtra(MainActivity.LIST_POSITION, 0);
//            if (mSong.isFavorite()) {
//                imgLike.setImageResource(R.drawable.top_rated_light);
//            } else imgLike.setImageResource(R.drawable.top_rated);
//            imgLike.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (!isLiked) {
//                        imgLike.setImageResource(R.drawable.top_rated_light);
//                        isLiked = true;
//                    } else {
//                        isLiked = false;
//                        imgLike.setImageResource(R.drawable.top_rated);
//                    }
////                    Intent resultIntent = new Intent();
////                    resultIntent.putExtra(MainActivity.LIST_POSITION, recyclerViewPosition);
////                    resultIntent.putExtra(MainActivity.IS_FAVORITE, isLiked);
////                    setResult(100, resultIntent);
//                }
//            });
//        }
//        imgPlayPause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mBound) {
//                    if (isNetworkAvailable()) {
//                        Message message = Message.obtain();
//                        message.arg1 = 2;
//                        message.arg2 = 1;
//                        message.replyTo = activityMessenger;
//                        try {
//                            playerMessenger.send(message);
//                        } catch (RemoteException e) {
//                            e.printStackTrace();
//                        }
//                    } else
//                        alertUserAboutError();
//                }
//                // Second way to stop animation
////                if(isAnimationWorking){
////                    pauseAbleAnimation.cancelAnimation();
////                    isAnimationWorking = false;
////                } else{
////                   pauseAbleAnimation.setAnimation();
////                    isAnimationWorking = true;
////                }
//            }
//        });
//
//        btnRepeat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (repeatCount == 2)
//                    repeatCount = 0;
//                else repeatCount++;
//                if (repeatCount == 1) {
//                    isRepeatAll = true;
//                    isRepeatOne = false;
//                    btnRepeat.setImageResource(R.drawable.btn_playback_repeat_all);
//                } else if (repeatCount == 2) {
//                    isRepeatOne = true;
//                    isRepeatAll = false;
//                    btnRepeat.setImageResource(R.drawable.btn_playback_repeat_one);
//                } else {
//                    isRepeatAll = false;
//                    isRepeatOne = false;
//                    btnRepeat.setImageResource(R.drawable.btn_playback_repeat);
//                }
//                Intent intent = new Intent("REPEAT");
//                intent.putExtra("REPEAT_ALL", isRepeatAll);
//                intent.putExtra("REPEAT_ONE", isRepeatOne);
//                sendBroadcast(intent);
//            }
//        });
//        imgbtExit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MainActivity.mLayout.setPanelState(PanelState.COLLAPSED);
//                finish();
//                overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
//            }
//        });
//    }
//
//    private void handleSendIntent(Intent intent) {
//        if (intent.getStringExtra(Intent.EXTRA_TEXT) != null) {
//            Snackbar.make(rootLayoutActivityDetail, intent.getStringExtra(Intent.EXTRA_TEXT), Snackbar.LENGTH_LONG).show();
//        }
//    }
//
//    private void addControls() {
//        rootLayoutActivityDetail = (RelativeLayout) findViewById(R.id.rootLayoutActivityDetail);
//        seekBar = (SeekBar) findViewById(R.id.seekBar);
//        seekBar.setOnSeekBarChangeListener(this);
//        btnRepeat = (ImageView) findViewById(R.id.btRepeat);
//        btnRepeat.setImageResource(R.drawable.btn_playback_repeat);
//        tvMaxTime = (TextView) findViewById(R.id.tvMaxTime);
//        tvCurPos = (TextView) findViewById(R.id.tvCurrentPosition);
//        imgPlayPause = (ImageView) findViewById(R.id.btPlayPause);
//        imgLike = (ImageView) findViewById(R.id.btLike);
//        tvSongTitle = (TextView) findViewById(R.id.tvTitle);
//        imageView = (ImageView) findViewById(R.id.imgSong);
//        imgbtExit = (ImageView) findViewById(R.id.imgbtExit);
//        initSmartTabSelector();
//    }
//
//    private void initSmartTabSelector() {
//        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
//                getSupportFragmentManager(), FragmentPagerItems.with(this)
//                .add("SONG_IMG", SongImageFragment.class)
//                .add("LYRICS", LyrisFragment.class)
//                .create());
//
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        viewPager.setAdapter(adapter);
//
//        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
//        viewPagerTab.setViewPager(viewPager);
//    }
//
//    public void ChangeButton(boolean isPlayOrPause) {
//        if (isPlayOrPause)
//            imgPlayPause.setImageResource(R.drawable.play_circle);
//        else imgPlayPause.setImageResource(R.drawable.pause_circle);
//    }
//
//    private void updateUIFromService() {
//        getMediaInfoReceive = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intentMediaInfo) {
//                Log.d(TAG, "CALL onReceive get current Media Info - DetailActivity");
//                int currentPosition = intentMediaInfo.getIntExtra("CURRENT_POST", 0);
//                int duration = intentMediaInfo.getIntExtra("DURATION", 0);
//                seekBar.setMax(duration);
//                tvMaxTime.setText(millisecondsToString(duration));
//                tvCurPos.setText(millisecondsToString(currentPosition));
//                Log.d(TAG, String.valueOf(currentPosition));
//                seekBar.setProgress(currentPosition);
//                if (currentPosition == duration) {
//                    seekBar.setProgress(0);
//                }
//            }
//        };
//    }
//
//    private String millisecondsToString(int milliseconds) {
//        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) milliseconds);
//        long seconds = TimeUnit.MILLISECONDS.toSeconds((long) milliseconds);
//        return minutes + ":" + seconds;
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.detail, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemId = item.getItemId();
//        if (itemId == R.id.action_share) {
//            if (mSong != null) {
//                Intent customIntent = new Intent(SHARE_SONG);
//                customIntent.putExtra(MainActivity.SONG_NAME, mSong);
//                Intent chooser = Intent.createChooser(customIntent, "Share_Song");
//                if (customIntent.resolveActivity(getPackageManager()) == null)
//                    Snackbar.make(rootLayoutActivityDetail, "Cannot found Activity for handle", Snackbar.LENGTH_LONG).show();
//                else
//                    startActivity(chooser);
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void alertUserAboutError() {
//        AlertDialog dialog = new AlertDialog();
//        dialog.show(getFragmentManager(), "error_dialog");
//    }
//
//    private boolean isNetworkAvailable() {
//        ConnectivityManager manager = (ConnectivityManager) getSystemService
//                (Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
//        boolean isAvailable = false;
//        if (networkInfo != null && networkInfo.isConnected()) {
//            isAvailable = true;
//        }
//        return isAvailable;
//    }
//
//    BroadcastReceiver stopMediaBroadcastReceive = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intentStopMedia) {
//            Log.d(TAG, "CALL onReceive Stop Media - DetailActivity");
//            final boolean isStopped = intentStopMedia.getBooleanExtra(PlayerService.COPA_MESSAGE, false);
//            ChangeButton(isStopped);
//        }
//    };
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(TAG, "onStart - DetailActivity");
//        SharedPreferences preferences = getSharedPreferences(SaveBySharedPreferences, Activity.MODE_PRIVATE);
//        repeatCount = preferences.getInt(SaveStatus, 0);
//        switch (repeatCount) {
//            case 0:
//                btnRepeat.setImageResource(R.drawable.btn_playback_repeat);
//                break;
//            case 1:
//                btnRepeat.setImageResource(R.drawable.btn_playback_repeat_all);
//                break;
//            case 2:
//                btnRepeat.setImageResource(R.drawable.btn_playback_repeat_one);
//                break;
//        }
//        isLiked = preferences.getBoolean("Save_like_button", false);
//        if(isLiked)
//            imgLike.setImageResource(R.drawable.top_rated_light);
//        else imgLike.setImageResource(R.drawable.top_rated);
//        Log.d(TAG, repeatCount + "");
//        LocalBroadcastManager.getInstance(this).registerReceiver((stopMediaBroadcastReceive),
//                new IntentFilter(PlayerService.COPA_RESULT)
//        );
//
//        LocalBroadcastManager.getInstance(this).registerReceiver((getMediaInfoReceive),
//                new IntentFilter(PlayerService.UPDATE_SEEKBAR)
//        );
//        Intent intent = new Intent(DetailActivity.this, PlayerService.class);
//        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d(TAG, "onStop - DetailActivity");
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(stopMediaBroadcastReceive);
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(getMediaInfoReceive);
//        if (mBound) {
//            unbindService(serviceConnection);
//            mBound = false;
//        }
//        Log.d(TAG, repeatCount + "");
//        SharedPreferences preferences = getSharedPreferences(SaveBySharedPreferences, Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt(SaveStatus, repeatCount);
//        editor.putBoolean("Save_like_button", isLiked);
//        editor.commit();
//    }
//
//    @Override
//    public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
//        if (fromUser) {
//            Log.d(TAG, "Call On progress changed");
//            int seekPos = seekBar.getProgress();
//            seekBarIntent.putExtra("seekNewPosByUser", seekPos);
//            sendBroadcast(seekBarIntent);
//        }
//    }
//
//    @Override
//    public void onStartTrackingTouch(SeekBar seekBar) {
//    }
//
//    @Override
//    public void onStopTrackingTouch(SeekBar seekBar) {
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
//    }
}
