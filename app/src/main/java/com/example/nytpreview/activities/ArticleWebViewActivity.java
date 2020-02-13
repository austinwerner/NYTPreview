package com.example.nytpreview.activities;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.nytpreview.R;

public class ArticleWebViewActivity extends BaseActivity {

    final public static String BUNDLE_URL = "ARTICLE_URL";

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_web_view);
        setupToolbar();

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString(BUNDLE_URL);
        if (url != null) {
            mUrl = url;
            loadUrl(url);
        }
        else {
            // Invalid URL, pop the page
            finish();
        }

        // Start progress bar when page start so the time
        // before the page starts loading shows the spinner
        showProgressBar(true);
    }

    // Setup toolbar with share icon
    private void setupToolbar() {

        Toolbar toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    // Loads URL to webview
    private void loadUrl(String url) {

        WebView webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    // Handles webview callbacks
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // Only show progress spinner before page appears
            showProgressBar(false);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.article_share_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    // Handles the share button being selected
    public boolean onOptionsItemSelected(MenuItem item) {

        shareLink();
        return super.onOptionsItemSelected(item);
    }

    // Opens sharing options for URL
    private void shareLink() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mUrl);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
