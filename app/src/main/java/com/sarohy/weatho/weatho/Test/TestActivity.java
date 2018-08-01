package com.sarohy.weatho.weatho.Test;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sarohy.weatho.weatho.R;

public class TestActivity extends AppCompatActivity {

    private WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        webView = findViewById(R.id.wv_anim);

        WebSettings webSetting = webView.getSettings();
        //webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");

        webView.setWebViewClient(new android.webkit.WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:myJavaScriptFunc(20,3,'Thunder Storm')");
            }
        });

    }
}
