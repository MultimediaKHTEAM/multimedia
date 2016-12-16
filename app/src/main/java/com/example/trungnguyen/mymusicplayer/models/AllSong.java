package com.example.trungnguyen.mymusicplayer.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Trung Nguyen on 12/16/2016.
 */
public class AllSong implements Parcelable {
    String title;
    String data;

    public AllSong(String data, String title){
        this.title = title;
        this.data = data;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setSourceList(String sourceList) {
        this.data = sourceList;
    }

    public String getTitle() {
        return title;
    }

    public String getSourceList() {
        return data;
    }
    @Override
    public int describeContents() {
        return 0; // ignore
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(data);

    }

    private AllSong(Parcel in) {
        title = in.readString();
        data = in.readString();
    }

    public static final Parcelable.Creator<AllSong> CREATOR = new Parcelable.Creator<AllSong>() {
        @Override
        public AllSong createFromParcel(Parcel source) {
            return new AllSong(source);
        }

        @Override
        public AllSong[] newArray(int size) {
            return new AllSong[size];
        }
    };
}
