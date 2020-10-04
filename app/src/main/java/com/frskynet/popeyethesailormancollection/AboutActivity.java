package com.frskynet.popeyethesailormancollection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AboutActivity extends Activity {
    private AdView adView;
    private BannerAdEvents bannerAdEvents;
    private InterstitialAd mInterstitialVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        adView = findViewById(R.id.about_activity_ad);
        MobileAds.initialize(this, this.getString(R.string.AD_APP_ID)); //App Id from string values
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
        bannerAdEvents = new BannerAdEvents();
        bannerAdEvents.loadAd(this.getApplicationContext(), adView);


        mInterstitialVideoAd = new InterstitialAd(this);
        mInterstitialVideoAd.setAdUnitId("ca-app-pub-3940256099942544/8691691433"); // Interstitial Video
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialVideoAd.loadAd(request);
        interstitialAdEvent();
    }

    public void goToHomeBtnClick(View view) {
        if(mInterstitialVideoAd.isLoaded()) {
            mInterstitialVideoAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
            Toast.makeText(this, "The interstitial wasn't loaded yet.", Toast.LENGTH_LONG).show();
        }
    }

    public void backToHomeEvent() {
        Intent intent = new Intent(AboutActivity.this, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private void interstitialAdEvent() {
        mInterstitialVideoAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
//                Toast.makeText(getApplicationContext(), "Ad is loaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
//                Toast.makeText(getApplicationContext(), "Your Ad failed to load", Toast.LENGTH_LONG).show();
                backToHomeEvent();
            }

            @Override
            public void onAdOpened() {
//                Toast.makeText(getApplicationContext(), "Ad is opened", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdClicked() {
//                Toast.makeText(getApplicationContext(), "Ad is clicked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLeftApplication() {
//                Toast.makeText(getApplicationContext(), "You have left ad application", Toast.LENGTH_LONG).show();
                backToHomeEvent();
            }

            @Override
            public void onAdClosed() {
//                Toast.makeText(getApplicationContext(), "You closed your Ad", Toast.LENGTH_LONG).show();
                mInterstitialVideoAd.loadAd(new AdRequest.Builder().build());
                backToHomeEvent();
            }
        });
    }
}
