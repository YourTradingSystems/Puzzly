package com.mobilez365.puzzly.screens;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.mobilez365.puzzly.util.AnimationEndListener;
import com.mobilez365.puzzly.util.BackgroundSound;
import com.mobilez365.puzzly.util.ShakeSensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by andrewtivodar on 14.05.2014.
 */
public class BonusLevelTreeActivity extends InterstitialActivity{

    private int gameType;
    private int screenHeight;
    private int previousFallenCandyPosY;
    private int fallenCandyStep;
    private int mCandiesCount = 7;
    private int mCandiesDroppedCount = 0;
    private int mCandiesPickedCount = 0;
    private int mGameNumber;
    private ShakeSensor mShaker;
    private Vibrator mVibrator;
    private List<ImageView> candiesList;
    private List<ObjectAnimator> candiesRotateAnimators;
    private int[] candiesStatus;
    private RelativeLayout candiesLayout;
    private ImageButton nextGame;

    private VideoView mTutorial;

    private RelativeLayout rlContainer_ABLT;

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnNextABL) {
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
                rlContainer_ABLT.removeView(mTutorial);
                AppHelper.setBonusTree(getApplicationContext(), true);
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
        setContentView(R.layout.activity_bonus_level_tree);

        gameType = getIntent().getIntExtra("type", 0);
        mGameNumber = getIntent().getIntExtra("gameNumber", 0);

        mShaker = new ShakeSensor();
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        rlContainer_ABLT = (RelativeLayout) findViewById(R.id.rlContainer_ABLT);

        initData();

        if (!AppHelper.getBonusTree(getApplicationContext()))
            mTutorial = AppHelper.showVideoTutorial(this, rlContainer_ABLT);
    }

    private void initData() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenHeight = size.y;
        fallenCandyStep = size.x / mCandiesCount;
        previousFallenCandyPosY = fallenCandyStep / 4;

        int startCandiesHeight = screenHeight / 11;
        int startCandiesWidth =  size.x / 3;

        candiesLayout = (RelativeLayout) findViewById(R.id.rlTreeABL);

        candiesList = new ArrayList<ImageView>();
        candiesStatus = new int[mCandiesCount];

        int candyPosStep = screenHeight / 60;

        candiesRotateAnimators = new ArrayList<ObjectAnimator>();
        createCandy(startCandiesWidth + candyPosStep * 2, startCandiesHeight + candyPosStep * 12, 0);
        createCandy(startCandiesWidth + candyPosStep * 7, startCandiesHeight + candyPosStep * 4, 1);
        createCandy(startCandiesWidth + candyPosStep * 10, startCandiesHeight + candyPosStep * 20, 2);
        createCandy(startCandiesWidth + candyPosStep * 16, startCandiesHeight + candyPosStep * 10, 3);
        createCandy(startCandiesWidth + candyPosStep * 19, startCandiesHeight + candyPosStep, 4);
        createCandy(startCandiesWidth + candyPosStep * 24, startCandiesHeight + candyPosStep * 12, 5);
        createCandy(startCandiesWidth + candyPosStep * 20, startCandiesHeight + candyPosStep * 20, 6);
    }

    private void createCandy(int x, int y, int num) {
        ImageView candy = new ImageView(this);

        Random r = new Random();
        int candyNumber = r.nextInt(4) + 1;
        candy.setImageResource(getResources().getIdentifier("img_candy" + candyNumber, "drawable", getPackageName()));
        candy.setX(x);
        candy.setY(y);
        candy.setTag(num);
        candy.setOnClickListener(mClickListener);
        candiesList.add(candy);
        candiesLayout.addView(candy);
        candiesStatus[num] = Constans.CANDY_ON_TOP;

        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(candy, "rotation", -10f, 0f, 10f, 0f, -10f);
        rotateAnimator.setDuration(800 + num * 20);
        rotateAnimator.setRepeatMode(ValueAnimator.RESTART);
        rotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimator.start();
        candiesRotateAnimators.add(rotateAnimator);

        candy.getLayoutParams().height = screenHeight / 8;
        candy.getLayoutParams().width = screenHeight / 8;
    }

    private void dropCandy() {
        Random r = new Random();
        int candyNumber = r.nextInt(mCandiesCount);

        ImageView candy = candiesList.get(candyNumber);
        if (candiesStatus[candyNumber] == Constans.CANDY_ON_TOP) {
            candiesStatus[candyNumber] = Constans.CANDY_FALLEN;

            candiesRotateAnimators.get(candyNumber).cancel();

            ObjectAnimator moveXAnimator = ObjectAnimator.ofFloat(candy, "translationX", candy.getX(), previousFallenCandyPosY);
            ObjectAnimator moveYAnimator = ObjectAnimator.ofFloat(candy, "translationY", candy.getY(), screenHeight - candy.getHeight() * 1.5f );

            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(candy, "scaleX", 1f, 1.2f);
            scaleXAnimator.setDuration(100);
            scaleXAnimator.start();

            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(candy, "scaleY", 1f, 1.2f);
            scaleYAnimator.setDuration(100);
            scaleYAnimator.start();

            AnimatorSet set = new AnimatorSet();
            set.play(moveXAnimator).with(moveYAnimator);
            set.setDuration(300);
            set.start();

            mCandiesDroppedCount++;
            previousFallenCandyPosY = previousFallenCandyPosY + fallenCandyStep;
        } else dropCandy();
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

    private void checkAllPicked(){
        if(mCandiesPickedCount == mCandiesCount) {
            nextGame = (ImageButton) findViewById(R.id.btnNextABL);
            nextGame.setVisibility(View.VISIBLE);
            nextGame.setOnClickListener(mClickListener);
        }
    }

    private void nextGame() {
        Intent gameIntent = new Intent(this, GameFillActivity.class);
        gameIntent.putExtra("type", gameType);
        gameIntent.putExtra("gameNumber", mGameNumber);
        startActivity(gameIntent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();

      mShaker.resume(getApplicationContext(), mShakeListener);

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