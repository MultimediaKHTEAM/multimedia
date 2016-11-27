package com.example.trungnguyen.mymusicplayer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Trung Nguyen on 11/6/2016.
 */
public class PlayerService extends Service {
    private static final String TAG = PlayerService.class.getSimpleName();
    Handler handler = new Handler();
    public static final String UPDATE_SEEKBAR = "com.example.trungnguyen.mymusicplayer.PlayerService.SEEKBAR_UPDATE";
    private MediaPlayer mediaPlayer;
    static final public String COPA_RESULT = "com.example.trungnguyen.mymusicplayer.PlayerService.REQUEST_PROCESSED";
    private final boolean isStop = true;
    static final public String COPA_MESSAGE = "listen_for_stop_media";
    //    private IBinder iBinder = new LocalBinder();
    public Messenger messenger = new Messenger(new PlayerHandler(this));
    private LocalBroadcastManager localBroadCastManager;
    Intent seekIntent;
    //    BroadcastReceiver songUrlReceiver;
    private boolean isRepeatAll = false;
    private boolean isRepeatOne = false;
    String tempUrl;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
//        mediaPlayer = MediaPlayer.create(this, R.raw.jingle);
        localBroadCastManager = LocalBroadcastManager.getInstance(this);
        mediaPlayer = new MediaPlayer();
        tempUrl = null;
        seekIntent = new Intent(UPDATE_SEEKBAR);
        registerReceiver(broadcastReceiver, new IntentFilter(
                MainActivity.BROADCAST_SEEKBAR));
        registerReceiver(repeatReceiver, new IntentFilter(
                "REPEAT"));
        registerReceiver(songUrlReceiver, new IntentFilter(
                "SEND_SONG_URL"));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Notification.Builder notificationBuilder = new Notification.Builder(this);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = notificationBuilder.build();
        startForeground(11, notification);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.d(TAG, "CALL COMPLETION MEDIA");
                sendStopMediaResult(isStop);
                if (!isRepeatOne) {
                    mediaPlayer.setLooping(false);
                    stopSelf();
                    stopForeground(true);
                }
            }
        });
        setupHandler();
        return Service.START_NOT_STICKY;
    }

    private void sendStopMediaResult(boolean isStopped) {
        Intent intent = new Intent(COPA_RESULT);
        intent.putExtra(COPA_MESSAGE, isStopped);
        localBroadCastManager.sendBroadcast(intent);
    }

    private void sendMediaInfo() {
        if (isMediaPlaying()) {
            Intent mediaInfoIntent = new Intent(UPDATE_SEEKBAR);
            mediaInfoIntent.putExtra("DURATION", this.getMediaDuration());
            mediaInfoIntent.putExtra("CURRENT_POST", this.getMediaCurrentPosition());
            localBroadCastManager.sendBroadcast(mediaInfoIntent);
        }
    }

    private void setupHandler() {
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second
    }

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            sendMediaInfo();
            handler.postDelayed(this, 1000); // 2 seconds
        }
    };
    BroadcastReceiver songUrlReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Call onReceive send Url");
            String mp3Url = intent.getStringExtra("SONG_URL");
            if (tempUrl == null || !tempUrl.equals(mp3Url)) {
                Uri uri = Uri.parse(mp3Url);
                try {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(getApplicationContext(), uri);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepare();
                    mediaPlayer.setLooping(false);
                } catch (IOException e) {
                    Log.d(TAG, "Error do not found resource");
                }
                tempUrl = mp3Url;
            } else
                mp3Url = null;
        }
    };
    private BroadcastReceiver repeatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isRepeatAll = intent.getBooleanExtra("REPEAT_ALL", false);
            isRepeatOne = intent.getBooleanExtra("REPEAT_ONE", false);
            if (!isRepeatAll && isRepeatOne)
                mediaPlayer.setLooping(isRepeatOne);
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateSeekPos(intent);
        }
    };

    // Update seek position from Activity
    public void updateSeekPos(Intent intent) {
        int seekPos = intent.getIntExtra("seekNewPosByUser", 0);
        if (mediaPlayer.isPlaying()) {
//            handler.removeCallbacks(sendUpdatesToUI);
            mediaPlayer.seekTo(seekPos);
//            setupHandler();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(repeatReceiver);
        unregisterReceiver(songUrlReceiver);
        mediaPlayer.release();
    }

//    public class LocalBinder extends Binder {
//        public PlayerService getService() {
//            return PlayerService.this;
//        }
//    }

    // Client Methods
    public boolean isMediaPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void playMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void pauseMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public int getMediaDuration() {
        return mediaPlayer.getDuration();
    }

    public int getMediaCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

}
