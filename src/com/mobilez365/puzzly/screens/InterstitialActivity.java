package com.mobilez365.puzzly.screens;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;

/**
 * An {@link android.app.Activity} that requests and can display an InterstitialAd.
 */
public class InterstitialActivity extends RestartActivty {

    protected String AD_UNIT_ID;
    protected InterstitialAd interstitialAd;

    @Override
    public void onResume() {
        super.onResume();
        if (!AppHelper.isAdsDisabled(getApplicationContext())) {
            AD_UNIT_ID = getApplicationContext().getResources().getString(R.string.interstitialAdUnitId);
            setupAD();
        }
    }

    @Override
    protected void onStop() {
        if (!AppHelper.isAdsDisabled(getApplicationContext())) showInterstitial();
        super.onStop();
    }

    protected void setupAD() {
        interstitialAd = new InterstitialAd(getApplicationContext());
        interstitialAd.setAdUnitId(AD_UNIT_ID);
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }

    protected void showInterstitial() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (status == ConnectionResult.SUCCESS) {
            if (interstitialAd.isLoaded()) interstitialAd.show();
        }

    }

}
