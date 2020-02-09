package com.example.nytpreview.Requests.Responses;

import com.example.nytpreview.Models.Article;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleSearchResponse {

    @SerializedName("docs")
    @Expose
    private Article article;

    public Article getArticle() {
        return article;
    }

    @Override
    public String toString() {
        return "ArticleSearchResponse{" +
                "article=" + article +
                '}';
    }
}
