package org.vedibarta.app.ui;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.vedibarta.app.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.webPageSplash)
    WebView webviewSplash;
    int index = 0;
    Intent returnIntent;

    @SuppressLint("SetJavaScriptEnabled")
    @JavascriptInterface
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        ButterKnife.bind(this);
        returnIntent = new Intent(this, MainActivity.class);
        webviewSplash.setInitialScale(1);
        WebSettings settings = webviewSplash.getSettings();
        webviewSplash.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                returnIntent.putExtra("log" + index, "splash");
                index++;
                returnIntent.putExtra("log" + index,
                        "url: " + webviewSplash.getUrl());
                index++;

            }
        });

        settings.setJavaScriptEnabled(true);
        // settings.setBuiltInZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setSupportMultipleWindows(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        File dir = new File(getFilesDir() + File.separator + "HtmlFiles");
        dir.mkdirs();
        String fileName = "splash.html";
        File myFile = new File(dir, fileName);
        if (myFile.length() > 100) {
            webviewSplash.loadUrl("file:///" + myFile.getAbsolutePath());
            returnIntent.putExtra("log" + index, "splash");
            index++;
            returnIntent.putExtra("log" + index,
                    "÷åáõ îñåô÷: " + myFile.getPath());
            index++;
        } else {
            webviewSplash.loadUrl("file:///android_asset/splash.html");
            returnIntent.putExtra("log" + index, "splash");
            index++;
            returnIntent.putExtra("log" + index, "÷åáõ îñåô÷: "
                    + "file:///android_asset/splash.html");
            index++;
        }

        webviewSplash.addJavascriptInterface(new WebAppInterface(), "orly");

    }

    private class WebAppInterface {
        @JavascriptInterface
        public void close() {
            returnIntent.putExtra("index", index);
            startActivity(returnIntent);
        }

        @JavascriptInterface
        public void rateApp() {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id="
                                + getPackageName())));
            }
        }
    }
}