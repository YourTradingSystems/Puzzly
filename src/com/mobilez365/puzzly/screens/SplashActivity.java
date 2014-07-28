package com.mobilez365.puzzly.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;

/**
 * Created by andrewtivodar on 22.07.2014.
 */
public class SplashActivity extends Activity {

    Animation logoScaleAnimation;

    private final Animation.AnimationListener logoAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            // nothing
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            startActivity(new Intent(SplashActivity.this, MenuActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // nothing
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PuzzlesDB.addBasePuzzlesToDB(getApplicationContext());

        ImageView ivMenuLogo_MS = (ImageView) findViewById(R.id.ivMenuLogo_AS);
        logoScaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_logo);
        logoScaleAnimation.setFillAfter(true);

        logoScaleAnimation.setAnimationListener(logoAnimationListener);
        ivMenuLogo_MS.startAnimation(logoScaleAnimation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(logoScaleAnimation != null) {
            logoScaleAnimation.cancel();
            logoScaleAnimation = null;
        }
    }
}