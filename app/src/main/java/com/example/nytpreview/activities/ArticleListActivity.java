package com.example.nytpreview.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nytpreview.R;
import com.example.nytpreview.adapters.ArticleRecyclerAdapter;
import com.example.nytpreview.adapters.OnArticleListener;
import com.example.nytpreview.models.Article;
import com.example.nytpreview.viewmodels.ArticleListViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleListActivity extends BaseActivity implements OnArticleListener {

    private static final String TAG = "ArticleListActivity";

    private ArticleListViewModel mArticleListViewModel;
    private RecyclerView mRecyclerView;
    private ArticleRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        mRecyclerView = findViewById(R.id.article_list);

        showProgressBar(false);

        mArticleListViewModel = new ViewModelProvider(this).get(ArticleListViewModel.class);

        subscribeObservers();
        initRecyclerView();
        testRetrofit();

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
                mAdapter.setArticles(articles);
            }
        });
    }

    private void initRecyclerView(){
        mAdapter = new ArticleRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void testRetrofit()
    {
        Log.d(TAG, "testRetrofit: Pressed");

        mArticleListViewModel.searchArticlesApi("Trump", 1);
    }

    @Override
    public void onArticleClick(int position) {
        Log.d(TAG, "article " + position + "clicked" );
    }
}
