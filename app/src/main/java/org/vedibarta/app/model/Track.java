package org.vedibarta.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by e560 on 14/05/17.
 */

public class Track implements Parcelable {
    private String url;
    private String size;
    private String length;

    public Track(String url, String size, String length) {
        this.url = url;
        this.size = size;
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public String getSize() {
        return size;
    }

    public String getLength() {
        return length;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.size);
        dest.writeString(this.length);
    }

    protected Track(Parcel in) {
        this.url = in.readString();
        this.size = in.readString();
        this.length = in.readString();
    }

    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
