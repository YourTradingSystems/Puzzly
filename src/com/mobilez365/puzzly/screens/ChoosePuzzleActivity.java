package com.mobilez365.puzzly.screens;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.util.ChooseGamePagerAdapter;

/**
 * Created by andrewtivodar on 28.05.2014.
 */
public class ChoosePuzzleActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener{

    private ViewPager levelsViewPager;
    private int mGameType;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    private ChooseGamePagerAdapter levelsAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_puzzle);

        levelsViewPager = (ViewPager) findViewById(R.id.vpMailACP);
        mGameType = getIntent().getIntExtra("type", 0);
        levelsViewPager.setOnPageChangeListener(this);

        btnPrevious = (ImageButton) findViewById(R.id.btnPreviousACP);
        btnNext = (ImageButton) findViewById(R.id.btnNextACP);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnNext.setVisibility(View.INVISIBLE);
        btnPrevious.setVisibility(View.INVISIBLE);
        levelsAdapter = new ChooseGamePagerAdapter(this, mGameType);
        levelsViewPager.setAdapter(levelsAdapter);
        if(AppHelper.getNextGame(this, mGameType) != -1)
            levelsViewPager.setCurrentItem(AppHelper.getCurrentGame(this, mGameType) / 4);
    }

    @Override
    public void onClick(View v) {
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
        if(v != 0) {
            btnNext.setVisibility(View.INVISIBLE);
            btnPrevious.setVisibility(View.INVISIBLE);
        }
        else {
            if(levelsViewPager.canScrollHorizontally(1))
                btnNext.setVisibility(View.VISIBLE);
            if(levelsViewPager.canScrollHorizontally(-1))
                btnPrevious.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}