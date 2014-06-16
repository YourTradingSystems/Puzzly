package com.mobilez365.puzzly.screens;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.util.AnimationEndListener;
import com.mobilez365.puzzly.util.BackgroundSound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by andrewtivodar on 15.05.2014.
 */
public class BonusLevelHedgehogActivity extends InterstitialActivity {

    private int mGameType;
    private final int mCandiesCount = 20;
    private int mCandiesUnpickedCount = 0;
    private int mGameNumber;
    private ImageView bigHedgehog;
    private List<ImageView> smallCandiesList;
    private List<ImageView> bigCandiesList;
    private List<ObjectAnimator> candiesFlyAnimators;
    private int mScreenHeight;

    private BackgroundSound mBackgroundSound;
    private ImageButton nextGame;

    private final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            bigHedgehog.getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
            initCandies();
        }
    };

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setClickable(false);
            if (v.getId() == R.id.btnNextABH) {
                v.setClickable(false);
                nextGame();
            } else
                pickCandy(v);
        }
    };

    private final AnimationEndListener.AnimEndListener mPickCandyAnimEndListener = new AnimationEndListener.AnimEndListener() {
        @Override
        public void OnAnimEnd(View v) {
            v.setVisibility(View.GONE);
        }
    };

    private final AnimationEndListener.AnimEndListener mFlyCandyAnimEndListener = new AnimationEndListener.AnimEndListener() {
        @Override
        public void OnAnimEnd(View v) {
            mCandiesUnpickedCount++;
            checkAllPicked();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_level_hedgehog);

        mGameType = getIntent().getIntExtra("type", 0);
        mGameNumber = getIntent().getIntExtra("gameNumber", 0);

        smallCandiesList = new ArrayList<ImageView>();
        bigCandiesList = new ArrayList<ImageView>();

        AppHelper.increasePassedGames();

        candiesFlyAnimators = new ArrayList<ObjectAnimator>();

        bigHedgehog = (ImageView) findViewById(R.id.ivHedgehogBigABH);
        bigHedgehog.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    private void initCandies() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mScreenHeight = size.y;
        int mScreenWidth = size.x;

        RelativeLayout candiesLayout = (RelativeLayout) findViewById(R.id.rlCandiesABH);
        int candiesLayoutHeight = (int) bigHedgehog.getY();

        Random r = new Random();
        List<Long> offsetList = new ArrayList<Long>();

        for (int i = 0; i < mCandiesCount; i++) {
            ImageView candy = new ImageView(this);

            boolean bigCandy = r.nextBoolean();
            int candySize;
            if (bigCandy)
                candySize = mScreenHeight / 4;
            else
                candySize = mScreenHeight / 6;

            int candyNumber = r.nextInt(4) + 1;
            int candyYPos = r.nextInt(Math.abs(candiesLayoutHeight - candySize));

            long startOffset = 0;
            boolean offsetFind = false;
            while (!offsetFind) {
                offsetFind = true;
                startOffset = r.nextInt(20000);
                for (Long offset : offsetList) {
                    if (Math.abs(offset - startOffset) < 500)
                        offsetFind = false;
                }
            }
            offsetList.add(startOffset);

            candy.setImageResource(getResources().getIdentifier("img_candy" + candyNumber, "drawable", getPackageName()));
            candy.setLayoutParams(new RelativeLayout.LayoutParams(candySize, candySize));
            candy.setPadding(10, 10, 10, 10);
            candy.setX(-candySize);
            candy.setY(candyYPos);
            candy.setTag(i);
            candy.setOnClickListener(mClickListener);

            if (bigCandy)
                bigCandiesList.add(candy);
            else
                smallCandiesList.add(candy);
            candiesLayout.addView(candy);

            ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(candy, "translationX", candy.getX(), mScreenWidth);
            moveAnimator.setDuration(7000);
            moveAnimator.setStartDelay(startOffset);
            moveAnimator.addListener(new AnimationEndListener(candy, mFlyCandyAnimEndListener));
            moveAnimator.start();

            candiesFlyAnimators.add(moveAnimator);
        }

    }

    private void pickCandy(View v) {
        v.setClickable(false);
        AppHelper.setGameAchievement(getApplicationContext(), AppHelper.getGameAchievement(getApplicationContext()) + 1);
        v.bringToFront();

        int finishCenterX;
        int finishCenterY;
        float koef;

        if (bigCandiesList.contains(v)) {
            finishCenterX = (int) (bigHedgehog.getX() + bigHedgehog.getWidth() * 0.55);
            finishCenterY = (int) (bigHedgehog.getY() + bigHedgehog.getHeight() * 0.4);
            koef = (float) ((bigHedgehog.getHeight() * 0.4) / v.getHeight());
        } else {
            ImageView smallHedgehog = (ImageView) findViewById(R.id.ivHedgehogSmallABH);
            finishCenterX = (int) (smallHedgehog.getX() + smallHedgehog.getWidth() * 0.15);
            finishCenterY = (int) (smallHedgehog.getY() + smallHedgehog.getHeight() * 0.5);
            koef = (float) ((smallHedgehog.getHeight() * 0.4) / v.getHeight());
        }

        ObjectAnimator moveXAnimator = ObjectAnimator.ofFloat(v, "translationX", v.getX(), finishCenterX);
        ObjectAnimator moveYAnimator = ObjectAnimator.ofFloat(v, "translationY", v.getY(), finishCenterY);

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(v, "scaleX", 1f, koef);
        scaleXAnimator.setDuration(100);
        scaleXAnimator.start();

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(v, "scaleY", 1f, koef);
        scaleYAnimator.setDuration(100);
        scaleYAnimator.start();

        AnimatorSet set = new AnimatorSet();
        set.play(moveXAnimator).with(moveYAnimator);
        set.setDuration(300);

        set.addListener(new AnimationEndListener(v, mPickCandyAnimEndListener));
        set.start();

        candiesFlyAnimators.get((Integer)v.getTag()).end();
    }

    private void checkAllPicked() {
        if (mCandiesUnpickedCount == mCandiesCount) {
            nextGame = (ImageButton) findViewById(R.id.btnNextABH);
            nextGame.setVisibility(View.VISIBLE);
            nextGame.setOnClickListener(mClickListener);
        }
    }

    private void nextGame() {
        Intent gameIntent = new Intent(this, GameFillActivity.class);
        gameIntent.putExtra("type", mGameType);
        gameIntent.putExtra("gameNumber", mGameNumber);
        startActivity(gameIntent);
        smallCandiesList.clear();
        bigCandiesList.clear();
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!AppHelper.isAppInBackground(getApplicationContext()))
            AppHelper.getBackgroundSound().pause(false);

        if (nextGame != null) nextGame.setClickable(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (AppHelper.isAppInBackground(getApplicationContext()) || AppHelper.isScreenOff(getApplicationContext()))
            AppHelper.getBackgroundSound().pause(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        for (ObjectAnimator candiesFlyAnimator : candiesFlyAnimators) {
            candiesFlyAnimator.cancel();
        }

    }

}