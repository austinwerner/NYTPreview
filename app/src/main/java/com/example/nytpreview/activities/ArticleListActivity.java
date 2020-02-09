package com.example.nytpreview.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nytpreview.R;
import com.example.nytpreview.models.Article;
import com.example.nytpreview.viewmodels.ArticleListViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ArticleListActivity extends BaseActivity {

    private static final String TAG = "ArticleListActivity";

    private ArticleListViewModel mArticleListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        showProgressBar(false);

        mArticleListViewModel = new ViewModelProvider(this).get(ArticleListViewModel.class);

        subscribeObservers();

        findViewById(R.id.test_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testRetrofit();
            }
        });
    }

    private void subscribeObservers(){

        mArticleListViewModel.getArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if(articles != null){
                    for( Article article : articles ) {
                        Log.d( TAG, article.toString() );
                    }
                }
            }
        });
    }

    private void testRetrofit()
    {
        Log.d(TAG, "testRetrofit: Pressed");

        mArticleListViewModel.searchArticlesApi("Kansas City", 1);
    }
}
