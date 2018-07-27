package com.sarohy.weatho.weatho.Test;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sarohy.weatho.weatho.R;

public class TestActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        webView = (WebView)findViewById(R.id.wv_anim);

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
    private class WebViewClient extends android.webkit.WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
