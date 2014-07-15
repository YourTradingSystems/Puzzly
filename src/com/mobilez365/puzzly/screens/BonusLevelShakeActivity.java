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
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.util.AnalyticsGoogle;
import com.mobilez365.puzzly.util.AnimationEndListener;
import com.mobilez365.puzzly.util.BackgroundSound;
import com.mobilez365.puzzly.util.ShakeSensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class BonusLevelShakeActivity extends InterstitialActivity {

    private int gameType;
    private final int mCandiesCount = 9;
    private int mCandiesDroppedCount = 0;
    private int mCandiesPickedCount = 0;
    private int mGameNumber;
    private ShakeSensor mShaker;
    private Vibrator mVibrator;
    private List<ImageView> candiesList;
    private List<ObjectAnimator> candiesRotateAnimators;
    private int[] candiesStatus;
    private int mScreenHeight;
    private int mScreenWidth;

    private RelativeLayout rlContainer_ABLS;
    private ImageButton nextGame;

    private VideoView mTutorial;

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnNextABS){
                v.setClickable(false);
                nextGame();
            }
            else
                pickCandy(v);
        }
    };

    private final ShakeSensor.OnShakeListener mShakeListener = new ShakeSensor.OnShakeListener() {
        @Override
        public void onShake() {
            if (mTutorial != null) {
                mTutorial.stopPlayback();
                rlContainer_ABLS.removeView(mTutorial);
                AppHelper.setBonusShake(getApplicationContext(), true);
                mTutorial = null;
            }

            if (mCandiesDroppedCount != mCandiesCount) {
                if (AppHelper.getVibrate(getApplicationContext()))
                    mVibrator.vibrate(100);
                dropCandy();
            }
        }
    };

    private final AnimationEndListener.AnimEndListener mAnimEndListener = new AnimationEndListener.AnimEndListener() {
        @Override
        public void OnAnimEnd(View v) {
            v.setVisibility(View.GONE);
            mCandiesPickedCount ++;
            checkAllPicked();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_level_shake);

        gameType = getIntent().getIntExtra("type", 0);
        mGameNumber = getIntent().getIntExtra("gameNumber", 0);

        mShaker = new ShakeSensor();

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        rlContainer_ABLS = (RelativeLayout) findViewById(R.id.rlContainer_ABLS);

        AnalyticsGoogle.fireScreenEvent(this, getString(R.string.bonus_level_shake));
        initCandies();

        if (!AppHelper.getBonusShake(getApplicationContext()))
            mTutorial = AppHelper.showVideoTutorial(this, rlContainer_ABLS);
    }

    private void initCandies() {
        RelativeLayout candiesLayout = (RelativeLayout) findViewById(R.id.rlCandiesABS);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        mScreenHeight = size.y;
        mScreenWidth = width;
        int step = width / mCandiesCount;

        candiesList = new ArrayList<ImageView>();
        candiesRotateAnimators = new ArrayList<ObjectAnimator>();
        candiesStatus = new int[mCandiesCount];
        Random r = new Random();
        for (int i = 0; i < mCandiesCount; i++) {
            ImageView candy = new ImageView(this);

            int candyNumber = r.nextInt(4) + 1;
            candy.setImageResource(getResources().getIdentifier("img_candy" + candyNumber, "drawable", getPackageName()));
            candy.setX(i * step);
            candy.setTag(i);
            candy.setOnClickListener(mClickListener);
            candiesList.add(candy);
            candiesLayout.addView(candy);
            candiesStatus[i] = Constans.CANDY_ON_TOP;

            ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(candy, "rotation", -10f, 0f, 10f, 0f, -10f);
            rotateAnimator.setDuration(800 + i * 20);
            rotateAnimator.setRepeatMode(ValueAnimator.RESTART);
            rotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
            rotateAnimator.start();
            candiesRotateAnimators.add(rotateAnimator);
        }
    }

    private void dropCandy() {
        Random r = new Random();
        int candyNumber = r.nextInt(mCandiesCount);

        ImageView candy = candiesList.get(candyNumber);
        if (candiesStatus[candyNumber] == Constans.CANDY_ON_TOP) {
            candiesStatus[candyNumber] = Constans.CANDY_FALLEN;

            candiesRotateAnimators.get(candyNumber).cancel();

            ObjectAnimator fallAnimator = ObjectAnimator.ofFloat(candy, "translationY", 0f, mScreenHeight - candy.getHeight());
            fallAnimator.setDuration(300);
            fallAnimator.start();

            mCandiesDroppedCount++;
        } else dropCandy();
    }

    private void pickCandy(View v) {
        if (candiesStatus[(Integer) v.getTag()] == Constans.CANDY_FALLEN) {
            candiesStatus[(Integer) v.getTag()] = Constans.CANDY_PICKED;

            AppHelper.setGameAchievement(getApplicationContext(), AppHelper.getGameAchievement(getApplicationContext()) + 1);
            v.bringToFront();

            int finishCenterX = mScreenWidth / 2 - (v.getWidth() / 2);
            int finishCenterY = mScreenHeight / 2 - (v.getHeight() / 2);

            ObjectAnimator moveXAnimator = ObjectAnimator.ofFloat(v, "translationX", v.getX(), finishCenterX);
            ObjectAnimator moveYAnimator = ObjectAnimator.ofFloat(v, "translationY", v.getY(), finishCenterY);

            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(v, "scaleX", 1f, 1.2f);
            scaleXAnimator.setDuration(100);
            scaleXAnimator.start();

            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(v, "scaleY", 1f, 1.2f);
            scaleYAnimator.setDuration(100);
            scaleYAnimator.start();

            AnimatorSet set = new AnimatorSet();
            set.play(moveXAnimator).with(moveYAnimator);
            set.setDuration(300);

            set.addListener(new AnimationEndListener(v, mAnimEndListener));

            set.start();

        }
    }

    private void checkAllPicked(){
        if(mCandiesPickedCount == mCandiesCount) {
            nextGame = (ImageButton) findViewById(R.id.btnNextABS);
            nextGame.setVisibility(View.VISIBLE);
            nextGame.setOnClickListener(mClickListener);
        }
    }

    private void nextGame() {
        AnalyticsGoogle.fireBonusLevelEndEvent(this, getString(R.string.bonus_level_shake));

        Intent gameIntent = new Intent(this, GameFillActivity.class);
        gameIntent.putExtra("type", gameType);
        gameIntent.putExtra("gameNumber", mGameNumber);
        startActivity(gameIntent);
        candiesList.clear();
        finish();
    }

    @Override
    public void onResume() {
        mShaker.resume(getApplicationContext(), mShakeListener, null);
        super.onResume();

        if (!AppHelper.isAppInBackground(getApplicationContext()))
            AppHelper.getBackgroundSound().pause(false);

        if (mTutorial != null)
            mTutorial.start();

        if(nextGame != null) nextGame.setClickable(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mShaker.pause();
        if (AppHelper.isAppInBackground(getApplicationContext()) || AppHelper.isScreenOff(getApplicationContext())) {
            AppHelper.getBackgroundSound().pause(true);

            if (mTutorial != null)
                mTutorial.stopPlayback();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        for (ObjectAnimator candiesRotateAnimator : candiesRotateAnimators) {
            candiesRotateAnimator.cancel();
        }

    }


}