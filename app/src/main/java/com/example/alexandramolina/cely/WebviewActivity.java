package com.example.alexandramolina.cely;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewActivity extends AppCompatActivity {

    WebView webView;
    //String link = "http://www.bbc.com/news";
    private static final String EXTRA = "com.example.alexandramolina.cely";

    public static void start(Context context, String link) {
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra(EXTRA, link);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Intent intent = this.getIntent();
        String l = intent.getStringExtra(EXTRA);

        webView = findViewById(R.id.webview);

        webView.loadUrl(l);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {
                viewx.loadUrl(urlx);
                return false;
            }
        });
    }
}
