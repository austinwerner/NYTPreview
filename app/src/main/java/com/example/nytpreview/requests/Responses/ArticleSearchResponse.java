package com.example.nytpreview.requests.Responses;

import com.example.nytpreview.models.Article;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleSearchResponse {

    @SerializedName("docs")
    @Expose
    private List<Article> mArticles;

    public List<Article> getArticles() {
        return mArticles;
    }

    @Override
    public String toString() {
        return "ArticleSearchResponse{" +
                "articles=" + mArticles +
                '}';
    }
}
