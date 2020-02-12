package com.example.nytpreview.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Article implements Parcelable {

    private String web_url;
    private String pub_date;
    private Headline headline;
    private Multimedia[] multimedia;
    private String snippet;

    public Article(String web_url, String pub_date, Headline headline, Multimedia[] multimedia, String snippet) {
        this.web_url = web_url;
        this.pub_date = pub_date;
        this.headline = headline;
        this.multimedia = multimedia;
        this.snippet = snippet;
    }

    public Article() {
    }

    protected Article(Parcel in) {
        web_url = in.readString();
        pub_date = in.readString();
        headline = in.readParcelable(Headline.class.getClassLoader());
        multimedia = in.createTypedArray(Multimedia.CREATOR);
        snippet = in.readString();
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

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(web_url);
        parcel.writeString(pub_date);
        parcel.writeParcelable(headline, i);
        parcel.writeTypedArray(multimedia, i);
        parcel.writeString(snippet);
    }

    @Override
    public String toString() {
        return "Article{" +
                "web_url='" + web_url + '\'' +
                ", pub_date='" + pub_date + '\'' +
                ", headline=" + headline +
                ", multimedia=" + Arrays.toString(multimedia) +
                ", snippet='" + snippet + '\'' +
                '}';
    }
}
