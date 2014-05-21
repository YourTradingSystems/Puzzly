package com.mobilez365.puzzly.screens;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.mobilez365.puzzly.R;

/**
 * An {@link android.app.Activity} that requests and can display an InterstitialAd.
 */
public class InterstitialActivity extends Activity {

    protected final String AD_UNIT_ID = getResources().getString(R.string.interstitialAdUnitId);
    protected InterstitialAd interstitialAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE")
                .build();
        interstitialAd.loadAd(adRequest);
    }

    protected void showInterstitial() {
        if (interstitialAd.isLoaded()) interstitialAd.show();
    }

}
