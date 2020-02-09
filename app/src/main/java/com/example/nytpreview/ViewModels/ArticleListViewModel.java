package com.example.nytpreview.ViewModels;

import com.example.nytpreview.Models.Article;
import com.example.nytpreview.Repositories.ArticleRepository;

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
}
