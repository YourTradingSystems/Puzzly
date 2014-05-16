package com.mobilez365.puzzly.screens;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.View;
import android.widget.*;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.util.AnimationEndListener;
import com.mobilez365.puzzly.util.ShakeSensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class BonusLevelShakeActivity extends Activity implements ShakeSensor.OnShakeListener, View.OnClickListener, AnimationEndListener.AnimEndListener {

    private final int mCandiesCount = 9;
    private int mCandiesDroppedCount = 0;
    private int mCandiesPickedCount = 0;
    private ShakeSensor mShaker;
    private Vibrator mVibrator;
    private List<ImageView> candiesList;
    private List<ObjectAnimator> candiesRotateAnimators;
    private int[] candiesStatus;
    private int mScreenHeight;
    private int mScreenWidth;

    private TextView tvCandiesCount;
    private TextView tvAllCandiesPicked;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_level_shake);

        mShaker = new ShakeSensor(this);
        mShaker.setOnShakeListener(this);

        tvCandiesCount = (TextView) findViewById(R.id.tvCandiesCountABS);
        tvAllCandiesPicked = (TextView) findViewById(R.id.tvAllPickedABS);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        initCandies();
    }

    private void initCandies() {
        RelativeLayout candiesLayout = (RelativeLayout) findViewById(R.id.rlCandiesABS);
        tvCandiesCount.setText("" + mCandiesPickedCount);

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
            candy.setOnClickListener(this);
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

            AppHelper.setGameAchievement(this, AppHelper.getGameAchievement(this) + 1);
            v.bringToFront();

            int finishCenterX = mScreenWidth / 2 - (v.getWidth() / 2);
            int finishCenterY = mScreenHeight / 2 - (v.getHeight() / 2);

            ObjectAnimator moveXAnimator = ObjectAnimator.ofFloat(v, "translationX", v.getX(), finishCenterX);
            ObjectAnimator moveYAnimator = ObjectAnimator.ofFloat(v, "translationY", mScreenHeight - v.getHeight(), finishCenterY);

            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(v, "scaleX", 1f, 1.2f);
            scaleXAnimator.setDuration(100);
            scaleXAnimator.start();

            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(v, "scaleY", 1f, 1.2f);
            scaleYAnimator.setDuration(100);
            scaleYAnimator.start();

            AnimatorSet set = new AnimatorSet();
            set.play(moveXAnimator).with(moveYAnimator);
            set.setDuration(300);

            set.addListener(new AnimationEndListener(v, this));

            set.start();

        }
    }

    private void checkAllPicked(){
        if(mCandiesPickedCount == mCandiesCount) {
            tvAllCandiesPicked.setVisibility(View.VISIBLE);
            ImageButton nextGame = (ImageButton) findViewById(R.id.btnNextABS);
            nextGame.setVisibility(View.VISIBLE);
            nextGame.setOnClickListener(this);
        }
    }

    private void nextGame() {
        startActivity(new Intent(this, GameFillActivity.class));
        finish();
    }

    @Override
    public void onResume() {
        mShaker.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mShaker.pause();
        super.onPause();
    }

    @Override
    public void onShake() {
        if (mCandiesDroppedCount != mCandiesCount) {
            mVibrator.vibrate(100);
            dropCandy();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnNextABS)
            nextGame();
        else
            pickCandy(v);
    }

    @Override
    public void OnAnimEnd(View v) {
        v.setVisibility(View.GONE);
        mCandiesPickedCount ++;
        tvCandiesCount.setText("" + mCandiesPickedCount);
        checkAllPicked();
    }
}