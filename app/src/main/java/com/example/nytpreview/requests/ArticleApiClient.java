package com.example.nytpreview.requests;

import android.util.Log;

import com.example.nytpreview.executors.AppExecutors;
import com.example.nytpreview.models.Article;
import com.example.nytpreview.requests.Responses.ResponseWrapper;
import com.example.nytpreview.util.SecretConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.nytpreview.util.Constants.NETWORK_TIMEOUT;

public class ArticleApiClient {

    private static final String TAG = "ArticleApiClient";

    private static ArticleApiClient instance;
    private MutableLiveData<List<Article>> mArticles;
    private RetrieveArticlesRunnables mRetrieveArticlesRunnable;

    public static ArticleApiClient getInstance(){
        if(instance == null){
            instance = new ArticleApiClient();
        }
        return instance;
    }

    private ArticleApiClient() {
        mArticles = new MutableLiveData<>();
    }

    public LiveData<List<Article>> getArticles(){
        return mArticles;
    }

    public void searchArticlesApi(String query, int pageNumber){
        if(mRetrieveArticlesRunnable != null){
            mRetrieveArticlesRunnable = null;
        }
        mRetrieveArticlesRunnable = new RetrieveArticlesRunnables(query, pageNumber);
        final Future handler = AppExecutors.get().networkIO().submit(mRetrieveArticlesRunnable);

        // Set a timeout for the data refresh
        AppExecutors.get().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know it timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveArticlesRunnables implements Runnable{

        private String query;
        private int pageNumber;
        private boolean cancelRequest;

        private RetrieveArticlesRunnables(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response response = getRecipes(query, pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<Article> list = new ArrayList<>(((ResponseWrapper)response.body()).getResponse().getArticles());
                    if(pageNumber == 1){
                        mArticles.postValue(list);
                    }
                    else{
                        List<Article> currentRecipes = mArticles.getValue();
                        currentRecipes.addAll(list);
                        mArticles.postValue(currentRecipes);
                    }
                }
                else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: error: " + error);
                    mArticles.postValue(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mArticles.postValue(null);
            }
        }

        private Call<ResponseWrapper> getRecipes(String query, int pageNumber){
            return ServiceGenerator.getArticleApi().searchArticle(
                    SecretConstants.API_KEY,
                    query,
                    String.valueOf(pageNumber),
                    "newest");
        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling the retrieval query");
            cancelRequest = true;
        }
    }
}
