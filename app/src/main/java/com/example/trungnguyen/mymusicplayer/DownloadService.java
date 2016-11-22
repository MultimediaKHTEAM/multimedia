package com.example.trungnguyen.mymusicplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Trung Nguyen on 11/6/2016.
 */
public class DownloadService extends Service {
    private static final String TAG = DownloadService.class.getSimpleName();
    private DownloadHandler downloadHandler;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate is called");
        DownloadThread downloadThread = new DownloadThread();
        downloadThread.setName("Download Thread");
        downloadThread.start();
        while (downloadThread.downloadHandler == null) {

        }
        downloadHandler = downloadThread.downloadHandler;
        downloadHandler.setService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand is Called");
        String song = intent.getStringExtra(MainActivity.KEY_SONG);
        Message message = Message.obtain();
        message.obj = song;
        message.arg1 = startId;
        downloadHandler.sendMessage(message);
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        MainActivity.dialogDownload.dismiss();
        Log.d(TAG, "onDestroy is called");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
