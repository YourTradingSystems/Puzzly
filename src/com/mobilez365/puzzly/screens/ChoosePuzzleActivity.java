package com.mobilez365.puzzly.screens;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.util.BackgroundSound;
import com.mobilez365.puzzly.util.ChooseGamePagerAdapter;

/**
 * Created by andrewtivodar on 28.05.2014.
 */
public class ChoosePuzzleActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager levelsViewPager;
    private int maxLevelCount;
    private int currentLevel;
    private boolean arrowsShown;
    private int mGameType;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    private ChooseGamePagerAdapter levelsAdapter;
    private BackgroundSound mBackgroundSound;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_puzzle);

        mBackgroundSound = AppHelper.getBackgroundSound();

        levelsViewPager = (ViewPager) findViewById(R.id.vpMailACP);
        mGameType = getIntent().getIntExtra("type", 0);
        levelsViewPager.setOnPageChangeListener(this);

        maxLevelCount = AppHelper.getMaxGame(this, mGameType);
        currentLevel = AppHelper.getCurrentGame(this, mGameType);

        btnPrevious = (ImageButton) findViewById(R.id.btnPreviousACP);
        btnNext = (ImageButton) findViewById(R.id.btnNextACP);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);

        updateLevelsInfo();
    }


    private void updateLevelsInfo(){
        arrowsShown = false;
        btnNext.setVisibility(View.INVISIBLE);
        btnPrevious.setVisibility(View.INVISIBLE);
        levelsAdapter = new ChooseGamePagerAdapter(this, mGameType);
        levelsViewPager.setAdapter(levelsAdapter);
        levelsViewPager.setCurrentItem(AppHelper.getCurrentGame(this, mGameType) / 4);
    }
    @Override
    protected void onResume() {
        super.onResume();

        AppHelper.changeLanguage(this, AppHelper.getLocaleLanguage(this, Constans.GAME_LANGUAGE).name());

        //AppHelper.resetPassedGames();
        if(maxLevelCount != AppHelper.getMaxGame(this, mGameType) ||
                currentLevel != AppHelper.getCurrentGame(this, mGameType)) {
            updateLevelsInfo();
            maxLevelCount = AppHelper.getMaxGame(this, mGameType);
            currentLevel = AppHelper.getCurrentGame(this, mGameType);
        }
        levelsAdapter.clickEnable = true;

        if (!AppHelper.isAppInBackground(this)) {
            if (mBackgroundSound != null && !mBackgroundSound.isPlay()) {
                mBackgroundSound.pause(false);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (AppHelper.isAppInBackground(this) || AppHelper.isScreenOff(this)) {
            if (mBackgroundSound != null && mBackgroundSound.isPlay()) {
                mBackgroundSound.pause(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(levelsAdapter.clickEnable)
        switch (v.getId()) {
            case R.id.btnPreviousACP:
                levelsViewPager.setCurrentItem(levelsViewPager.getCurrentItem() - 1);
                break;
            case R.id.btnNextACP:
                levelsViewPager.setCurrentItem(levelsViewPager.getCurrentItem() + 1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {
        if (v != 0) {
            btnNext.setVisibility(View.INVISIBLE);
            btnPrevious.setVisibility(View.INVISIBLE);
            levelsAdapter.clickEnable = false;
        }
        if(!arrowsShown) {
            if (levelsViewPager.canScrollHorizontally(1))
                btnNext.setVisibility(View.VISIBLE);
            if (levelsViewPager.canScrollHorizontally(-1))
                btnPrevious.setVisibility(View.VISIBLE);
            arrowsShown = true;
        }
    }

    @Override
    public void onPageSelected(int i) {
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        if (i == 0) {
            if (levelsViewPager.canScrollHorizontally(1))
                btnNext.setVisibility(View.VISIBLE);
            if (levelsViewPager.canScrollHorizontally(-1))
                btnPrevious.setVisibility(View.VISIBLE);
            levelsAdapter.clickEnable = true;
        }
    }
}