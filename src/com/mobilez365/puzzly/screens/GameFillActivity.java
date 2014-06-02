package com.mobilez365.puzzly.screens;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.customViews.GameView;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.puzzles.PuzzleFillGame;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;
import com.mobilez365.puzzly.util.AnimationEndListener;
import com.mobilez365.puzzly.util.BackgroundSound;

import java.util.Random;

public class GameFillActivity extends RestartActivty implements GameView.GameCallBacks, View.OnClickListener, AnimationEndListener.AnimEndListener {

    private boolean gameIsFinished = false;
    private int mGameType;
    private int mGameNumber;
    private Vibrator mVibrator;
    private ImageButton nextGame;
    private ImageButton previousGame;
    private TextView gameText;
    private PuzzleFillGame mPuzzleFillGame;
    private MediaPlayer mPlayer;
    private BackgroundSound mBackgroundSound;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_fill);

        mBackgroundSound = AppHelper.getBackgroundSound();

        mGameType = getIntent().getIntExtra("type", 0);
        mGameNumber = getIntent().getIntExtra("gameNumber", 0);
        AppHelper.setCurrentGame(this, mGameNumber, mGameType);

        mPuzzleFillGame = PuzzlesDB.getPuzzle(mGameNumber, mGameType, this);
        ((FrameLayout) findViewById(R.id.rlForGame)).addView(new GameView(this, mPuzzleFillGame, this));

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        nextGame = (ImageButton) findViewById(R.id.btnNextAGF);
        previousGame = (ImageButton) findViewById(R.id.btnPreviousAGF);
        gameText = (TextView) findViewById(R.id.tvWordAFG);

        nextGame.setOnClickListener(this);
        previousGame.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppHelper.changeLanguage(this, AppHelper.getLocaleLanguage(this).name());

        if (!AppHelper.isAppInBackground(this)) {
            if (mBackgroundSound != null && !mBackgroundSound.isPlay())
                mBackgroundSound.pause(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (AppHelper.isAppInBackground(this) || AppHelper.isScreenOff(this)) {
            if (mBackgroundSound != null && mBackgroundSound.isPlay())
                mBackgroundSound.pause(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppHelper.startBackgroundSound(this, Constans.MENU_BACKGROUND_MUSIC);
    }

    private void switchGame(boolean nextGame) {
        int passedGame = AppHelper.getPassedGames();
        if (passedGame != 3) {
            Intent gameIntent = new Intent(this, GameFillActivity.class);
            gameIntent.putExtra("type", mGameType); if(nextGame)
                gameIntent.putExtra("gameNumber", AppHelper.getNextGame(this, mGameType));
            else
                gameIntent.putExtra("gameNumber", AppHelper.getPreviousGame(this, mGameType));
            startActivity(gameIntent);
        } else {
            Random r = new Random();
            int bonusLevelIndex = r.nextInt(3);

            Activity bonusLevelActivity = null;
            switch (bonusLevelIndex) {
                case 0:
                    bonusLevelActivity = new BonusLevelTreeActivity();
                    break;
                case 1:
                    bonusLevelActivity = new BonusLevelShakeActivity();
                    break;
                case 2:
                    bonusLevelActivity = new BonusLevelFlowerActivity();
                    break;
            }
            Intent gameIntent = new Intent(this, bonusLevelActivity.getClass());
            gameIntent.putExtra("type", mGameType);
            if(nextGame)
                gameIntent.putExtra("gameNumber", AppHelper.getNextGame(this, mGameType));
            else
                gameIntent.putExtra("gameNumber", AppHelper.getPreviousGame(this, mGameType));
            startActivity(gameIntent);
        }

        finish();
    }

    private void showBasketAnimation() {
        RelativeLayout basketLayout = (RelativeLayout) findViewById(R.id.rlBasketsAFG);
        basketLayout.setVisibility(View.VISIBLE);

        ImageView basket = (ImageView) findViewById(R.id.ivBasketAGF);
        ImageView candy = (ImageView) findViewById(R.id.ivCandyAFG);

        ObjectAnimator moveYAnimator = ObjectAnimator.ofFloat(candy, "translationY", candy.getY(), basket.getY() + basket.getHeight() / 2);
        moveYAnimator.setDuration(1000);
        moveYAnimator.addListener(new AnimationEndListener(candy, this));
        moveYAnimator.start();
    }

    private void showArrowAnimation(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        ObjectAnimator moveXAnimator = ObjectAnimator.ofFloat(gameText, "translationX", gameText.getX(), size.x / 2 - gameText.getWidth() / 2 );
        moveXAnimator.setDuration(1000);
        moveXAnimator.start();
    }

    @Override
    public void onGameFinish() {

        String excellent_words[] = new String[]{"excellent", "well_done"};
        Random random = new Random();
        String excellent_word = excellent_words[random.nextInt(excellent_words.length)];

        gameText.setVisibility(View.VISIBLE);

        if (!gameIsFinished) {
            AppHelper.increasePassedGames();
            AppHelper.setMaxGame(this, mGameNumber + 1, mGameType);
            AppHelper.setGameAchievement(this, AppHelper.getGameAchievement(this) + 1);
            showBasketAnimation();
            gameIsFinished = true;

            if (AppHelper.getLocaleLanguage(this).equals(AppHelper.Languages.eng))
                gameText.setText(mPuzzleFillGame.getWordEng());
            else if (AppHelper.getLocaleLanguage(this).equals(AppHelper.Languages.rus))
                gameText.setText(mPuzzleFillGame.getWordRus());

            if (AppHelper.getPlaySound(this)) {
                mPlayer = AppHelper.playSound(this, excellent_word);

                final Activity activity = this;
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (AppHelper.getPlaySound(activity)) {
                            AppHelper.playSound(activity, mPuzzleFillGame.getItemName());
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onPartMove() {
        if (AppHelper.getVibrate(this))
            mVibrator.vibrate(100);
    }

    @Override
    public void onPartsLock() {
        if (AppHelper.getVibrate(this))
            mVibrator.vibrate(100);
    }

    @Override
    public void onClick(View v) {
        if (v.isClickable()) {
            nextGame.setClickable(false);
            switch (v.getId()) {
                case R.id.btnNextAGF:
                    switchGame(true);
                    break;
                case R.id.btnPreviousAGF:
                    switchGame(false);
                    break;
            }
        }
    }

    @Override
    public void OnAnimEnd(View v) {
        showArrowAnimation();
        if (AppHelper.getNextGame(this, mGameType) != -1)
            nextGame.setVisibility(View.VISIBLE);

        if (mGameNumber != 0)
            previousGame.setVisibility(View.VISIBLE);

        v.setVisibility(View.GONE);
        findViewById(R.id.ivBasketAGF).setVisibility(View.GONE);
    }
}
