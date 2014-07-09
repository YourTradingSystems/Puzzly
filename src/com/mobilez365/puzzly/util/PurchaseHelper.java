package com.mobilez365.puzzly.util;

import android.content.Context;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import android.app.Activity;
import android.content.Intent;
import com.mobilez365.puzzly.screens.SettingsActivity;
//import android.util.Log;


public class PurchaseHelper {

    static final String SKU_ADS_DISABLE = "purchasefordisablingadvertising";

    public static String base64PublicKey;
    static final int RC_REQUEST = 10001;
    private static volatile IabHelper mHelper;
    private static Context context;

    private static  IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        private static final String TAG = "QueryInventoryFinishedListener";

        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {
            if (result.isFailure()) {
                return;
            }


            Purchase purchase = inventory.getPurchase(SKU_ADS_DISABLE);
            AppHelper.savePurchase(context, AppHelper.Purchase.DISABLE_ADS, purchase != null && verifyDeveloperPayload(purchase));

        }
    };
    static boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
		/*
		 * TODO: здесь необходимо свою верификацию реализовать Хорошо бы ещё с
		 * использованием собственного стороннего сервера.
		 */

        return true;
    }

    private static  IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                return;
            }

            if (purchase.getSku().equals(SKU_ADS_DISABLE)) {
                AppHelper.savePurchase(context, AppHelper.Purchase.DISABLE_ADS, true);
            }

        }
    };

    public static void purchaseAdSubscription(Activity activity) {
        if (!AppHelper.isAdsDisabled(context)) {
			/*
			 * для безопасности сгенерьте payload для верификации. В данном
			 * примере просто пустая строка юзается. Но в реальном приложение
			 * подходить к этому шагу с умом.
			 */
            String payload = "";

            mHelper.launchPurchaseFlow(activity, SKU_ADS_DISABLE, RC_REQUEST, mPurchaseFinishedListener, payload);
        }
    }
    public static boolean handleResult(int requestCode, int resultCode, Intent data) {
        return mHelper.handleActivityResult(requestCode, resultCode, data);
    }

    public static void DestroyHelper() {
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }
    public static boolean isHelperInit(){
        if(mHelper != null)
            return true;
        return false;
    }

    public static void initPurchaseWorker(Context _context) {
        context = _context;
        base64PublicKey = _context.getResources().getString(R.string.purchaseKey);

        mHelper = getIabHelper();

        mHelper.enableDebugLogging(false);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    return;
                }
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }
    public static IabHelper getIabHelper() {
        IabHelper localInstance = mHelper;
        if (localInstance == null) {
            synchronized (PurchaseHelper.class) {
                localInstance = mHelper;
                if (localInstance == null) {
                    mHelper = localInstance = new IabHelper(context, base64PublicKey);
                }
            }
        }
        return localInstance;
    }
}
