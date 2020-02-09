package com.example.nytpreview.Repositories;

import com.example.nytpreview.Models.Article;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ArticleRepository {

    private static ArticleRepository instance;
    private MutableLiveData<List<Article>> mRecipes;

    public static ArticleRepository getInstance(){
        if(instance == null){
            instance = new ArticleRepository();
        }
        return instance;
    }

    private ArticleRepository() {
        mRecipes = new MutableLiveData<>();
    }

    public LiveData<List<Article>> getArticles(){
        return mRecipes;
    }
}