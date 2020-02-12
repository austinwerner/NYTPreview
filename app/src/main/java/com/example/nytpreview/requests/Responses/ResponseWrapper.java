package com.example.nytpreview.requests.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseWrapper {
    @SerializedName("response")
    @Expose
    private ArticleSearchResponse mResponses;

    public ArticleSearchResponse getResponse() {
        return mResponses;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "response=" + mResponses +
                '}';
    }
}
