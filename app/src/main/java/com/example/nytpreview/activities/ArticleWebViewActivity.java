package com.example.nytpreview.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.nytpreview.R;

public class ArticleWebViewActivity extends AppCompatActivity {

    final public static String BUNDLE_URL = "ARTICLE_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_web_view);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString(BUNDLE_URL);

        if( url != null ) {
            loadUrl(url);
        }
        else {
            finish();
        }
    }

    private void loadUrl(String url) {
        WebView webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
