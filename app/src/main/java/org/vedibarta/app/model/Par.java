package org.vedibarta.app.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by e560 on 11/05/17.
 */

public class Par implements Parcelable {
    private String parTitle;
    private String size;
    private String dedication;
    private String[] zipFiles;
    private String englishName;
    private String parashUrl;
    private Track[] trackList;


    public Par(String bookTitle, String size, String dedication, String zipFile
            , String latName, String[][] mp3List) {

        this.parTitle = bookTitle;
        this.size = size;
        this.dedication = dedication;
        this.zipFiles = zipFile.split(",");
        parashUrl = this.zipFiles[0].replaceAll(Uri.parse(this.zipFiles[0]).getLastPathSegment(), "");
        this.englishName = latName;
        trackList = new Track[mp3List.length];
        int index = 0;
        for (String[] track: mp3List){
            Track trk = new Track(track[0], track[1], track[2]);
            trackList[index] = trk;
            index++;
        }
    }

    public String getParTitle() {
        return parTitle;
    }

    public String getSize() {
        return size;
    }

    public String getDedication() {
        return dedication;
    }

    public String[] getZipFiles() {
        return zipFiles;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getParashUrl() {
        return parashUrl;
    }

    public Track[] getTrackList() {
        return trackList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parTitle);
        dest.writeString(this.size);
        dest.writeString(this.dedication);
        dest.writeStringArray(this.zipFiles);
        dest.writeString(this.englishName);
        dest.writeString(this.parashUrl);
        dest.writeTypedArray(this.trackList, flags);
    }

    protected Par(Parcel in) {
        this.parTitle = in.readString();
        this.size = in.readString();
        this.dedication = in.readString();
        this.zipFiles = in.createStringArray();
        this.englishName = in.readString();
        this.parashUrl = in.readString();
        this.trackList = in.createTypedArray(Track.CREATOR);
    }

    public static final Parcelable.Creator<Par> CREATOR = new Parcelable.Creator<Par>() {
        @Override
        public Par createFromParcel(Parcel source) {
            return new Par(source);
        }

        @Override
        public Par[] newArray(int size) {
            return new Par[size];
        }
    };
}
