package com.example.trungnguyen.mymusicplayer;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

import com.example.trungnguyen.mymusicplayer.models.Song;

/**
 * Created by Trung Nguyen on 11/7/2016.
 */
public class ActivityHandler extends Handler {
    private NowPlayingFragment mNowPlayingFragment;

    public ActivityHandler(NowPlayingFragment nowPlayingFragment) {
        this.mNowPlayingFragment = nowPlayingFragment;
    }
//    PauseAbleAnimation pauseAbleAnimation; //= new PauseAbleAnimation(SongImageFragment.imgSongs, detailActivity);
    @Override
    public void handleMessage(Message msg) {
//        if (msg.arg1 == 0) {
//            //Music is not playing
//            if (msg.arg2 == 1) {
//                detailActivity.ChangeButton(true);
//            } else {
//                //Play the music
//                Message message = Message.obtain();
//                message.arg1 = 0;
//                try {
//                    msg.replyTo.send(message);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//                //Change button to "Pause"
//                detailActivity.ChangeButton(false);
//            }
//        } else if (msg.arg1 == 1) {
//            //Music is playing
//            if (msg.arg2 == 1) {
//                detailActivity.ChangeButton(false);
//            } else {
//                //Pause the music
//                Message message = Message.obtain();
//                message.arg1 = 1;
//                try {
//                    msg.replyTo.send(message);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//                //Change the button to "Play"
//                detailActivity.ChangeButton(true);
//            }
//        }
        if (msg.arg1 == 0) {
            //Music is not playing
            Message message = Message.obtain();
            message.arg1 = 0;
            try {
                msg.replyTo.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //Change button to "Pause"
            mNowPlayingFragment.imgPlayPause.setImageResource(R.drawable.pause_circle);
            mNowPlayingFragment.mVuMeterView.resume(true);
        } else if (msg.arg1 == 1) {
            //Music is playing
            Message message = Message.obtain();
            message.arg1 = 1;
            try {
                msg.replyTo.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //Change the button to "Play"
            mNowPlayingFragment.imgPlayPause.setImageResource(R.drawable.play_circle);
            mNowPlayingFragment.mVuMeterView.pause();
        }
    }
}
