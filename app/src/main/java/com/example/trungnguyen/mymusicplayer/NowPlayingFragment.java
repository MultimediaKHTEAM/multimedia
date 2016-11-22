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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.trungnguyen.mymusicplayer.models.Song;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.concurrent.TimeUnit;

import io.gresse.hugo.vumeterlibrary.VuMeterView;

/**
 * Created by Trung Nguyen on 11/21/2016.
 */
public class NowPlayingFragment extends Fragment {
    private static final String TAG = NowPlayingFragment.class.getSimpleName();
    private Song mSong;
    public VuMeterView mVuMeterView;
    private TextView songTitle, songArtist;
    SeekBar seekBar;
    private Intent seekBarIntent;
    private ImageView btnRepeat, imgLike;
    public ImageView imgPlayPause;
    private TextView tvMaxTime, tvCurPos;
    private boolean mBound = false;
    private Messenger activityMessenger = new Messenger(new ActivityHandler(this));
    private Messenger playerMessenger;
    private String songUrl;
    private boolean isLiked = false;
    int repeatCount = 0;
    private boolean isRepeatOne = false;
    private boolean isRepeatAll = false;
    BroadcastReceiver getMediaInfoReceive;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBound = true;
            Intent startServiceIntent = new Intent(getActivity(), PlayerService.class);
            getActivity().startService(startServiceIntent);
            Intent songUrlIntent = new Intent("SEND_SONG_URL");
            PauseAbleAnimation pauseAbleAnimation = new PauseAbleAnimation(SongImageFragment.imgSongs, getActivity());
            pauseAbleAnimation.setAnimation();
            songUrlIntent.putExtra("SONG_URL", songUrl);
            getActivity().sendBroadcast(songUrlIntent);
            if (isNetworkAvailable()) {
                playerMessenger = new Messenger(iBinder);
                Message message = Message.obtain();
                message.arg1 = 2;
                message.arg2 = 2;
                message.replyTo = activityMessenger;
                try {
                    playerMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else
                alertUserAboutError();
            Log.d(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };
    public static final String SaveBySharedPreferences = "AAA";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Now Playing Fragment onCreate");
//        updateUIFromService();
//        mSong = getArguments().getParcelable(MainActivity.SONG_NAME);
//        if (getArguments().getParcelableArray(MainActivity.SONG_NAME) != null)
//            songUrl = mSong.getmSongUrl();
//        imgLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!isLiked) {
//                    imgLike.setImageResource(R.drawable.top_rated_light);
//                    isLiked = true;
//                } else {
//                    isLiked = false;
//                    imgLike.setImageResource(R.drawable.top_rated);
//                }
//            }
//        });
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
//            }
//        });
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
//                getActivity().sendBroadcast(intent);
//            }
//        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Now Playing Fragment onCreateView");
        View mReturnView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_playing_song, container, false);
        addControls(mReturnView);
        updateUIFromService();
        mSong = getArguments().getParcelable(MainActivity.SONG_NAME);
        if (getArguments().getParcelable(MainActivity.SONG_NAME) != null)
            songUrl = mSong.getmSongUrl();
        if (mSong.isFavorite())
            imgLike.setImageResource(R.drawable.top_rated_light);
        else imgLike.setImageResource(R.drawable.top_rated);
        songTitle.setText(mSong.getTitle());
        songArtist.setText(mSong.getArtist());
        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mSong.isFavorite()) {
                    imgLike.setImageResource(R.drawable.top_rated_light);
                    mSong.setIsFavorite(true);
                } else {
                    imgLike.setImageResource(R.drawable.top_rated);
                    mSong.setIsFavorite(false);
                }
            }
        });
        imgPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {
                    if (isNetworkAvailable()) {
                        Message message = Message.obtain();
                        message.arg1 = 2;
                        message.arg2 = 1;
                        message.replyTo = activityMessenger;
                        try {
                            playerMessenger.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } else
                        alertUserAboutError();
                }
            }
        });
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (repeatCount == 2)
                    repeatCount = 0;
                else repeatCount++;
                if (repeatCount == 1) {
                    isRepeatAll = true;
                    isRepeatOne = false;
                    btnRepeat.setImageResource(R.drawable.btn_playback_repeat_all);
                } else if (repeatCount == 2) {
                    isRepeatOne = true;
                    isRepeatAll = false;
                    btnRepeat.setImageResource(R.drawable.btn_playback_repeat_one);
                } else {
                    isRepeatAll = false;
                    isRepeatOne = false;
                    btnRepeat.setImageResource(R.drawable.btn_playback_repeat);
                }
                Intent intent = new Intent("REPEAT");
                intent.putExtra("REPEAT_ALL", isRepeatAll);
                intent.putExtra("REPEAT_ONE", isRepeatOne);
                getActivity().sendBroadcast(intent);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (fromUser) {
                    Log.d(TAG, "Call On progress changed");
                    int seekPos = seekBar.getProgress();
                    seekBarIntent.putExtra("seekNewPosByUser", seekPos);
                    getActivity().sendBroadcast(seekBarIntent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return mReturnView;
    }

    private void addControls(View mReturnView) {
        mVuMeterView = (VuMeterView) mReturnView.findViewById(R.id.vumeter);
        songTitle = (TextView) mReturnView.findViewById(R.id.tvMiniTitle);
        songArtist = (TextView) mReturnView.findViewById(R.id.tvMiniArtis);
        seekBar = (SeekBar) mReturnView.findViewById(R.id.seekBar);
        btnRepeat = (ImageView) mReturnView.findViewById(R.id.btRepeat);
        btnRepeat.setImageResource(R.drawable.btn_playback_repeat);
        tvMaxTime = (TextView) mReturnView.findViewById(R.id.tvMaxTime);
        tvCurPos = (TextView) mReturnView.findViewById(R.id.tvCurrentPosition);
        imgPlayPause = (ImageView) mReturnView.findViewById(R.id.btPlayPause);
        imgLike = (ImageView) mReturnView.findViewById(R.id.btLike);
        initSmartTabSelector(mReturnView);
        seekBarIntent = new Intent(MainActivity.BROADCAST_SEEKBAR);
    }

    private void initSmartTabSelector(View mReturnView) {
        Log.d(TAG, "Call iniSmartSelector");
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(),
                FragmentPagerItems.with(getActivity())
                        .add("SONG_IMG", SongImageFragment.class)
                        .add("LYRICS", LyrisFragment.class)
                        .create());
        ViewPager viewPager = (ViewPager) mReturnView.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) mReturnView.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }

    BroadcastReceiver stopMediaBroadcastReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intentStopMedia) {
            Log.d(TAG, "CALL onReceive Stop Media - DetailActivity");
            final boolean isStopped = intentStopMedia.getBooleanExtra(PlayerService.COPA_MESSAGE, false);
            ChangeButton(isStopped);
            mVuMeterView.pause();
        }
    };

    public void ChangeButton(boolean isPlayOrPause) {
        if (isPlayOrPause)
            imgPlayPause.setImageResource(R.drawable.play_circle);
        else imgPlayPause.setImageResource(R.drawable.pause_circle);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void updateUIFromService() {
        getMediaInfoReceive = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intentMediaInfo) {
//                Log.d(TAG, "CALL onReceive get current Media Info - DetailActivity");
                int currentPosition = intentMediaInfo.getIntExtra("CURRENT_POST", 0);
                int duration = intentMediaInfo.getIntExtra("DURATION", 0);
                seekBar.setMax(duration);
                tvMaxTime.setText(millisecondsToString(duration));
                tvCurPos.setText(millisecondsToString(currentPosition));
//                Log.d(TAG, String.valueOf(currentPosition));
                seekBar.setProgress(currentPosition);
                if (currentPosition == duration) {
                    seekBar.setProgress(0);
                }
            }
        };
    }

    private String millisecondsToString(int milliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds((long) milliseconds);
        return minutes + ":" + seconds;
    }

    private void alertUserAboutError() {
        AlertDialog dialog = new AlertDialog();
        dialog.show(getActivity().getFragmentManager(), "error_dialog");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Now Playing Fragment onResume");
        SharedPreferences preferences = getActivity().getSharedPreferences(SaveBySharedPreferences, Activity.MODE_PRIVATE);
        repeatCount = preferences.getInt("SaveStatus", 0);
        switch (repeatCount) {
            case 0:
                btnRepeat.setImageResource(R.drawable.btn_playback_repeat);
                break;
            case 1:
                btnRepeat.setImageResource(R.drawable.btn_playback_repeat_all);
                break;
            case 2:
                btnRepeat.setImageResource(R.drawable.btn_playback_repeat_one);
                break;
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((getMediaInfoReceive),
                new IntentFilter(PlayerService.UPDATE_SEEKBAR)
        );
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((stopMediaBroadcastReceive),
                new IntentFilter(PlayerService.COPA_RESULT)
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Now Playing Fragment OnPause");
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(getMediaInfoReceive);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(stopMediaBroadcastReceive);
        if (mBound) {
            getActivity().unbindService(serviceConnection);
            mBound = false;
        }
        SharedPreferences preferences = getActivity().getSharedPreferences(SaveBySharedPreferences, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("SaveStatus", repeatCount);
        editor.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Now Playing Fragment onStart");
        Intent intent = new Intent(getActivity(), PlayerService.class);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBound) {
            getActivity().unbindService(serviceConnection);
            mBound = false;
        }
    }
}
