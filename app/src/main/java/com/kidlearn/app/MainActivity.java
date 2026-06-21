package com.kidlearn.app;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    AdView bannerAd;
    InterstitialAd interstitialAd;
    int pageCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, initializationStatus -> {});

        bannerAd = findViewById(R.id.bannerAd);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAd.loadAd(adRequest);

        InterstitialAd.load(this,
            "ca-app-pub-3403694718641998/5184929941",
            adRequest,
            new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(InterstitialAd ad) {
                    interstitialAd = ad;
                }
            });

        webView = findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                pageCount++;
                if (pageCount % 3 == 0 && interstitialAd != null) {
                    interstitialAd.show(MainActivity.this);
                    interstitialAd = null;
                }
            }
        });

        webView.loadUrl("https://ayuubgaafaa.github.io");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
