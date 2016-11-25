package com.example.trungnguyen.mymusicplayer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.fragments.SongListFragment;
import com.example.trungnguyen.mymusicplayer.models.Song;

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
            mListener.onListSongItemSelected(getAdapterPosition(), v);
        }
    }
}