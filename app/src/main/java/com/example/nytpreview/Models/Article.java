package com.example.nytpreview.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Article implements Parcelable {

    private String web_url;
    private String pub_date;
    private String[] person;
    private Headline recipe_id;
    private Multimedia[] image_url;

    public Article(String web_url, String pub_date, String[] person, Headline recipe_id, Multimedia[] image_url) {
        this.web_url = web_url;
        this.pub_date = pub_date;
        this.person = person;
        this.recipe_id = recipe_id;
        this.image_url = image_url;
    }

    public Article() {
    }

    protected Article(Parcel in) {
        web_url = in.readString();
        pub_date = in.readString();
        person = in.createStringArray();
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

    public String[] getPerson() {
        return person;
    }

    public void setPerson(String[] person) {
        this.person = person;
    }

    public Headline getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Headline recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Multimedia[] getImage_url() {
        return image_url;
    }

    public void setImage_url(Multimedia[] image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "Article{" +
                "web_url='" + web_url + '\'' +
                ", pub_date='" + pub_date + '\'' +
                ", person=" + Arrays.toString(person) +
                ", recipe_id=" + recipe_id +
                ", image_url=" + Arrays.toString(image_url) +
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
        parcel.writeStringArray(person);
    }
}
