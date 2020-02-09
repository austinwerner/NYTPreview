package com.example.nytpreview.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Multimedia implements Parcelable {

    private String url;
    private String type;

    public Multimedia(String url, String type) {
        this.url = url;
        this.type = type;
    }

    public Multimedia() {
    }

    protected Multimedia(Parcel in) {
        url = in.readString();
        type = in.readString();
    }

    public static final Creator<Multimedia> CREATOR = new Creator<Multimedia>() {
        @Override
        public Multimedia createFromParcel(Parcel in) {
            return new Multimedia(in);
        }

        @Override
        public Multimedia[] newArray(int size) {
            return new Multimedia[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Multimedia{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(type);
    }
}
