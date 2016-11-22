package com.example.trungnguyen.mymusicplayer;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

/**
 * Created by Trung Nguyen on 11/7/2016.
 */
public class PlayerHandler extends Handler {
    private PlayerService mPlayerService;
    public PlayerHandler(PlayerService playerService) {
        this.mPlayerService = playerService;
    }
    @Override
    public void handleMessage(Message msg) {
        switch (msg.arg1) {
            case 0://Play media
                mPlayerService.playMedia();
                break;
            case 1: //Pause media
                mPlayerService.pauseMedia();
                break;
            case 2: // isPlaying
                int isPlaying = mPlayerService.isMediaPlaying() ? 1 : 0;
                Message message = Message.obtain();
                if (isPlaying == 1 && msg.arg2 == 1) // Music is playing and user click play button
                    message.arg1 = 1;
                else if (isPlaying == 1 && msg.arg2 == 2) //Music is playing and user change track
                    message.arg1 = 0;
                else message.arg1 = isPlaying; //Music is not playing and user start new track
                message.replyTo = mPlayerService.messenger;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
