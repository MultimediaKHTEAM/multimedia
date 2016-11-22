package com.example.trungnguyen.mymusicplayer;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Trung Nguyen on 11/6/2016.
 */
public class DownloadIntentService extends IntentService {
    private static final String TAG = DownloadIntentService.class.getSimpleName();
    public DownloadIntentService() {
        super("DownloadIntentService");
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String song = intent.getStringExtra(MainActivity.KEY_SONG);
        downloadNewSong(song);
    }
    private void downloadNewSong(String song) {
        long endTime = System.currentTimeMillis() + 5 * 1000;
        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, song + " Song downloaded");
    }

}
