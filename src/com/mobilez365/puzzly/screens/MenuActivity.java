package com.mobilez365.puzzly.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;
import com.mobilez365.puzzly.util.BackgroundSound;
import com.startad.lib.SADView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MenuActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rlMenuMainLayout_MS;
    private ImageView ivGameSimpleFill_MS;
    private ImageView ivGameSimpleReveal_MS;
    private ImageButton btnGameSettings_MS;
    private TextView tvGameAchievement_MS;
    private ImageView ivMenuLogo_MS;
    private LinearLayout llSubMenu_MS;
    private LinearLayout llMainMenu_MS;
    private RelativeLayout rlLeftBalloon_MS;
    private RelativeLayout rlRightBalloon_MS;
    private ImageView ivLeftHandTutorial_MS;
    private ImageView ivRightHandTutorial_MS;

    private BackgroundSound mBackgroundSound;
    private List<ImageView> mClouds = new ArrayList<ImageView>();

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        AppHelper.appStatus = 1;
        AppHelper.changeLanguage(this, AppHelper.getLocaleLanguage(this).name());

        super.onCreate(_savedInstanceState);
        AppHelper.setDefaultFont(this);

        if (AppHelper.getBackgroundSound() == null) {
            AppHelper.startBackgroundSound(this, Constans.MENU_BACKGROUND_MUSIC);
        }
        mBackgroundSound = AppHelper.getBackgroundSound();

        setContentView(R.layout.menu_screen);
        showBanner();
        findViews();
        setListeners();
        startAnimation();
        PuzzlesDB.addBasePuzzlesToDB(this);
    }

    @Override
    public void onClick(View _v) {
        if (_v.isClickable()) {
            btnGameSettings_MS.setClickable(false);
            ivGameSimpleReveal_MS.setClickable(false);
            ivGameSimpleFill_MS.setClickable(false);
            switch (_v.getId()) {

                case R.id.ivGameSimpleFill_MS:
                    startGame(0);
                    AppHelper.setLeftHandTutorial(this, true);
                    break;

                case R.id.ivGameSimpleReveal_MS:
                    startGame(1);
                    AppHelper.setRightHandTutorial(this, true);
                    break;

                case R.id.btnGameSettings_MS:
                    settings();
                    break;

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setGameAchievement(AppHelper.getGameAchievement(this));

        mBackgroundSound = AppHelper.getBackgroundSound();

        if (!AppHelper.isAppInBackground(this)) {
            if (mBackgroundSound != null && !mBackgroundSound.isPlay())
                mBackgroundSound.pause(false);
        }

        if (AppHelper.getLeftHandTutorial(this)) {
            ivLeftHandTutorial_MS.clearAnimation();
            ivLeftHandTutorial_MS.setVisibility(View.GONE);
        }

        if (AppHelper.getRightHandTutorial(this)) {
            ivRightHandTutorial_MS.clearAnimation();
            ivRightHandTutorial_MS.setVisibility(View.GONE);
        }


        if (btnGameSettings_MS != null) btnGameSettings_MS.setClickable(true);
        if (ivGameSimpleReveal_MS != null) ivGameSimpleReveal_MS.setClickable(true);
        if (ivGameSimpleFill_MS != null) ivGameSimpleFill_MS.setClickable(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (AppHelper.isAppInBackground(this) || AppHelper.isScreenOff(this)) {
            if (mBackgroundSound != null && mBackgroundSound.isPlay())
                mBackgroundSound.pause(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mBackgroundSound.pause(true);
    }

    private final void findViews() {
        rlMenuMainLayout_MS = (RelativeLayout) findViewById(R.id.rlMenuMainLayout_MS);
        ivGameSimpleFill_MS = (ImageView) findViewById(R.id.ivGameSimpleFill_MS);
        ivGameSimpleReveal_MS = (ImageView) findViewById(R.id.ivGameSimpleReveal_MS);
        btnGameSettings_MS = (ImageButton) findViewById(R.id.btnGameSettings_MS);
        tvGameAchievement_MS = (TextView) findViewById(R.id.tvGameAchievement_MS);
        ivMenuLogo_MS = (ImageView) findViewById(R.id.ivMenuLogo_MS);
        llSubMenu_MS = (LinearLayout) findViewById(R.id.llSubMenu_MS);
        llMainMenu_MS = (LinearLayout) findViewById(R.id.llMainMenu_MS);
        rlLeftBalloon_MS = (RelativeLayout) findViewById(R.id.rlLeftBalloon_MS);
        rlRightBalloon_MS = (RelativeLayout) findViewById(R.id.rlRightBalloon_MS);
        ivLeftHandTutorial_MS = (ImageView) findViewById(R.id.ivLeftHandTutorial_MS);
        ivRightHandTutorial_MS = (ImageView) findViewById(R.id.ivRightHandTutorial_MS);
    }

    private final void setListeners() {
        ivGameSimpleFill_MS.setOnClickListener(this);
        ivGameSimpleReveal_MS.setOnClickListener(this);
        btnGameSettings_MS.setOnClickListener(this);
    }

    private final void startAnimation() {
        Animation logoScaleAnimation = AnimationUtils.loadAnimation(this, R.anim.menu_logo);
        startCloudAnimation(0);
        startCloudAnimation(15000);

        if (!AppHelper.getLeftHandTutorial(this))
            ivLeftHandTutorial_MS.startAnimation(AnimationUtils.loadAnimation(this, R.anim.menu_hand_left));
        else
            ivLeftHandTutorial_MS.setVisibility(View.GONE);

        if (!AppHelper.getRightHandTutorial(this))
            ivRightHandTutorial_MS.startAnimation(AnimationUtils.loadAnimation(this, R.anim.menu_hand_right));
        else
            ivRightHandTutorial_MS.setVisibility(View.GONE);

        logoScaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMenuLogo_MS.setVisibility(View.INVISIBLE);
                llSubMenu_MS.setVisibility(View.VISIBLE);
                llMainMenu_MS.setVisibility(View.VISIBLE);

                for (ImageView img : mClouds)
                    img.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // nothing
            }
        });
        ivMenuLogo_MS.startAnimation(logoScaleAnimation);
        rlLeftBalloon_MS.startAnimation(AnimationUtils.loadAnimation(this, R.anim.menu_balloon_rotate_left));
        rlRightBalloon_MS.startAnimation(AnimationUtils.loadAnimation(this, R.anim.menu_balloon_rotate_right));

    }

    private final void startCloudAnimation(long _startOffset) {
        int cloudCount = 4;
        int cloud[] = new int[]{R.drawable.clouds1_icon, R.drawable.clouds2_icon, R.drawable.clouds3_icon, R.drawable.clouds4_icon};
        Random rand = new Random();
        float y = 50;

        for (int i = 0; i < cloudCount; i++) {
            y += rand.nextInt(50);
            Animation animation = new TranslateAnimation(-250, getResources().getDisplayMetrics().widthPixels, y, y);
            animation.setDuration(rand.nextInt(20000) + 30000);
            animation.setInterpolator(this, android.R.anim.linear_interpolator);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.RESTART);
            animation.setStartOffset(_startOffset);

            ImageView animCloud = new ImageView(this);
            animCloud.setImageResource(cloud[rand.nextInt(cloud.length)]);
            animCloud.startAnimation(animation);
            animCloud.setVisibility(View.GONE);
            rlMenuMainLayout_MS.addView(animCloud, 0);
            y += 50;
            mClouds.add(animCloud);
        }
    }

    private final void setGameAchievement(int _count) {
        tvGameAchievement_MS.setText("" + _count);
    }

    private final void startGame(int type) {

        AppHelper.startBackgroundSound(this, Constans.GAME_BACKGROUND_MUSIC);

        int passedGame = AppHelper.getPassedGames(this);
        if (passedGame != 3) {
            Intent gameIntent = new Intent(this, GameFillActivity.class);
            gameIntent.putExtra("type", type);
            startActivity(gameIntent);
        } else {
            Random r = new Random();
            int bonusLevelIndex = r.nextInt(3);

            Activity bonusLevelActivity = null;
            switch (bonusLevelIndex) {
                case 0:
                    bonusLevelActivity = new BonusLevelTreeActivity();
                    break;
                case 1:
                    bonusLevelActivity = new BonusLevelShakeActivity();
                    break;
                case 2:
                    bonusLevelActivity = new BonusLevelFlowerActivity();
                    break;
            }

            Intent gameIntent = new Intent(this, bonusLevelActivity.getClass());
            gameIntent.putExtra("type", type);
            startActivity(gameIntent);
        }

    }


    private final void settings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void showBanner() {
        switch (AppHelper.adware % 2){
            case 0:
                AdView adView = (AdView) findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
                break;
            case 1:
                SADView sadView = new SADView(this, getResources().getString(R.string.startADId));
                LinearLayout layout = (LinearLayout)findViewById(R.id.llBanner);
                layout.addView(sadView);
                sadView.loadAd(SADView.LANGUAGE_EN);
                break;
        }
        AppHelper.adware +=1;
    }
}
