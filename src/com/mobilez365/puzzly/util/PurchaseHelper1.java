package com.mobilez365.puzzly.util;

import android.content.Context;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import android.app.Activity;
import android.content.Intent;
import com.mobilez365.puzzly.screens.SettingsActivity;
//import android.util.Log;


public class PurchaseHelper1{

    // id вашей покупки из админки в Google Play
    static final String SKU_ADS_DISABLE = "purchasefordisablingadvertising";

    // public key из админки Google Play
    public static String base64PublicKey;
    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;
    static IabHelper mHelper;
    private static Context context;


    private static void billingInit() {
        mHelper = new IabHelper(context, base64PublicKey);

        // включаем дебагинг (в релизной версии ОБЯЗАТЕЛЬНО выставьте в false)
        mHelper.enableDebugLogging(false);

        // инициализируем; запрос асинхронен
        // будет вызван, когда инициализация завершится
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    return;
                }

                // чекаем уже купленное
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    // Слушатель для востановителя покупок.
    private static  IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        private static final String TAG = "QueryInventoryFinishedListener";

        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {
            if (result.isFailure()) {
                return;
            }


			/*
			 * Проверяются покупки. Обратите внимание, что надо проверить каждую
			 * покупку, чтобы убедиться, что всё норм! см.
			 * verifyDeveloperPayload().
			 */

            Purchase purchase = inventory.getPurchase(SKU_ADS_DISABLE);
            AppHelper.savePurchase(context, AppHelper.Purchase.DISABLE_ADS, purchase != null && verifyDeveloperPayload(purchase));

        }
    };

    /** Verifies the developer payload of a purchase. */
    static boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
		/*
		 * TODO: здесь необходимо свою верификацию реализовать Хорошо бы ещё с
		 * использованием собственного стороннего сервера.
		 */

        return true;
    }

    // Прокает, когда покупка завершена
    private static  IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                return;
            }

            if (purchase.getSku().equals(SKU_ADS_DISABLE)) {
                // сохраняем в настройках, что отключили рекламу
                AppHelper.savePurchase(context, AppHelper.Purchase.DISABLE_ADS, true);
                // отключаем рекламу
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

        mHelper = new IabHelper(_context, base64PublicKey);

        // включаем дебагинг (в релизной версии ОБЯЗАТЕЛЬНО выставьте в false)
        mHelper.enableDebugLogging(false);

        // инициализируем; запрос асинхронен
        // будет вызван, когда инициализация завершится
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    return;
                }

                // чекаем уже купленное
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }
}
