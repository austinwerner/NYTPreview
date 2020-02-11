package com.example.nytpreview.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.nytpreview.R;
import com.example.nytpreview.adapters.ArticleRecyclerAdapter;
import com.example.nytpreview.adapters.OnArticleListener;
import com.example.nytpreview.models.Article;
import com.example.nytpreview.viewmodels.ArticleListViewModel;

import org.w3c.dom.Text;

import java.util.List;

import androidx.appcompat.widget.SearchView;
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

    private boolean mSearched;
    private TextView mNoResultsText;

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

        mSearched = false;
        mNoResultsText = findViewById(R.id.no_results_text);
    }

    private void subscribeObservers(){

        mArticleListViewModel.getArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                showProgressBar(false);
                if(articles != null && articles.size() > 0) {
                    mAdapter.setArticles(articles);
                    mNoResultsText.setVisibility(View.INVISIBLE);
                }
                else {
                    mAdapter.setArticles(null);
                    mNoResultsText.setVisibility(View.VISIBLE);
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
        Log.d(TAG, "article " + position + "clicked" );
    }

    private void createSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                // Search for the first page with this query
                showProgressBar(true);
                mArticleListViewModel.searchArticlesApi(query, 1);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
    }
}
