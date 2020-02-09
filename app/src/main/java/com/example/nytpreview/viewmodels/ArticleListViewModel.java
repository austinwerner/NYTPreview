package com.example.nytpreview.viewmodels;

import com.example.nytpreview.models.Article;
import com.example.nytpreview.repositories.ArticleRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ArticleListViewModel extends ViewModel {

    private ArticleRepository mArticleReposity;

    public ArticleListViewModel() {
        mArticleReposity = ArticleRepository.getInstance();
    }

    public LiveData<List<Article>> getArticles() {
        return mArticleReposity.getArticles();
    }

    public void searchArticlesApi(String query, int pageNumber){
        mArticleReposity.searchArticlesApi(query, pageNumber);
    }
}
