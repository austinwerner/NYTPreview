package com.example.nytpreview.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Headline implements Parcelable {

    private String main;

    public Headline(String main) {
        this.main = main;
    }

    public Headline() {
    }

    protected Headline(Parcel in) {
        main = in.readString();
    }

    public static final Creator<Headline> CREATOR = new Creator<Headline>() {
        @Override
        public Headline createFromParcel(Parcel in) {
            return new Headline(in);
        }

        @Override
        public Headline[] newArray(int size) {
            return new Headline[size];
        }
    };

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "Headline{" +
                "main='" + main + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(main);
    }
}
