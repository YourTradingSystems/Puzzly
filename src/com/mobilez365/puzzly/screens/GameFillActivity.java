package com.mobilez365.puzzly.screens;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.graphics.Point;
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
import com.mobilez365.puzzly.util.BackgroundSound;

import java.util.Random;

public class GameFillActivity extends Activity implements GameView.GameCallBacks, View.OnClickListener, AnimationEndListener.AnimEndListener {

    private int mGameType;
    private int mGameNumber;
    private Vibrator mVibrator;
    private ImageButton nextGame;
    private ImageButton previousGame;
    private TextView gameText;
    private PuzzleFillGame mPuzzleFillGame;
    private ImageView basket;

    private boolean isFirsOpenFragment = false;
    private MediaPlayer mPlayer;
    private BackgroundSound mBackgroundSound;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_fill);

        mBackgroundSound = AppHelper.getBackgroundSound();

        mGameType = getIntent().getIntExtra("type", 0);
        mGameNumber = AppHelper.getCurrentGame(this, mGameType);

        mPuzzleFillGame = PuzzlesDB.getPuzzle(mGameNumber, mGameType, this);
        ((FrameLayout) findViewById(R.id.rlForGame)).addView(new GameView(this, mPuzzleFillGame, this));

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        nextGame = (ImageButton) findViewById(R.id.btnNextAGF);
        previousGame = (ImageButton) findViewById(R.id.btnPreviousAFG);
        gameText = (TextView) findViewById(R.id.tvWordAFG);
        basket = (ImageView) findViewById(R.id.ivBasketAGF);

        nextGame.setOnClickListener(this);
        previousGame.setOnClickListener(this);

        showArrows();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppHelper.getPlayBackgroundMusic(this))
            mBackgroundSound.pause(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (AppHelper.getPlayBackgroundMusic(this))
            mBackgroundSound.pause(true);
    }

    private void switchGame(int gameNum) {
        AppHelper.setCurrentGame(this, gameNum, mGameType);

        int passedGame = AppHelper.getPassedGames(this);
        if(passedGame != 3)  {
            Intent gameIntent = new Intent(this, GameFillActivity.class);
            gameIntent.putExtra("type", mGameType);
            startActivity(gameIntent);
        }
        else {
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
            startActivity(gameIntent);
        }

        finish();
    }

    private void showBasketAnimation() {
        RelativeLayout basketLayout = (RelativeLayout) findViewById(R.id.rlBasketsAFG);
        basketLayout.setVisibility(View.VISIBLE);

        ImageView candy = (ImageView) findViewById(R.id.ivCandyAFG);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        RelativeLayout.LayoutParams basketLayoutParam = (RelativeLayout.LayoutParams) basketLayout.getLayoutParams();
        basketLayoutParam.width = size.x / 2;
        basketLayout.setLayoutParams(basketLayoutParam);

        ObjectAnimator moveXAnimator = ObjectAnimator.ofFloat(candy, "translationY", basket.getY() - basket.getHeight()/2, basket.getHeight()/2);
        moveXAnimator.setDuration(600);
        moveXAnimator.addListener(new AnimationEndListener(candy, this));
        moveXAnimator.start();
    }

    private void showArrows(){
        if (mGameNumber > 0)
            previousGame.setVisibility(View.VISIBLE);

        if (AppHelper.getMaxGame(this, mGameType) > mGameNumber)
            nextGame.setVisibility(View.VISIBLE);
    }

    private void hideArrows(){
            previousGame.setVisibility(View.GONE);
            nextGame.setVisibility(View.GONE);
    }

    @Override
    public void onGameFinish() {
        String excellent_words[] = new String[]{"excellent", "well_done"};
        Random random = new Random();
        String excellent_word = excellent_words[random.nextInt(excellent_words.length)];

        AppHelper.increasePassedGames(this);
        AppHelper.setMaxGame(this, mGameNumber + 1, mGameType);
        AppHelper.setGameAchievement(this, AppHelper.getGameAchievement(this) + 1);

        if (AppHelper.getDisplayWords(this)) {
            gameText.setVisibility(View.VISIBLE);
            if (AppHelper.getLocaleLanguage(this).equals(AppHelper.Languages.us))
                gameText.setText(mPuzzleFillGame.getWordEng());
            else if (AppHelper.getLocaleLanguage(this).equals(AppHelper.Languages.ru))
                gameText.setText(mPuzzleFillGame.getWordRus());
        }

        if (AppHelper.getPlaySoundImageAppear(this) && !isFirsOpenFragment) {
            mPlayer = AppHelper.playSound(this, excellent_word);

            final Activity activity = this;
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (AppHelper.getVoiceForDisplayWords(activity)) {
                        AppHelper.playSound(activity, mPuzzleFillGame.getItemName());
                    }
                }
            });
            isFirsOpenFragment = true;
        }

        if (AppHelper.getVoiceForDisplayWords(this)) {
            if (mPlayer == null)
                mPlayer = AppHelper.playSound(this, mPuzzleFillGame.getItemName());
            else if (!mPlayer.isPlaying()) {
                mPlayer = AppHelper.playSound(this, mPuzzleFillGame.getItemName());
            }
        }


        showBasketAnimation();
    }

    @Override
    public void onPartMove() {
        if (AppHelper.getVibrateDragPuzzles(this))
            mVibrator.vibrate(100);
        hideArrows();

    }

    @Override
    public void onPartsLock() {
        if (AppHelper.getVibratePieceInPlace(this))
            mVibrator.vibrate(100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextAGF:
                switchGame(mGameNumber + 1);
                break;
            case R.id.btnPreviousAFG:
                switchGame(mGameNumber - 1);
                break;
        }
    }

    @Override
    public void OnAnimEnd(View v) {
        showArrows();
        v.setVisibility(View.INVISIBLE);
    }
}
