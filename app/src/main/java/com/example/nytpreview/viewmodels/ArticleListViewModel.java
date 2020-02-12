package com.example.nytpreview.viewmodels;

import android.util.Log;

import com.example.nytpreview.models.Article;
import com.example.nytpreview.repositories.ArticleRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ArticleListViewModel extends ViewModel {

    private ArticleRepository mArticleReposity;
    private boolean mIsPerformingQuery;

    public ArticleListViewModel() {
        mArticleReposity = ArticleRepository.getInstance();
        mIsPerformingQuery = false;
    }

    public LiveData<List<Article>> getArticles() {
        return mArticleReposity.getArticles();
    }

    public void searchArticlesApi(String query, int pageNumber){
        mArticleReposity.searchArticlesApi(query, pageNumber);
    }

    public void searchNextPage(){
        Log.d(TAG, "searchNextPage: called.");
        if(!mIsPerformingQuery ) {
            mArticleReposity.searchNextPage();
        }
    }
}
