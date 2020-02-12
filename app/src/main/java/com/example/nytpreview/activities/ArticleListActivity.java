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
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleListActivity extends BaseActivity implements OnArticleListener {

    private ArticleListViewModel mArticleListViewModel;
    private RecyclerView mRecyclerView;
    private ArticleRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        mRecyclerView = findViewById(R.id.article_list);
        mArticleListViewModel = new ViewModelProvider(this).get(ArticleListViewModel.class);

        subscribeObservers();
        initRecyclerView();
        createSearchView();
        setToolbarScrollingEnabled(false);
    }

    @Override
    protected void onResume() {

        showHelpText();
        super.onResume();
    }

    // Show help text if not (results or no results text)
    private void showHelpText() {

        if (findViewById(R.id.no_results_text).getVisibility() != View.VISIBLE &&
                ( mArticleListViewModel.getArticles().getValue() == null ||
                mArticleListViewModel.getArticles().getValue().size() == 0 ) ) {
            findViewById(R.id.help_text).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.help_text).setVisibility(View.GONE);
        }
    }

    // Sets articles up to automatically update with live data
    private void subscribeObservers() {

        mArticleListViewModel.getArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                showProgressBar(false);
                TextView noResults = findViewById(R.id.no_results_text);
                if (articles != null && articles.size() > 0) {
                    mAdapter.setArticles(articles);
                    noResults.setVisibility(View.INVISIBLE);
                    setToolbarScrollingEnabled(true);
                } else {
                    mAdapter.setArticles(null);
                    noResults.setVisibility(View.VISIBLE);
                    setToolbarScrollingEnabled(false);
                }
            }
        });
    }

    // Sets up recycler view and has callback to see if no more articles are available
    private void initRecyclerView() {

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
    // Handles article clicks and pushes web view activity with URL for specific article
    public void onArticleClick(int position) {

        if (isInternetAvailable()) {
            final String webUrl = mAdapter.getArticleUrl(position);
            Intent webViewPage = new Intent(this, ArticleWebViewActivity.class);
            webViewPage.putExtra(ArticleWebViewActivity.BUNDLE_URL, webUrl);
            startActivity(webViewPage);
        }
    }

    // Creates search text and handles queries being executed
    private void createSearchView() {

        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (isInternetAvailable()) {
                    // Hide help text and start progress spinner
                    findViewById(R.id.help_text).setVisibility(View.GONE);
                    showProgressBar(true);

                    // Search for the first page with this query
                    mArticleListViewModel.searchArticlesApi(query, 1);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
    }

    // Checks to see if internet is available, pushes toast if not
    private boolean isInternetAvailable() {

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            Toast.makeText(this, getString(R.string.disconnected), Toast.LENGTH_SHORT).show();
        }
        return isConnected;
    }

    // Allows toolbar to hide when scrolling with articles
    private void setToolbarScrollingEnabled(boolean enableScroll) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        if (enableScroll) {
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                    | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        } else {
            params.setScrollFlags(0);
        }
    }
}
