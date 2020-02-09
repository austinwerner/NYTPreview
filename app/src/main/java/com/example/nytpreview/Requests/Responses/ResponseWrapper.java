package com.example.nytpreview.Requests.Responses;

import com.example.nytpreview.Models.Article;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.Response;

public class ResponseWrapper {
    @SerializedName("response")
    @Expose
    private ArticleSearchResponse response;

    public ArticleSearchResponse getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "response=" + response +
                '}';
    }
}
