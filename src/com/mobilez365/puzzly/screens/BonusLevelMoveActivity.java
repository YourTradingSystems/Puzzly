package com.mobilez365.puzzly.screens;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.mobilez365.puzzly.AnimationEndListener;
import com.mobilez365.puzzly.ParseSvgAsyncTask;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.customViews.AutoResizeImageView;
import com.mobilez365.puzzly.global.AccelerometerSensor;
import com.mobilez365.puzzly.global.AnalyticsGoogle;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.PuzzlesApplication;

import java.util.Random;

/**
 * Created by andrewtivodar on 14.07.2014.
 */
public class BonusLevelMoveActivity extends Activity {

    private int mGameType;
    private int mGameNumber;
    private boolean shakerEnabled = true;
    private AccelerometerSensor mShaker;
    private Vibrator mVibrator;
    private int mCandiesPassedCount = 0;
    private Point screenSize;
    private ImageView basket;
    private ImageView candy;
    private float previousBasketPosition;
    private int candySize;
    private Point basketSize;
    private ObjectAnimator fallAnimator;
    private ObjectAnimator missAnimator;
    private boolean candiesInPause = false;
    private long currentFallTime = 0;
    private long currentMissTime = 0;

    private final AccelerometerSensor.OnMoveListener mShakeListener = new AccelerometerSensor.OnMoveListener() {
        @Override
        public void onMove(float diff) {
            int step = screenSize.x / 150;
            float newPosition = previousBasketPosition + step * diff;

            if (newPosition < 0)
                newPosition = 0;
            else if (newPosition > screenSize.x - basketSize.x)
                newPosition = screenSize.x - basketSize.x;

            basket.setX(newPosition);
            previousBasketPosition = newPosition;
        }
    };

    private final AnimationEndListener.AnimEndListener mFallCandyAnimEndListener = new AnimationEndListener.AnimEndListener() {
        @Override
        public void OnAnimEnd(View v) {
            if (!candiesInPause)
                checkGathered();
        }
    };

    private final AnimationEndListener.AnimEndListener mGatherCandyAnimEndListener = new AnimationEndListener.AnimEndListener() {
        @Override
        public void OnAnimEnd(View v) {
            if (!candiesInPause) {
                candy.setVisibility(View.GONE);
                missAnimator = null;

                mCandiesPassedCount++;
                checkAllPicked();
            }
        }
    };

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnNextABM)
                nextGame();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_level_move);

        mShaker = new AccelerometerSensor();
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        mGameType = getIntent().getIntExtra("type", 0);
        mGameNumber = getIntent().getIntExtra("gameNumber", 0);

        screenSize = ((PuzzlesApplication) getApplication()).getScreenSize();
        previousBasketPosition = screenSize.x / 2;

        basket = (ImageView) findViewById(R.id.ivBasketABM);
        setBasketParams();

        dropCandy();

        ((PuzzlesApplication) getApplicationContext()).setNeedToShowAd(true);
    }

    private void setBasketParams() {
        basket.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int width = basket.getMeasuredWidth();
        int height = basket.getMeasuredHeight();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) basket.getLayoutParams();
        params.height = (int) (screenSize.y * 0.25f);
        params.width = (int) (params.height / (float) height * width);
        basket.setLayoutParams(params);

        basketSize = new Point( params.width, params.height);
    }

    private void dropCandy() {
        RelativeLayout candiesLayout = (RelativeLayout) findViewById(R.id.rlCandiesABM);

        Random r = new Random();
        candy = new ImageView(this);

        candySize = screenSize.y / 8;

        int candyNumber = r.nextInt(4) + 1;
        int candyXPos = r.nextInt(screenSize.x - candySize);

        candy.setImageResource(getResources().getIdentifier("img_candy" + candyNumber, "drawable", getPackageName()));
        candy.setLayoutParams(new RelativeLayout.LayoutParams(candySize, candySize));
        candy.setX(candyXPos);
        candy.setY(-candySize);
        candiesLayout.addView(candy);

        fallAnimator = ObjectAnimator.ofFloat(candy, "translationY", candy.getY(), screenSize.y - basketSize.y);
        fallAnimator.setDuration(3000);
        fallAnimator.setInterpolator(new LinearInterpolator());
        fallAnimator.addListener(new AnimationEndListener(candy, mFallCandyAnimEndListener));
        fallAnimator.start();
    }

    @Override
    public void onResume() {
        if (shakerEnabled)
            mShaker.resume(getApplicationContext(), null, mShakeListener);
        super.onResume();

        candiesInPause = false;

        if (fallAnimator != null) {
            fallAnimator.start();
            fallAnimator.setCurrentPlayTime(currentFallTime);
        }

        if (missAnimator != null) {
            missAnimator.start();
            missAnimator.setCurrentPlayTime(currentMissTime);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mShaker.pause();

        candiesInPause = true;

        if (fallAnimator != null) {
            currentFallTime = fallAnimator.getCurrentPlayTime();
            fallAnimator.end();
        }

        if (missAnimator != null) {
            currentMissTime = missAnimator.getCurrentPlayTime();
            missAnimator.end();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (fallAnimator != null)
            fallAnimator.cancel();

        if (missAnimator != null)
            missAnimator.cancel();
    }

    private void checkAllPicked() {
        int mCandiesCount = 20;
        if (mCandiesPassedCount == mCandiesCount) {
            ImageButton nextGame = (ImageButton) findViewById(R.id.btnNextABM);
            nextGame.setVisibility(View.VISIBLE);
            nextGame.setOnClickListener(mClickListener);

            mShaker.pause();
            shakerEnabled = false;
        } else
            dropCandy();
    }

    private void checkGathered() {
        int hedgehogPosX = (int) basket.getX();
        int candyPosX = (int) candy.getX();
        fallAnimator = null;
        if (candyPosX >= hedgehogPosX && candyPosX <= hedgehogPosX + basketSize.x - candySize) {
            AppHelper.setGameAchievement(getApplicationContext(), AppHelper.getGameAchievement(getApplicationContext()) + 1);

            if (AppHelper.getVibrate(getApplicationContext()))
                mVibrator.vibrate(100);

            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(candy, "scaleX", 1f, 1.3f);
            scaleXAnimator.setDuration(200);
            scaleXAnimator.start();

            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(candy, "scaleY", 1f, 1.3f);
            scaleYAnimator.setDuration(200);
            scaleYAnimator.addListener(new AnimationEndListener(candy, mGatherCandyAnimEndListener));
            scaleYAnimator.start();
        } else {
            missAnimator = ObjectAnimator.ofFloat(candy, "translationY", candy.getY(), screenSize.y);
            missAnimator.setDuration(750);
            missAnimator.addListener(new AnimationEndListener(candy, mGatherCandyAnimEndListener));
            missAnimator.setInterpolator(new LinearInterpolator());
            missAnimator.start();
        }
    }

    private void nextGame() {
        AnalyticsGoogle.fireBonusLevelEndEvent(this, getString(R.string.bonus_level_hedgehog));

        Intent gameIntent = new Intent(this, PuzzleGameActivity.class);
        gameIntent.putExtra("type", mGameType);
        gameIntent.putExtra("gameNumber", mGameNumber);
        startActivity(gameIntent);
        finish();
    }
}