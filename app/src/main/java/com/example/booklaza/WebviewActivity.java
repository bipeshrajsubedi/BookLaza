package com.example.booklaza;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebviewActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        // action bar
        String title = getIntent().getExtras().getString("title");
        setTitle(title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initializing views
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress_webview);

        String url = getIntent().getExtras().getString("url");

        if (Config.checkNetworkStatus(WebviewActivity.this)) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    progressBar.setVisibility(View.VISIBLE);
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                    super.onPageFinished(view, url);
                }
            });

            webView.loadUrl(url);
        }
        else {
            Toast.makeText(WebviewActivity.this, "No internet. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}