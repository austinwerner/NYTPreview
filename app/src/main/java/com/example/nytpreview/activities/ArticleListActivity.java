package com.example.nytpreview.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nytpreview.R;
import com.example.nytpreview.adapters.ArticleRecyclerAdapter;
import com.example.nytpreview.adapters.OnArticleListener;
import com.example.nytpreview.models.Article;
import com.example.nytpreview.viewmodels.ArticleListViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
        createSearchView();
    }

    private void subscribeObservers(){

        mArticleListViewModel.getArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                showProgressBar(false);
                TextView noResults = findViewById(R.id.no_results_text);
                if(articles != null && articles.size() > 0) {
                    mAdapter.setArticles(articles);
                    noResults.setVisibility(View.INVISIBLE);
                }
                else {
                    mAdapter.setArticles(null);
                    noResults.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initRecyclerView(){
        mAdapter = new ArticleRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                // If the recycler view can't be search any further, query next page
                if (!mRecyclerView.canScrollVertically(1)) {
                    showProgressBar(true);
                    mArticleListViewModel.searchNextPage();
                }
            }
        });
    }

    @Override
    public void onArticleClick(int position) {
        if(isInternetAvailable()) {
            final String webUrl = mAdapter.getArticleUrl(position);
            Intent webViewPage = new Intent(this, ArticleWebViewActivity.class);
            webViewPage.putExtra(ArticleWebViewActivity.BUNDLE_URL, webUrl);
            startActivity(webViewPage);
        }
    }

    private void createSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(isInternetAvailable()) {
                    // Search for the first page with this query
                    showProgressBar(true);
                    mArticleListViewModel.searchArticlesApi(query, 1);
                } else {
                    showDisconnectedToast();
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected) {
            showDisconnectedToast();
        }
        return isConnected;
    }

    private void showDisconnectedToast() {
        Toast.makeText(this, getString(R.string.disconnected), Toast.LENGTH_SHORT).show();
    }
}
