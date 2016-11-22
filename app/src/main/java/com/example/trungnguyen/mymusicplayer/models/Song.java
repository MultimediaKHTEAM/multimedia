package com.example.trungnguyen.mymusicplayer.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Trung Nguyen on 11/8/2016.
 */
public class Song implements Parcelable {

    private long mId;
    private String mTitle;
    private String mSongUrl;
    private int mDuration;
    private String mArtist;
    private String mLabel;
    private int mYearReleased;
    private long mAlbumId;
    private boolean mIsFavorite;
    private boolean isPlaying;
    public Song(String url, long id, String title, int duration, String artist, String label,
                int yearReleased, long albumId, boolean isFavorite) {
        setmSongUrl(url);
        setId(id);
        setTitle(title);
        setDuration(duration);
        setArtist(artist);
        setLabel(label);
        setYearReleased(yearReleased);
        setAlbumId(albumId);
        setIsFavorite(isFavorite);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public void setmSongUrl(String mSongUrl) {
        this.mSongUrl = mSongUrl;
    }

    public String getmSongUrl() {
        return mSongUrl;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public int getYearReleased() {
        return mYearReleased;
    }

    public void setYearReleased(int yearReleased) {
        mYearReleased = yearReleased;
    }

    public long getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(long albumId) {
        mAlbumId = albumId;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        mIsFavorite = isFavorite;
    }

    public String toString() {
        return "ID: " + mId + "\n" +
                "Title: " + mTitle + "\n" +
                "Duration: " + mDuration + "\n" +
                "Artist: " + mArtist + "\n" +
                "Label: " + mLabel + "\n" +
                "Year Released: " + mYearReleased + "\n" +
                "Album ID: " + mAlbumId + "\n" +
                "Favorite?: " + mIsFavorite;
    }

    @Override
    public int describeContents() {
        return 0; // ignore
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSongUrl);
        dest.writeLong(mId);
        dest.writeString(mTitle);
        dest.writeInt(mDuration);
        dest.writeString(mArtist);
        dest.writeString(mLabel);
        dest.writeInt(mYearReleased);
        dest.writeLong(mAlbumId);
        dest.writeInt(mIsFavorite ? 1 : 0);
    }

    private Song(Parcel in) {
        mSongUrl = in.readString();
        mId = in.readLong();
        mTitle = in.readString();
        mDuration = in.readInt();
        mArtist = in.readString();
        mLabel = in.readString();
        mYearReleased = in.readInt();
        mAlbumId = in.readLong();
        mIsFavorite = in.readInt() != 0;
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}

