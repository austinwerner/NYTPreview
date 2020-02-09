package com.example.nytpreview.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Byline implements Parcelable {

    private Author[] person;

    public Byline(Author[] person) {
        this.person = person;
    }

    public Byline() {
    }

    protected Byline(Parcel in) {
    }

    public static final Creator<Byline> CREATOR = new Creator<Byline>() {
        @Override
        public Byline createFromParcel(Parcel in) {
            return new Byline(in);
        }

        @Override
        public Byline[] newArray(int size) {
            return new Byline[size];
        }
    };

    public Author[] getPerson() {
        return person;
    }

    public void setPerson(Author[] person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Byline{" +
                "person=" + Arrays.toString(person) +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
