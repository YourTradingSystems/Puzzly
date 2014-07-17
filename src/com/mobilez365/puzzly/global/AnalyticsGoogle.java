package com.mobilez365.puzzly.global;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.mobilez365.puzzly.global.AppHelper;

/**
 * Created by andrewtivodar on 07.07.2014.
 */
public class AnalyticsGoogle {

    private static Tracker tracker;
    public final static String GOOGLE_TRACKING_ID = "UA-51080238-3";

    private static synchronized Tracker getTracker(Context context) {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
            tracker = analytics.newTracker(GOOGLE_TRACKING_ID);
            GoogleAnalytics.getInstance(context).setLocalDispatchPeriod(60);
        }

        return tracker;
    }

    public static void fireScreenEvent(Context context, String activity) {
        int isTrackingAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        boolean needToTrack = AppHelper.getAnalytics(context);
        if (isTrackingAvailable == ConnectionResult.SUCCESS && needToTrack) {
            Tracker t = getTracker(context);

            t.setScreenName(activity);

            t.send(new HitBuilders.AppViewBuilder().build());
        }

    }

    public static void fireSettingsEvent(Context context, String btn, String value) {
        int isTrackingAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        boolean needToTrack = AppHelper.getAnalytics(context);
        if (isTrackingAvailable == ConnectionResult.SUCCESS && needToTrack) {
            Tracker t = getTracker(context);

            t.send(new HitBuilders.EventBuilder()
                    .setCategory("Settings")
                    .setAction(btn)
                    .setLabel(value)
                    .build());
        }

    }

    public static void fireLevelStartedEvent(Context context, String level) {
        int isTrackingAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        boolean needToTrack = AppHelper.getAnalytics(context);
        if (isTrackingAvailable == ConnectionResult.SUCCESS && needToTrack) {
            Tracker t = getTracker(context);

            t.send(new HitBuilders.EventBuilder()
                    .setCategory("LevelStarted")
                    .setAction(level)
                    .build());
        }

    }

    public static void fireBonusLevelEndEvent(Context context, String level) {
        int isTrackingAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        boolean needToTrack = AppHelper.getAnalytics(context);
        if (isTrackingAvailable == ConnectionResult.SUCCESS && needToTrack) {
            Tracker t = getTracker(context);

            t.send(new HitBuilders.EventBuilder()
                    .setCategory("BonusLevelEnded")
                    .setAction(level)
                    .build());
        }

    }
}
