package com.example.nytpreview.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Article implements Parcelable {

    private String web_url;
    private String pub_date;
    private Byline byline;
    private Headline headline;
    private Multimedia[] multimedia;

    public Article(String web_url, String pub_date, Byline byline, Headline headline, Multimedia[] multimedia) {
        this.web_url = web_url;
        this.pub_date = pub_date;
        this.byline = byline;
        this.headline = headline;
        this.multimedia = multimedia;
    }

    public Article() {
    }

    protected Article(Parcel in) {
        web_url = in.readString();
        pub_date = in.readString();
        byline = in.readParcelable(Byline.class.getClassLoader());
        headline = in.readParcelable(Headline.class.getClassLoader());
        multimedia = in.createTypedArray(Multimedia.CREATOR);
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }

    public Byline getByline() {
        return byline;
    }

    public void setByline(Byline byline) {
        this.byline = byline;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public Multimedia[] getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Multimedia[] multimedia) {
        this.multimedia = multimedia;
    }

    @Override
    public String toString() {
        return "Article{" +
                "web_url='" + web_url + '\'' +
                ", pub_date='" + pub_date + '\'' +
                ", byline=" + byline +
                ", headline=" + headline +
                ", multimedia=" + Arrays.toString(multimedia) +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(web_url);
        parcel.writeString(pub_date);
        parcel.writeParcelable(byline, i);
        parcel.writeParcelable(headline, i);
        parcel.writeTypedArray(multimedia, i);
    }
}
