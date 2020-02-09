package com.example.nytpreview.Requests;

import com.example.nytpreview.Requests.Responses.ArticleSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleApi {

    // Search for articles
    @GET("api/get")
    Call<ArticleSearchResponse> searchArticle(
            @Query("key") String key,

            @Query("q") String query,
            @Query("page") String page,
            @Query("sort") String sort
    );
}
