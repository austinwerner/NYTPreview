package com.example.nytpreview.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nytpreview.Models.Article;
import com.example.nytpreview.R;
import com.example.nytpreview.Requests.ArticleApi;
import com.example.nytpreview.Requests.Responses.ArticleSearchResponse;
import com.example.nytpreview.Requests.ServiceGenerator;
import com.example.nytpreview.Util.SecretConstants;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleListActivity extends BaseActivity {

    private static final String TAG = "ArticleListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        showProgressBar(false);

        findViewById(R.id.test_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testRetrofit();
            }
        });
    }

    private void testRetrofit()
    {
        Log.d(TAG, "testRetrofit: Pressed");

        ArticleApi articleApi = ServiceGenerator.getArticleApi();

        Call<ArticleSearchResponse> responseCall = articleApi
                .searchArticle(
                        SecretConstants.API_KEY,
                        "Kansas City",
                        "1",
                        "neweset"
                );

        responseCall.enqueue(new Callback<ArticleSearchResponse>() {

            @Override
            public void onResponse(Call<ArticleSearchResponse> call, Response<ArticleSearchResponse> response) {
                Log.d(TAG, "onResponse: Server Response: " + response.toString());
                if (response.code() == 200) {
                    Article article = response.body().getArticle();
                    Log.d(TAG, "onResponse: " + article.toString());
                }
                else{
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ArticleSearchResponse> call, Throwable t) {
                Log.d(TAG, "onResponse: ERROR: " + t.getMessage());
            }
        });
    }
}
