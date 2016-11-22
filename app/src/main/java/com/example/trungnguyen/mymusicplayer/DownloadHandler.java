package com.example.trungnguyen.mymusicplayer;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Trung Nguyen on 11/5/2016.
 */
public class DownloadHandler extends Handler {
    private static final String TAG = DownloadHandler.class.getSimpleName();
    private DownloadService service;

    @Override
    public void handleMessage(Message msg) {// Tell handler what it should do when it gets a message
        Log.d(TAG, "handleMessage is called");
        downloadNewSong(msg.obj.toString());
        service.stopSelf(msg.arg1);
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

    public void setService(DownloadService service) {
        this.service = service;
    }
}
