package com.example.trungnguyen.mymusicplayer.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trungnguyen.mymusicplayer.MainActivity;
import com.example.trungnguyen.mymusicplayer.NowPlayingFragment;
import com.example.trungnguyen.mymusicplayer.PauseAbleAnimation;
import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.fragments.SongListFragment;
import com.example.trungnguyen.mymusicplayer.models.Song;

import io.gresse.hugo.vumeterlibrary.VuMeterView;

/**
 * Created by benjakuben on 5/12/16.
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.SongViewHolder> {
    private Song[] mSongs;
    private Context mContext;
    private SongViewHolder mViewHolder;
    private  SongListFragment.OnSongItemSelectedInterface mListener;
    public PlaylistAdapter(Context context, Song[] songs, SongListFragment.OnSongItemSelectedInterface listener) {
        mContext = context;
        mSongs = songs;
        mListener = listener;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_song, parent, false);
        mViewHolder = new SongViewHolder(view);
        return mViewHolder;
//        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        holder.bindSong(mSongs[position]);
    }

    @Override
    public int getItemCount() {
        return mSongs.length;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitleLabel;
        public ImageView mFavIcon, imgIsPlaying;

        public SongViewHolder(View itemView) {
            super(itemView);
            mTitleLabel = (TextView) itemView.findViewById(R.id.songTitleLabel);
            mFavIcon = (ImageView) itemView.findViewById(R.id.favIcon);
            imgIsPlaying = (ImageView) itemView.findViewById(R.id.imgPlayingNow);
            itemView.setOnClickListener(this);
        }

        public void bindSong(Song song) {
            mTitleLabel.setText(song.getTitle());
//            if (song.isFavorite()) {
//                mFavIcon.setVisibility(View.VISIBLE);
//            } else {
//                mFavIcon.setVisibility(View.INVISIBLE);
//            }
//            if (song.isPlaying()) {
//                imgIsPlaying.setVisibility(View.VISIBLE);
//            } else {
//                imgIsPlaying.setVisibility(View.INVISIBLE);
//            }
        }
        @Override
        public void onClick(View v) {
//            Animation effectOnclickAnimation = new AlphaAnimation(0.3f, 1.0f);
//            effectOnclickAnimation.setDuration(500);
//            v.startAnimation(effectOnclickAnimation);
//            Intent intent = new Intent(mContext, DetailActivity.class);
//            intent.putExtra(MainActivity.SONG_NAME, mSongs[getAdapterPosition()]);
//            intent.putExtra(MainActivity.LIST_POSITION, getAdapterPosition());
//            mContext.startActivity(intent);
//            NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(MainActivity.SONG_NAME, mSongs[getAdapterPosition()]);
//            bundle.putInt(MainActivity.LIST_POSITION, getAdapterPosition());
//            nowPlayingFragment.setArguments(bundle);
//            FragmentManager fragmentManager =
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.childPlaceHolder, nowPlayingFragment, MainActivity.FRAGMENT_NOW_PLAYING);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//            ((Activity) mContext).overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
//            MainActivity.miniTitle.setText(mSongs[getAdapterPosition()].getTitle());
//            MainActivity.miniArtis.setText(mSongs[getAdapterPosition()].getArtist());
//            PauseAbleAnimation pauseAbleAnimation = new PauseAbleAnimation(
//                    MainActivity.miniSongPic, (Activity) mContext);
//            pauseAbleAnimation.setAnimation();
            mListener.onListSongItemSelected(getAdapterPosition(), v);
        }
    }
}