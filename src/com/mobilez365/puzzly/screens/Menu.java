package com.mobilez365.puzzly.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;

public class Menu extends Activity implements View.OnClickListener {

    private ImageView ivGameSimpleFill_MS;
    private ImageView ivGameSimpleReveal_MS;
    private ImageButton btnGameSettings_MS;
    private TextView tvGameAchievement_MS;
    private ImageView ivMenuLogo_MS;
    private LinearLayout llSubMenu_MS;
    private LinearLayout llMainMenu_MS;
    private RelativeLayout rlLeftBalloon_MS;
    private RelativeLayout rlRightBalloon_MS;

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        AppHelper.changeLanguage(this, AppHelper.getLocaleLanguage(this).name());

        setContentView(R.layout.menu_screen);
        findViews();
        setListeners();
        startAnimation();
        setGameAchievement(100);
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {

            case R.id.ivGameSimpleFill_MS:
                gameSimpleFill();
                break;

            case R.id.ivGameSimpleReveal_MS:
                gameSimpleReveal();
                break;

            case R.id.btnGameSettings_MS:
                settings();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private final void findViews() {
        ivGameSimpleFill_MS = (ImageView)findViewById(R.id.ivGameSimpleFill_MS);
        ivGameSimpleReveal_MS = (ImageView)findViewById(R.id.ivGameSimpleReveal_MS);
        btnGameSettings_MS = (ImageButton)findViewById(R.id.btnGameSettings_MS);
        tvGameAchievement_MS = (TextView)findViewById(R.id.tvGameAchievement_MS);
        ivMenuLogo_MS = (ImageView)findViewById(R.id.ivMenuLogo_MS);
        llSubMenu_MS = (LinearLayout)findViewById(R.id.llSubMenu_MS);
        llMainMenu_MS = (LinearLayout)findViewById(R.id.llMainMenu_MS);
        rlLeftBalloon_MS = (RelativeLayout)findViewById(R.id.rlLeftBalloon_MS);
        rlRightBalloon_MS = (RelativeLayout)findViewById(R.id.rlRightBalloon_MS);
    }

    private final void setListeners() {
        ivGameSimpleFill_MS.setOnClickListener(this);
        ivGameSimpleReveal_MS.setOnClickListener(this);
        btnGameSettings_MS.setOnClickListener(this);
    }

    private final void startAnimation() {
        Animation logoScaleAnimation = AnimationUtils.loadAnimation(this, R.anim.menu_logo);
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

    private final void setGameAchievement(int _count) {
        tvGameAchievement_MS.setText(""+_count);
    }

    private final void gameSimpleFill() {

    }

    private final void gameSimpleReveal() {
        startActivity(new Intent(this, BonusLevelTreeActivity.class));
    }

    private final void settings() {
        startActivity(new Intent(this, Settings.class));
    }

}
