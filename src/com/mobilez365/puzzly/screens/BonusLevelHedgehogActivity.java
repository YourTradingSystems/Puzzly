package com.mobilez365.puzzly.screens;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.AnalyticsGoogle;
import com.mobilez365.puzzly.AnimationEndListener;
import com.mobilez365.puzzly.global.PuzzlesApplication;

import java.util.*;

/**
 * Created by andrewtivodar on 15.05.2014.
 */
public class BonusLevelHedgehogActivity extends Activity {

    private int mGameType;
    private final int mCandiesCount = 20;
    private int mCandiesUnpickedCount = 0;
    private int mGameNumber;
    private ImageView bigHedgehog;
    private List<ImageView> smallCandiesList;
    private List<ImageView> bigCandiesList;
    private List<ObjectAnimator> candiesFlyAnimators;
    private List<Long> candiesFlyAnimatorsTime;
    private int mScreenHeight;
    private long passedTime;
    private boolean candiesInPause = false;

    private ImageButton nextGame;

    public class AnimatorComparator implements Comparator<ObjectAnimator> {
        @Override
        public int compare(ObjectAnimator o1, ObjectAnimator o2) {
            return (int) (o1.getStartDelay() - o2.getStartDelay());
        }
    }

    private final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            bigHedgehog.getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
            initCandies();
        }
    };

    private final View.OnTouchListener mCandyTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                pickCandy(v);
            return true;
        }
    };

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnNextABH) {
                v.setClickable(false);
                nextGame();
            }
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
            if(!candiesInPause) {
                mCandiesUnpickedCount++;
                checkAllPicked();
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_level_hedgehog);

        mGameType = getIntent().getIntExtra("type", 0);
        mGameNumber = getIntent().getIntExtra("gameNumber", 0);

        smallCandiesList = new ArrayList<ImageView>();
        bigCandiesList = new ArrayList<ImageView>();

        candiesFlyAnimators = new ArrayList<ObjectAnimator>();

        AnalyticsGoogle.fireScreenEvent(this, getString(R.string.bonus_level_hedgehog));

        bigHedgehog = (ImageView) findViewById(R.id.ivHedgehogBigABH);
        bigHedgehog.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);

        ((PuzzlesApplication) getApplicationContext()).setNeedToShowAd(true);
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
            candy.setOnTouchListener(mCandyTouchListener);

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

            candy.setTag(moveAnimator);
            candiesFlyAnimators.add(moveAnimator);
        }
        Collections.sort(candiesFlyAnimators, new AnimatorComparator());
    }

    private void pickCandy(View v) {
        v.setOnTouchListener(null);
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

        ((ObjectAnimator) v.getTag()).end();
    }

    private void checkAllPicked() {
        if (mCandiesUnpickedCount == mCandiesCount) {
            nextGame = (ImageButton) findViewById(R.id.btnNextABH);
            nextGame.setVisibility(View.VISIBLE);
            nextGame.setOnClickListener(mClickListener);
        }
    }

    private void nextGame() {
        AnalyticsGoogle.fireBonusLevelEndEvent(this, getString(R.string.bonus_level_hedgehog));

        Intent gameIntent = new Intent(this, PuzzleGameActivity.class);
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

        candiesInPause = false;

        for (int i = 0; i < candiesFlyAnimators.size(); i++) {
            if (candiesFlyAnimatorsTime != null)
                if(candiesFlyAnimatorsTime.get(i) != -1) {
                    candiesFlyAnimators.get(i).start();
                    if(candiesFlyAnimatorsTime.get(i) != 0)
                        candiesFlyAnimators.get(i).setCurrentPlayTime(candiesFlyAnimatorsTime.get(i));
                }
        }

        candiesFlyAnimatorsTime = null;

        if (nextGame != null) nextGame.setClickable(true);

        passedTime = new Date().getTime();
    }

    @Override
    protected void onPause() {
        super.onPause();

        candiesInPause = true;
        passedTime = new Date().getTime() - passedTime;
        candiesFlyAnimatorsTime = new ArrayList<Long>();
        for (ObjectAnimator candiesFlyAnimator : candiesFlyAnimators) {
            if(candiesFlyAnimator.getStartDelay() - passedTime < 0)
                candiesFlyAnimator.setStartDelay(0);
            else
                candiesFlyAnimator.setStartDelay(candiesFlyAnimator.getStartDelay() - passedTime);
            if(candiesFlyAnimator.isStarted())
                candiesFlyAnimatorsTime.add(candiesFlyAnimator.getCurrentPlayTime());
            else
                candiesFlyAnimatorsTime.add(-1l);
            candiesFlyAnimator.end();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        for (ObjectAnimator candiesFlyAnimator : candiesFlyAnimators) {
            candiesFlyAnimator.cancel();
        }

    }

}