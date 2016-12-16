package com.example.trungnguyen.mymusicplayer.fragments;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.trungnguyen.mymusicplayer.ActivityHandler;
import com.example.trungnguyen.mymusicplayer.AlertDialog;
import com.example.trungnguyen.mymusicplayer.MainActivity;
import com.example.trungnguyen.mymusicplayer.PauseAbleAnimation;
import com.example.trungnguyen.mymusicplayer.PlayerService;
import com.example.trungnguyen.mymusicplayer.Playlist;
import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.managers.LastSongPreference;
import com.example.trungnguyen.mymusicplayer.models.Song;
import com.example.trungnguyen.mymusicplayer.models.TopTracks;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.concurrent.TimeUnit;

import io.gresse.hugo.vumeterlibrary.VuMeterView;

/**
 * Created by Trung Nguyen on 11/21/2016.
 */
public class NowPlayingFragment extends Fragment {
    private static final String TAG = NowPlayingFragment.class.getSimpleName();
    public VuMeterView mVuMeterView;
    SeekBar seekBar;
    private Intent seekBarIntent;
    private ImageView btnRepeat, imgLike, btSkipNext, btSkipPrevious;
    public ImageView imgPlayPause;
    private TextView tvMaxTime, tvCurPos, songTitle, songArtist;
    private boolean mBound = false;
    private Messenger activityMessenger = new Messenger(new ActivityHandler(this));
    private Messenger playerMessenger;
    private String songUrl = null;
    private int repeatCount = 0;
    TopTracks mSong;
    private boolean isRepeatOne = false;
    private boolean isRepeatAll = false;
    private int songIndex;
    BroadcastReceiver getMediaInfoReceive;
    // onServices connected and disconnected
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Now Playing Fragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Now Playing Fragment onCreateView");
        View mReturnView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_playing_song, container, false);
        updateUIFromService();
        Log.d(TAG, getArguments().getInt(MainActivity.LIST_POSITION) + "");
        if (getArguments().getParcelable(MainActivity.SONG_NAME) != null) {
            mSong = getArguments().getParcelable(MainActivity.SONG_NAME);
            songIndex = getArguments().getInt("LIST_INDEX");
        } else {
            mSong = LastSongPreference.getLastSong(getActivity());
            songIndex = LastSongPreference.getLastSongPlayingIndex(getActivity());
        }
        addControls(mReturnView);
        if (mSong != null) {
            songUrl = mSong.getTrackUrl();
            Log.d(TAG, songUrl);
            if (mSong.isLike())
                imgLike.setImageResource(R.drawable.thumb_up);
            else imgLike.setImageResource(R.drawable.thumb_up_outline);
            songTitle.setText(mSong.getTopTrackName());
            songArtist.setText(mSong.getArtist());
            ButtonSkipPreviousEvent();
            ButtonLikeEvent();
            ButtonSkipNextEvent();
        }
        ButtonPlayPauseEvent();
        ButtonRepeatEvent();
        //On Seek bar progress changed
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

    private void ButtonSkipPreviousEvent() {
        btSkipPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation effectOnclickAnimation = new AlphaAnimation(0.3f, 1.0f);
                effectOnclickAnimation.setDuration(500);
                view.startAnimation(effectOnclickAnimation);
                songIndex--;
                if (songIndex >= 0) {
                    NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
                    PlayNewSong(songIndex, nowPlayingFragment);
                }
            }
        });
    }

    private void ButtonSkipNextEvent() {
        btSkipNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation effectOnclickAnimation = new AlphaAnimation(0.3f, 1.0f);
                effectOnclickAnimation.setDuration(500);
                view.startAnimation(effectOnclickAnimation);
                songIndex++;
                if (songIndex <= Playlist.songs.length) {
                    NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
                    PlayNewSong(songIndex, nowPlayingFragment);
                }
            }
        });
    }

    public void PlayNewSong(int skipIndex, NowPlayingFragment nowPlayingFragment) {
        Log.d(TAG, "onItemClick");
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.SONG_NAME, Playlist.songs[skipIndex]);
        bundle.putInt("LIST_INDEX", skipIndex);
        Log.d(TAG, skipIndex + "");
        nowPlayingFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.childPlaceHolder, nowPlayingFragment, MainActivity.FRAGMENT_NOW_PLAYING);
        fragmentTransaction.commit();
    }

    private void ButtonPlayPauseEvent() {
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
    }

    private void ButtonRepeatEvent() {
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (repeatCount == 2)
                    repeatCount = 0;
                else repeatCount++;
                setRepeatButton(repeatCount);
            }
        });
    }

    private void ButtonLikeEvent() {
        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mSong.isLike()) {
                    imgLike.setImageResource(R.drawable.thumb_up);
                    mSong.setLike(true);
                } else {
                    imgLike.setImageResource(R.drawable.thumb_up_outline);
                    mSong.setLike(false);
                }
            }
        });
    }

    private void sendBroadcastRepeat() {
        Intent intent = new Intent("REPEAT");
        intent.putExtra("REPEAT_ALL", isRepeatAll);
        intent.putExtra("REPEAT_ONE", isRepeatOne);
        getActivity().sendBroadcast(intent);
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
        imgPlayPause.setBackgroundResource(R.drawable.click_effet);
        imgLike = (ImageView) mReturnView.findViewById(R.id.imgLove);
        imgLike.setBackgroundResource(R.drawable.click_effet);
        initSmartTabSelector(mReturnView);
        seekBarIntent = new Intent(MainActivity.BROADCAST_SEEKBAR);
        btSkipNext = (ImageView) mReturnView.findViewById(R.id.btSkipNext);
        btSkipNext.setBackgroundResource(R.drawable.click_effet);
        btSkipPrevious = (ImageView) mReturnView.findViewById(R.id.btSkipPrevious);
        btSkipPrevious.setBackgroundResource(R.drawable.click_effet);
    }

    private void initSmartTabSelector(View mReturnView) {
        Log.d(TAG, "Call iniSmartSelector");
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(),
                FragmentPagerItems.with(getActivity())
                        .add("SONG_IMG", SongImageFragment.class, new Bundler().putString("COVER", mSong.getImageUrl()).get())
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
            final boolean isStopped = intentStopMedia.getBooleanExtra(PlayerService.COPA_MESSAGE, false);
            boolean repeatAll = intentStopMedia.getBooleanExtra("IS_REPEAT_ALL", false);
            ChangeButton(isStopped);
            songIndex++;
            if (songIndex > 2) songIndex = 0;
            if (repeatAll) {
                NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
                PlayNewSong(songIndex, nowPlayingFragment);

            }
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
        repeatCount = LastSongPreference.getLastRepeatCount(getActivity());
        setRepeatButton(repeatCount);
    }

    private void setRepeatButton(int repeatCountTem) {
        switch (repeatCountTem) {
            case 0:
                btnRepeat.setImageResource(R.drawable.btn_playback_repeat);
                isRepeatAll = false;
                isRepeatOne = false;
                break;
            case 1:
                btnRepeat.setImageResource(R.drawable.btn_playback_repeat_all);
                isRepeatAll = true;
                isRepeatOne = false;
                break;
            case 2:
                btnRepeat.setImageResource(R.drawable.btn_playback_repeat_one);
                isRepeatAll = false;
                isRepeatOne = true;
                break;
        }
        sendBroadcastRepeat();
    }

    @Override
    public void onPause() {
        super.onPause();
        LastSongPreference.saveLastRepeatCount(getActivity(), repeatCount);
        Log.d(TAG, "Now Playing Fragment OnPause");
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(getMediaInfoReceive);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(stopMediaBroadcastReceive);
        if (mBound) {
            getActivity().unbindService(serviceConnection);
            mBound = false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Now Playing Fragment onStart");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((getMediaInfoReceive),
                new IntentFilter(PlayerService.UPDATE_SEEKBAR)
        );
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((stopMediaBroadcastReceive),
                new IntentFilter(PlayerService.COPA_RESULT)
        );
        Intent intent = new Intent(getActivity(), PlayerService.class);
        if (songUrl != null)
            getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        if (mBound) {
            getActivity().unbindService(serviceConnection);
            mBound = false;
        }
        LastSongPreference.saveLastSong(getActivity(), mSong);
        LastSongPreference.saveLastSongPlayingIndex(getActivity(), songIndex);
        Log.d(TAG, "Saved last song by SharedPreferences");
        super.onStop();
    }
}
