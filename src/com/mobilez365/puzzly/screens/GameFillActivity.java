package com.mobilez365.puzzly.screens;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.View;
import android.widget.*;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.customViews.GameView;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.puzzles.PuzzleFillGame;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;
import com.mobilez365.puzzly.util.AnimationEndListener;
import com.mobilez365.puzzly.util.ParseSvgAsyncTask;

import java.util.Random;

public class GameFillActivity extends RestartActivty implements GameView.GameCallBacks, View.OnClickListener, AnimationEndListener.AnimEndListener, ParseSvgAsyncTask.ParseListener {

    private boolean gameIsFinished = false;
    private int mGameType;
    private int mGameNumber;
    private Vibrator mVibrator;
    private ImageButton nextGame;
    private ImageButton previousGame;
    private ImageView ivResultImage;
    private TextView gameText;
    private PuzzleFillGame mPuzzleFillGame;
    private MediaPlayer mPlayer;
    private boolean mFirstPlayItemSound;
    private GameView gameView;
    private Point displaySize;
    private int resultImageXPos;
    private int resultImageYPos;

    private MediaPlayer mExcellentWord;
    private MediaPlayer mItemWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_fill);

        if (AppHelper.getPlayBackgroundMusic(this) && !AppHelper.getBackgroundSound().getName().equals(Constans.GAME_BACKGROUND_MUSIC))
            AppHelper.startBackgroundSound(this, Constans.GAME_BACKGROUND_MUSIC);

        mGameType = getIntent().getIntExtra("type", 0);
        mGameNumber = getIntent().getIntExtra("gameNumber", 0);
        AppHelper.setCurrentGame(this, mGameNumber, mGameType);

        mPuzzleFillGame = PuzzlesDB.getPuzzle(mGameNumber, mGameType, this);
        gameView = new GameView(this, mPuzzleFillGame, this);
        ((FrameLayout) findViewById(R.id.rlForGame)).addView(gameView);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        nextGame = (ImageButton) findViewById(R.id.btnNextAGF);
        previousGame = (ImageButton) findViewById(R.id.btnPreviousAGF);
        gameText = (TextView) findViewById(R.id.tvWordAFG);
        ivResultImage = (ImageView) findViewById(R.id.ivResultImageAGF);
        findViewById(R.id.rlBackgroundAFG).setBackgroundColor(Color.TRANSPARENT);

        nextGame.setOnClickListener(this);
        previousGame.setOnClickListener(this);

        Display display = getWindowManager().getDefaultDisplay();
        displaySize = new Point();
        display.getSize(displaySize);

        mFirstPlayItemSound = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppHelper.changeLanguage(this, AppHelper.getLocaleLanguage(this, Constans.GAME_LANGUAGE).name());

        if (!AppHelper.isAppInBackground(this)) {
            AppHelper.getBackgroundSound().pause(false);

            if (!mFirstPlayItemSound && mPlayer != null)
                mPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (AppHelper.isAppInBackground(this) || AppHelper.isScreenOff(this)) {
            AppHelper.getBackgroundSound().pause(true);

            if (!mFirstPlayItemSound && mPlayer != null)
                mPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameView.release();
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
            int bonusLevelIndex = r.nextInt(4);

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
                case 3:
                    bonusLevelActivity = new BonusLevelHedgehogActivity();
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

    private void showResultImageAnimation(Bitmap resultImage, int x, int y) {
        ivResultImage.setImageBitmap(resultImage);
        ivResultImage.setX(x);
        ivResultImage.setY(y);

        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(ivResultImage, "alpha", 0, 1, 1);
        alphaAnim.setDuration(1000);
        alphaAnim.start();
    }

    private void showMoveToCenterAnimation(){
        ObjectAnimator moveXAnimator = ObjectAnimator.ofFloat(gameText, "translationX", gameText.getX(), displaySize.x / 2 - gameText.getWidth() / 2 );
        moveXAnimator.setDuration(1000);
        moveXAnimator.start();

        int newFigureSize = (int) (displaySize.y - ((displaySize.y - gameText.getY()) * 2));
        float koef = newFigureSize / (float) ivResultImage.getHeight();
        if(koef > 1)
            koef = 1;
        ObjectAnimator moveImageXAnimator = ObjectAnimator.ofFloat(ivResultImage, "translationX", ivResultImage.getX(), displaySize.x / 2 - ivResultImage.getWidth() / 2 );
        ObjectAnimator scaleImageXAnimator = ObjectAnimator.ofFloat(ivResultImage, "scaleX", 1f, koef);
        ObjectAnimator scaleImageYAnimator = ObjectAnimator.ofFloat(ivResultImage, "scaleY", 1f, koef);

        AnimatorSet set = new AnimatorSet();
        set.play(moveImageXAnimator).with(scaleImageXAnimator).with(scaleImageYAnimator);
        set.setDuration(1000);
        set.start();
    }

    @Override
    public void onGameFinish(String resultImage, int x, int y, int width, int height) {
        if (!gameIsFinished) {
            AppHelper.increasePassedGames();
            AppHelper.setMaxGame(this, mGameNumber + 1, mGameType);
            AppHelper.setGameAchievement(this, AppHelper.getGameAchievement(this) + 1);
            resultImageXPos = x;
            resultImageYPos = y;

            ParseSvgAsyncTask parseSvgAsyncTask = new ParseSvgAsyncTask(this, this, width, height);
            parseSvgAsyncTask.execute(resultImage);

            gameIsFinished = true;
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
        findViewById(R.id.rlBackgroundAFG).setBackgroundColor(getResources().getColor(R.color.background_game));
        showMoveToCenterAnimation();

        if (AppHelper.getNextGame(this, mGameType) != -1)
            nextGame.setVisibility(View.VISIBLE);

        if (mGameNumber != 0)
            previousGame.setVisibility(View.VISIBLE);

        v.setVisibility(View.GONE);
        findViewById(R.id.ivBasketAGF).setVisibility(View.GONE);
    }

    @Override
    public void onParseDone(Bitmap bitmap) {
        gameText.setVisibility(View.VISIBLE);

        showBasketAnimation();
        showResultImageAnimation(bitmap, resultImageXPos, resultImageYPos);

        gameText.setText(mPuzzleFillGame.getWord());

        if (AppHelper.getPlaySound(this)) {
            mPlayer = AppHelper.playSound(this, mPuzzleFillGame.getItemName());
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mFirstPlayItemSound = true;
                }
            });
        }

        //  String excellent_words[] = new String[]{"perfect", "wonderfull", "well_done", "good_job"};
        //  Random random = new Random();
        //  String excellent_word = excellent_words[random.nextInt(excellent_words.length)];

                   /* if (AppHelper.getPlaySound(this)) {
                mExcellentWord = AppHelper.playSound(this, excellent_word);

                final Activity activity = this;
                mExcellentWord.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (AppHelper.getPlaySound(activity)) {
                            AppHelper.playSound(activity, mPuzzleFillGame.getItemName());
                        }
                    }
                });*/
    }
}
