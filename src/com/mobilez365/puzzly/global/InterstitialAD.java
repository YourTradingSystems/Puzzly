package com.mobilez365.puzzly.global;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.PuzzlesApplication;

/**
 * An {@link android.app.Activity} that requests and can display an InterstitialAd.
 */
public class InterstitialAD {

    protected static InterstitialAd interstitialAd;

    public static void initFullPageAd(final Context _context){
        interstitialAd = new InterstitialAd(_context);
        interstitialAd.setAdUnitId(_context.getString(R.string.interstitialAdUnitId));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadAd(_context);
            }
        });
    }

    public static void loadAd(Context _context) {
        boolean showBanner = !AppHelper.isAdsDisabled(_context);
        if (showBanner && interstitialAd != null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest);
        }
    }

    public static void showFullAD(Context _context) {
        boolean showBanner = !AppHelper.isAdsDisabled(_context);
        PuzzlesApplication app = (PuzzlesApplication) _context.getApplicationContext();
        boolean needToShowAd =  app.isNeedToShowAd();
        if (showBanner && needToShowAd && interstitialAd != null)
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
                ((PuzzlesApplication) _context.getApplicationContext()).setNeedToShowAd(false);
            }
    }

}
