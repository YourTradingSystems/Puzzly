package com.mobilez365.puzzly.screens;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;
import com.mobilez365.puzzly.util.ChooseGamePagerAdapter;

/**
 * Created by andrewtivodar on 28.05.2014.
 */
public class ChoosePuzzleActivity extends RestartActivty {

    private ViewPager levelsViewPager;
    private int maxLevelCount;
    private int currentLevel;
    private boolean arrowsShown;
    private int mGameType;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    private ChooseGamePagerAdapter levelsAdapter;

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
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
    };

    private final ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
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
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_puzzle);
        if (AppHelper.getPlayBackgroundMusic(getApplicationContext()) && !AppHelper.getBackgroundSound().getName().equals(Constans.MENU_BACKGROUND_MUSIC))
            AppHelper.startBackgroundSound(getApplicationContext(), Constans.MENU_BACKGROUND_MUSIC);

        levelsViewPager = (ViewPager) findViewById(R.id.vpMailACP);
        mGameType = getIntent().getIntExtra("type", 0);
        levelsViewPager.setOnPageChangeListener(mPageChangeListener);

        maxLevelCount = AppHelper.getMaxGame(getApplicationContext(), mGameType);
        currentLevel = AppHelper.getCurrentGame(getApplicationContext(), mGameType);

        btnPrevious = (ImageButton) findViewById(R.id.btnPreviousACP);
        btnNext = (ImageButton) findViewById(R.id.btnNextACP);
        btnNext.setOnClickListener(mClickListener);
        btnPrevious.setOnClickListener(mClickListener);

        updateLevelsInfo();
    }

    private void updateLevelsInfo(){
        arrowsShown = false;
        btnNext.setVisibility(View.INVISIBLE);
        btnPrevious.setVisibility(View.INVISIBLE);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        levelsAdapter = new ChooseGamePagerAdapter(getApplicationContext(), mGameType, size);
        levelsViewPager.setAdapter(levelsAdapter);
        //Reverse for arabic
        int currentGame =  AppHelper.getLocalizeStudyLanguage(getApplicationContext())== 8 ? PuzzlesDB.getPuzzleGameCount(getApplicationContext(), mGameType) - AppHelper.getCurrentGame(getApplicationContext(), mGameType): AppHelper.getCurrentGame(getApplicationContext(), mGameType);
        levelsViewPager.setCurrentItem( currentGame / 4 );
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppHelper.changeLanguage(getApplicationContext(), AppHelper.getLocaleLanguage(getApplicationContext(), Constans.GAME_LANGUAGE).name());

        if(maxLevelCount != AppHelper.getMaxGame(getApplicationContext(), mGameType) ||
                currentLevel != AppHelper.getCurrentGame(getApplicationContext(), mGameType)) {
            updateLevelsInfo();
            maxLevelCount = AppHelper.getMaxGame(getApplicationContext(), mGameType);
            currentLevel = AppHelper.getCurrentGame(getApplicationContext(), mGameType);
        }
        levelsAdapter.clickEnable = true;

        if (AppHelper.getPlayBackgroundMusic(getApplicationContext()) && !AppHelper.getBackgroundSound().getName().equals(Constans.MENU_BACKGROUND_MUSIC))
            AppHelper.startBackgroundSound(getApplicationContext(), Constans.MENU_BACKGROUND_MUSIC);

        if (!AppHelper.isAppInBackground(getApplicationContext()))
            AppHelper.getBackgroundSound().pause(false);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (AppHelper.isAppInBackground(getApplicationContext()) || AppHelper.isScreenOff(getApplicationContext()))
            AppHelper.getBackgroundSound().pause(true);
    }
}