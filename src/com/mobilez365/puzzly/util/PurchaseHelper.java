/* Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mobilez365.puzzly.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.mobilez365.puzzly.R;


public class PurchaseHelper {

    static final String SKU_AD_SUBSCRIPTION = "ad_subscription";
    static final int RC_REQUEST = 10001;
    static IabHelper mHelper;

    private static IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {

            if (mHelper == null) return;
            if (result.isFailure()) return;
            if (!verifyDeveloperPayload(purchase)) return;

            if (purchase.getSku().equals(SKU_AD_SUBSCRIPTION)) {
                // TODO add to shared

            }
        }
    };

    private static IabHelper.OnIabSetupFinishedListener mOnIabSetupFinishedListener =  new IabHelper.OnIabSetupFinishedListener() {
        public void onIabSetupFinished(IabResult result) {

            if (!result.isSuccess()) return;
            if (mHelper == null) return;

            mHelper.queryInventoryAsync(mGotInventoryListener);
        }
    };

    private static IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

            if (mHelper == null) return;
            if (result.isFailure()) return;

            boolean mAdSubscription;

            Purchase infiniteGasPurchase = inventory.getPurchase(SKU_AD_SUBSCRIPTION);
            mAdSubscription = (infiniteGasPurchase != null &&
                    verifyDeveloperPayload(infiniteGasPurchase));
            if (mAdSubscription) ;
            // TODO add to shared

        }
    };

    public static void initPurchaseWorker(Context context) {

        String base64EncodedPublicKey = context.getResources().getString(R.string.purchaseKey);
        mHelper = new IabHelper(context, base64EncodedPublicKey);
        mHelper.startSetup(mOnIabSetupFinishedListener);
    }

    public static void purchaseAdSubscription(Context context) {
        if (!mHelper.subscriptionsSupported()) return;

        String payload = "";

        mHelper.launchPurchaseFlow((Activity) context,
                SKU_AD_SUBSCRIPTION, IabHelper.ITEM_TYPE_SUBS,
                RC_REQUEST, mPurchaseFinishedListener, payload);
    }

    public static boolean handleResult(int requestCode, int resultCode, Intent data) {
        return mHelper.handleActivityResult(requestCode, resultCode, data);
    }

   private static boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
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

}
