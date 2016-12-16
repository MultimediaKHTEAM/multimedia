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
import com.example.trungnguyen.mymusicplayer.models.TopTracks;

/**
 * Created by benjakuben on 5/12/16.
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.SongViewHolder> {
    private TopTracks[] mSongs;
    private Context mContext;
    private SongViewHolder mViewHolder;
    private  SongListFragment.OnSongItemSelectedInterface mListener;
    public PlaylistAdapter(Context context, TopTracks[] songs, SongListFragment.OnSongItemSelectedInterface listener) {
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
//        public ImageView mFavIcon, imgIsPlaying;

        public SongViewHolder(View itemView) {
            super(itemView);
            mTitleLabel = (TextView) itemView.findViewById(R.id.songTitleLabel);
            itemView.setOnClickListener(this);
        }

        public void bindSong(TopTracks song) {
            mTitleLabel.setText(song.getTopTrackName());
        }
        @Override
        public void onClick(View view) {
            mListener.onListSongItemSelected(getAdapterPosition(), view);
        }
    }
}