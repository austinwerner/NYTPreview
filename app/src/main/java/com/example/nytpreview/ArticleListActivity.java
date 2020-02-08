package com.example.nytpreview;

import android.os.Bundle;

public class ArticleListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        showProgressBar(true);
    }
}
