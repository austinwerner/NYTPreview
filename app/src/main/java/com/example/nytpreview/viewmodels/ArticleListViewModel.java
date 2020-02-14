package com.example.nytpreview.viewmodels;

import com.example.nytpreview.models.Article;
import com.example.nytpreview.repositories.ArticleRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


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

    public void searchArticlesApi(String query, int pageNumber) {
        
        mArticleReposity.searchArticlesApi(query, pageNumber);
    }

    public void searchNextPage() {

        if(!mIsPerformingQuery ) {
            mArticleReposity.searchNextPage();
        }
    }

    public boolean articlesAvailable() {

        return mArticleReposity.getArticles().getValue() != null &&
                mArticleReposity.getArticles().getValue().size() != 0;
    }
}
