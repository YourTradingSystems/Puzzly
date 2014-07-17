package com.mobilez365.puzzly.screens;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.mobilez365.puzzly.global.AccelerometerSensor;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.global.AnalyticsGoogle;
import com.mobilez365.puzzly.AnimationEndListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by andrewtivodar on 15.05.2014.
 */
public class BonusLevelFlowerActivity extends InterstitialActivity{

    private int gameType;
    private final int mCandiesCount = 5;
    private int mFlowersShownCount = 0;
    private int mCandiesPickedCount = 0;
    private int mGameNumber;
    private AccelerometerSensor mShaker;
    private Vibrator mVibrator;
    private List<ImageView> flowersList;
    private ImageView[] candiesList;
    private List<ObjectAnimator> candiesRotateAnimators;
    private int[] flowersStatus;
    private int[] candiesStatus;
    private int mScreenHeight;
    private int mScreenWidth;
    private ObjectAnimator sunRotateAnimator;

    private RelativeLayout rlContainer_ABLF;
    private RelativeLayout candiesLayout;
    private ImageButton nextGame;

    private VideoView mTutorial;

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setClickable(false);
            if (v.getId() == R.id.btnNextABF){
                v.setClickable(false);
                nextGame();
            }
            else
                pickCandy(v);
        }
    };

    private final AccelerometerSensor.OnShakeListener mShakeListener = new AccelerometerSensor.OnShakeListener() {
        @Override
        public void onShake() {
            if (mTutorial != null) {
                mTutorial.stopPlayback();
                rlContainer_ABLF.removeView(mTutorial);
                AppHelper.setBonusFlower(getApplicationContext(), true);
                mTutorial = null;
            }

            if (mFlowersShownCount != mCandiesCount) {
                if (AppHelper.getVibrate(getApplicationContext()))
                    mVibrator.vibrate(100);
                showFlower();
            }
        }
    };

    private final AnimationEndListener.AnimEndListener mAnimEndListener = new AnimationEndListener.AnimEndListener() {
        @Override
        public void OnAnimEnd(View v) {
            v.setVisibility(View.GONE);
            mCandiesPickedCount++;
            checkAllPicked();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_level_flower);

        gameType = getIntent().getIntExtra("type", 0);
        mGameNumber = getIntent().getIntExtra("gameNumber", 0);

        mShaker = new AccelerometerSensor();

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        candiesLayout = (RelativeLayout) findViewById(R.id.rlCandiesABF);
        rlContainer_ABLF = (RelativeLayout) findViewById(R.id.rlContainer_ABLF);

        candiesList =  new ImageView[mCandiesCount];
        candiesRotateAnimators = new ArrayList<ObjectAnimator>();
        candiesStatus = new int[mCandiesCount];

        AnalyticsGoogle.fireScreenEvent(this, getString(R.string.bonus_level_flower));

        initFlowers();
        initSun();

        if (!AppHelper.getBonusFlower(getApplicationContext()))
            mTutorial = AppHelper.showVideoTutorial(this, rlContainer_ABLF);
    }

    private void initSun(){
        ImageView sun = (ImageView) findViewById(R.id.ivSunABF);
        sun.setY(mScreenHeight / 10);

        sunRotateAnimator = ObjectAnimator.ofFloat(sun, "rotation", 0, 360f);
        sunRotateAnimator.setDuration(5000);
        sunRotateAnimator.setInterpolator(new LinearInterpolator());
        sunRotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        sunRotateAnimator.start();
    }

    private void initFlowers() {
        RelativeLayout candiesLayout = (RelativeLayout) findViewById(R.id.rlCandiesABF);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mScreenWidth = size.x;
        mScreenHeight = size.y;
        int step = mScreenWidth / mCandiesCount;

        flowersList = new ArrayList<ImageView>();
        flowersStatus = new int[mCandiesCount];
        for (int i = 0; i < mCandiesCount; i++) {
            ImageView flower = new ImageView(this);

            flower.setImageResource(R.drawable.img_flower);
            flower.setLayoutParams(new RelativeLayout.LayoutParams((int)(mScreenHeight / 4.2f), mScreenHeight / 3));
            flower.setX(i * step);
            flower.setY(mScreenHeight);
            flower.setId(i);
            flowersList.add(flower);
            candiesLayout.addView(flower);
            flowersStatus[i] = Constans.CANDY_ON_TOP;
        }
    }

    private void initCandy(int candyNum, View flower) {
        Random r = new Random();
        ImageView candy = new ImageView(this);

        int candyNumber = r.nextInt(4) + 1;
        candy.setImageResource(getResources().getIdentifier("img_candy" + candyNumber, "drawable", getPackageName()));
        candy.setLayoutParams(new RelativeLayout.LayoutParams(flower.getWidth() / 2, flower.getWidth() / 2));
        candy.setX(flower.getX() + (int) (flower.getWidth() / 3.5f));
        candy.setY(flower.getY() + (int) (flower.getHeight() / 6f));
        candy.setTag(candyNum);
        candy.setOnClickListener(mClickListener);
        candiesList[candyNum] = candy;
        candiesLayout.addView(candy);
        candiesStatus[candyNum] = Constans.CANDY_FALLEN;

        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(candy, "rotation", -10f, 0f, 10f, 0f, -10f);
        rotateAnimator.setDuration(800 + candyNum * 20);
        rotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimator.start();
        candiesRotateAnimators.add(rotateAnimator);
    }

    private void showFlower() {
        Random r = new Random();
        int candyNumber = r.nextInt(mCandiesCount);

        ImageView flower = flowersList.get(candyNumber);
        if (flowersStatus[candyNumber] == Constans.CANDY_ON_TOP) {
            flowersStatus[candyNumber] = Constans.CANDY_FALLEN;

            ObjectAnimator showAnimator = ObjectAnimator.ofFloat(flower, "translationY", mScreenHeight, mScreenHeight - (flower.getHeight() * 1.3f));
            showAnimator.setDuration(300);
            showAnimator.addListener(new AnimationEndListener(flower, new AnimationEndListener.AnimEndListener() {
                @Override
                public void OnAnimEnd(View v) {
                    initCandy(v.getId(), v);
                }
            }));
            showAnimator.start();

            mFlowersShownCount++;
        } else showFlower();
    }

    private void pickCandy(View v) {
        if (candiesStatus[(Integer) v.getTag()] == Constans.CANDY_FALLEN) {
            candiesStatus[(Integer) v.getTag()] = Constans.CANDY_PICKED;
            AppHelper.setGameAchievement(getApplicationContext(), AppHelper.getGameAchievement(getApplicationContext()) + 1);
            v.bringToFront();

            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(v, "scaleX", 1f, 1.5f);
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(v, "scaleY", 1f, 1.5f);

            AnimatorSet set = new AnimatorSet();
            set.play(scaleXAnimator).with(scaleYAnimator);
            set.setDuration(300);

            set.addListener(new AnimationEndListener(v, mAnimEndListener));

            set.start();

        }
    }

    private void checkAllPicked() {
        if (mCandiesPickedCount == mCandiesCount) {
             nextGame = (ImageButton) findViewById(R.id.btnNextABF);
            nextGame.setVisibility(View.VISIBLE);
            nextGame.setOnClickListener(mClickListener);
        }
    }

    private void nextGame() {
        AnalyticsGoogle.fireBonusLevelEndEvent(this, getString(R.string.bonus_level_flower));

        Intent gameIntent = new Intent(this, PuzzleGameActivity.class);
        gameIntent.putExtra("type", gameType);
        gameIntent.putExtra("gameNumber", mGameNumber);
        startActivity(gameIntent);
        finish();
    }

    @Override
    public void onResume() {
        mShaker.resume(getApplicationContext(), mShakeListener, null);
        super.onResume();

        if (mTutorial != null)
            mTutorial.start();

        if(nextGame != null) nextGame.setClickable(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mShaker.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        sunRotateAnimator.cancel();
        for (ObjectAnimator candiesRotateAnimator : candiesRotateAnimators) {
            candiesRotateAnimator.cancel();
        }

    }
}