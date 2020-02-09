package com.example.nytpreview.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nytpreview.Models.Article;
import com.example.nytpreview.R;
import com.example.nytpreview.Requests.ArticleApi;
import com.example.nytpreview.Requests.Responses.ResponseWrapper;
import com.example.nytpreview.Requests.ServiceGenerator;
import com.example.nytpreview.Util.SecretConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        Call<ResponseWrapper> responseCall = articleApi
                .searchArticle(
                        SecretConstants.API_KEY,
                        "Kansas City",
                        "1",
                        "newest"
                );

        responseCall.enqueue(new Callback<ResponseWrapper>() {

            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                Log.d(TAG, "onResponse: Server Response: " + response.toString());
                if (response.code() == 200) {
                    List<Article> articles = new ArrayList<>(response.body().getResponse().getArticles());
                    Log.d(TAG, "onResponse: " + articles.get(0).toString());
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
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                Log.d(TAG, "onResponse: ERROR: " + t.getMessage());
            }
        });
    }
}
