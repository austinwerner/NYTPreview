package com.example.nytpreview.requests;

import com.example.nytpreview.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static ArticleApi articleApi = retrofit.create(ArticleApi.class);

    public static ArticleApi getArticleApi(){
        return articleApi;
    }
}