package com.example.trungnguyen.mymusicplayer;

import android.os.Looper;
import android.util.Log;


/**
 * Created by Trung Nguyen on 11/5/2016.
 */
public class DownloadThread extends Thread {
    private static final String TAG = DownloadThread.class.getSimpleName();
    public DownloadHandler downloadHandler;

    @Override
    public void run() {
        Looper.prepare(); //this creates a looper for a thread and also creates the message queue.
        downloadHandler = new DownloadHandler();
        Log.d(TAG, "run is called");
        Looper.loop(); // To start looping over the message queue, the process in run() method only build to Looper.loop()
    }
}
