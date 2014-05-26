package com.mobilez365.puzzly.screens;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * An {@link android.app.Activity} that requests and can display an InterstitialAd.
 */
public class InterstitialActivity extends RestartActivty {

    protected final String AD_UNIT_ID = "ca-app-pub-8370463222730338/1339125208";
    protected InterstitialAd interstitialAd;

    @Override
    public void onResume() {
        super.onResume();
        setupAD();
    }

    @Override
    protected void onStop() {
        showInterstitial();
        super.onStop();
    }

    protected void setupAD(){
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(AD_UNIT_ID);
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }

    protected void showInterstitial() {
        if (interstitialAd.isLoaded()) interstitialAd.show();
    }

}
