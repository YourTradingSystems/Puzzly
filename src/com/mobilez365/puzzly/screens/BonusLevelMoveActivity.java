package com.mobilez365.puzzly.screens;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.util.AnalyticsGoogle;
import com.mobilez365.puzzly.util.AnimationEndListener;
import com.mobilez365.puzzly.util.ShakeSensor;

import java.util.Random;

/**
 * Created by andrewtivodar on 14.07.2014.
 */
public class BonusLevelMoveActivity extends InterstitialActivity {

    private int mGameType;
    private int mGameNumber;
    private boolean shakerEnabled = true;
    private ShakeSensor mShaker;
    private Vibrator mVibrator;
    private ImageButton nextGame;
    private final int mCandiesCount = 20;
    private int mCandiesPickedCount = 0;
    private int mScreenWidth;
    private int mScreenHeight;
    private ImageView hedgehog;
    private ImageView candy;
    private float previousHedgehogPosition = 400;
    private int candySize;
    private int hedgehogWidth;
    private int hedgehogHeight;
    private ObjectAnimator fallAnimator;
    private ObjectAnimator missAnimator;
    private boolean candiesInPause = false;
    private long currentFallTime = 0;
    private long currentMissTime = 0;

    private final ShakeSensor.OnMoveListener mShakeListener = new ShakeSensor.OnMoveListener() {
        @Override
        public void onMove(float diff) {
            int step = mScreenWidth / 150;
            float newPosition = previousHedgehogPosition + step * diff;

            if (newPosition < 0)
                newPosition = 0;
            else if (newPosition > mScreenWidth - hedgehogWidth)
                newPosition = mScreenWidth - hedgehogWidth;

            hedgehog.setX(newPosition);
            previousHedgehogPosition = newPosition;
        }
    };

    private final AnimationEndListener.AnimEndListener mFallCandyAnimEndListener = new AnimationEndListener.AnimEndListener() {
        @Override
        public void OnAnimEnd(View v) {
            if(!candiesInPause)
                checkGathered();
        }
    };

    private final AnimationEndListener.AnimEndListener mGatherCandyAnimEndListener = new AnimationEndListener.AnimEndListener() {
        @Override
        public void OnAnimEnd(View v) {
            if(!candiesInPause) {
                candy.setVisibility(View.GONE);
                missAnimator = null;

                mCandiesPickedCount++;
                checkAllPicked();
            }
        }
    };

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setClickable(false);
            if (v.getId() == R.id.btnNextABM) {
                v.setClickable(false);
                nextGame();
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_level_move);

        mShaker = new ShakeSensor();
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        hedgehog = (ImageView) findViewById(R.id.ivHedgehogABM);

        mGameType = getIntent().getIntExtra("type", 0);
        mGameNumber = getIntent().getIntExtra("gameNumber", 0);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mScreenWidth = size.x;
        mScreenHeight = size.y;
        setHedgehogParams(mScreenHeight / 3);

        dropCandy();
    }

    private void setHedgehogParams(int height) {
        hedgehogWidth = (int) (height * 0.728);
        hedgehogHeight = height;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) hedgehog.getLayoutParams();
        params.width = hedgehogWidth;
        params.height = hedgehogHeight;
        hedgehog.setLayoutParams(params);
    }

    private void dropCandy() {
        RelativeLayout candiesLayout = (RelativeLayout) findViewById(R.id.rlCandiesABM);

        Random r = new Random();
        candy = new ImageView(this);

        candySize = mScreenHeight / 8;

        int candyNumber = r.nextInt(4) + 1;
        int candyXPos = r.nextInt(mScreenWidth - candySize);

        candy.setImageResource(getResources().getIdentifier("img_candy" + candyNumber, "drawable", getPackageName()));
        candy.setLayoutParams(new RelativeLayout.LayoutParams(candySize, candySize));
        candy.setX(candyXPos);
        candy.setY(-candySize);
        candiesLayout.addView(candy);

        fallAnimator = ObjectAnimator.ofFloat(candy, "translationY", candy.getY(), mScreenHeight - hedgehogHeight);
        fallAnimator.setDuration(3000);
        fallAnimator.setInterpolator(new LinearInterpolator());
        fallAnimator.addListener(new AnimationEndListener(candy, mFallCandyAnimEndListener));
        fallAnimator.start();
    }

    @Override
    public void onResume() {
        if(shakerEnabled)
            mShaker.resume(getApplicationContext(), null, mShakeListener);
        super.onResume();

        if (!AppHelper.isAppInBackground(getApplicationContext()))
            AppHelper.getBackgroundSound().pause(false);

        candiesInPause = false;

        if(fallAnimator != null) {
            fallAnimator.start();
            fallAnimator.setCurrentPlayTime(currentFallTime);
        }

        if(missAnimator != null) {
            missAnimator.start();
            missAnimator.setCurrentPlayTime(currentMissTime);
        }

        if (nextGame != null) nextGame.setClickable(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mShaker.pause();
        if (AppHelper.isAppInBackground(getApplicationContext()) || AppHelper.isScreenOff(getApplicationContext())) {
            AppHelper.getBackgroundSound().pause(true);
        }

        candiesInPause = true;

        if(fallAnimator != null) {
            currentFallTime = fallAnimator.getCurrentPlayTime();
            fallAnimator.end();
        }

        if(missAnimator != null) {
            currentMissTime = missAnimator.getCurrentPlayTime();
            missAnimator.end();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(fallAnimator != null)
            fallAnimator.cancel();

        if(missAnimator != null)
            missAnimator.cancel();
    }

    private void checkAllPicked() {
        if (mCandiesPickedCount == mCandiesCount) {
            nextGame = (ImageButton) findViewById(R.id.btnNextABM);
            nextGame.setVisibility(View.VISIBLE);
            nextGame.setOnClickListener(mClickListener);

            mShaker.pause();
            shakerEnabled = false;
        }
        else
            dropCandy();
    }

    private void checkGathered() {
        int hedgehogPosX = (int) hedgehog.getX();
        int candyPosX = (int) candy.getX();
        fallAnimator = null;
        if (candyPosX >= hedgehogPosX && candyPosX <= hedgehogPosX + hedgehogWidth - candySize) {
            AppHelper.setGameAchievement(getApplicationContext(), AppHelper.getGameAchievement(getApplicationContext()) + 1);

            if (AppHelper.getVibrate(getApplicationContext()))
                mVibrator.vibrate(100);

            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(candy, "scaleX", 1f, 1.3f);
            scaleXAnimator.setDuration(100);
            scaleXAnimator.start();

            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(candy, "scaleY", 1f, 1.3f);
            scaleYAnimator.setDuration(100);
            scaleYAnimator.addListener(new AnimationEndListener(candy, mGatherCandyAnimEndListener));
            scaleYAnimator.start();
        }
        else {
            missAnimator = ObjectAnimator.ofFloat(candy, "translationY", candy.getY(), mScreenHeight);
            missAnimator.setDuration(1250);
            missAnimator.addListener(new AnimationEndListener(candy, mGatherCandyAnimEndListener));
            missAnimator.setInterpolator(new LinearInterpolator());
            missAnimator.start();
        }
    }

    private void nextGame() {
        AnalyticsGoogle.fireBonusLevelEndEvent(this, getString(R.string.bonus_level_hedgehog));

        Intent gameIntent = new Intent(this, GameFillActivity.class);
        gameIntent.putExtra("type", mGameType);
        gameIntent.putExtra("gameNumber", mGameNumber);
        startActivity(gameIntent);
        finish();
    }
}