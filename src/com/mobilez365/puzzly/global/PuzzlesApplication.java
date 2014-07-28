package com.mobilez365.puzzly.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import com.mobilez365.puzzly.screens.*;

/**
 * Created by andrewtivodar on 17.07.2014.
 */
public class PuzzlesApplication extends Application {

    private final ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (appStatusChecking != null) return;

            Intent i = new Intent(activity, MenuActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            activity.finish();
        }

        @Override
        public void onActivityStarted(Activity activity) {
            activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            boolean isMenuActivity = activity instanceof MenuActivity || activity instanceof SplashActivity ||
                    activity instanceof SettingsActivity || activity instanceof ChoosePuzzleActivity;
            SoundManager.playBackgroundMusic(getApplicationContext(), isMenuActivity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            isActivityVisible = true;
            if(isApplicationInBackground) {
                isApplicationInBackground = false;
                SoundManager.resumeBackgroundMusic();
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            isActivityVisible = false;
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (!isActivityVisible) {
                isApplicationInBackground = true;
                SoundManager.pauseBackgroundMusic();
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    private int startedGamesCount;
    private Integer appStatusChecking;
    private boolean isApplicationInBackground = false;
    private boolean isActivityVisible;
    private boolean needToShowAd = false;

    public Point getScreenSize() {
        if(screenSize == null) {
            Point size = new Point();
            Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            display.getSize(size);
            screenSize = size;
        }
        return screenSize;
    }

    private Point screenSize;

    @Override
    public void onCreate() {
        super.onCreate();
        appStatusChecking = 1;
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        InterstitialAD.initFullPageAd(getApplicationContext());
        InterstitialAD.loadAd(getApplicationContext());
    }

    public final void increaseStartedGamesCount() {
        startedGamesCount ++;
    }

    public final int getStartedGamesCount() {
        return startedGamesCount;
    }

    public boolean isNeedToShowAd() {
        return needToShowAd;
    }

    public void setNeedToShowAd(boolean needToShowAd) {
        this.needToShowAd = needToShowAd;
    }

}
