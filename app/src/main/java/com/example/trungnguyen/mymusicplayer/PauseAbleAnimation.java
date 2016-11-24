package com.example.trungnguyen.mymusicplayer;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Trung Nguyen on 11/18/2016.
 */
public class PauseAbleAnimation extends Animation {
    Animation animation;
    ImageView mImgSong;
    Activity mActivity;

    public PauseAbleAnimation(ImageView v, Activity activity) {
        mImgSong = v;
        mActivity = activity;
    }

    public void setAnimation() {
        animation = AnimationUtils.loadAnimation(mActivity, R.anim.turn_around);
        animation.setDuration(20000);
        animation.setRepeatCount(Animation.INFINITE);
        mImgSong.startAnimation(animation);
    }
    public void cancelAnimation(){
        animation.cancel();
        animation.reset();
    }
}

