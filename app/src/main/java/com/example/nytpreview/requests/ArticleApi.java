package com.example.nytpreview.requests;

import com.example.nytpreview.requests.Responses.ResponseWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleApi {

    // Search for articles
    @GET("svc/search/v2/articlesearch.json")
    Call<ResponseWrapper> searchArticle(
            @Query("api-key") String key,
            @Query("q") String query,
            @Query("page") String page,
            @Query("sort") String sort
    );
}
