package com.example.nytpreview.repositories;

import com.example.nytpreview.models.Article;
import com.example.nytpreview.requests.ArticleApiClient;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ArticleRepository {

    private static ArticleRepository instance;
    private ArticleApiClient mArticleApiClient;
    private String mQuery;
    private int mPageNumber;

    public static ArticleRepository getInstance(){
        if(instance == null){
            instance = new ArticleRepository();
        }
        return instance;
    }

    private ArticleRepository() {
        mArticleApiClient = ArticleApiClient.getInstance();
    }

    public LiveData<List<Article>> getArticles(){
        return mArticleApiClient.getArticles();
    }

    public void searchArticlesApi(String query, int pageNumber){
        if(pageNumber == 0){
            pageNumber = 1;
        }
        mQuery = query;
        mPageNumber = pageNumber;
        mArticleApiClient.searchArticlesApi(mQuery, mPageNumber);
    }

    public void searchNextPage() {
        searchArticlesApi(mQuery, mPageNumber + 1);
    }

    public void cancelRequest() {
        mArticleApiClient.cancelRequest();
    }
}