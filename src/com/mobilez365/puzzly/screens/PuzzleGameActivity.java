package com.mobilez365.puzzly.screens;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.customViews.AutoResizeImageView;
import com.mobilez365.puzzly.global.*;
import com.mobilez365.puzzly.puzzles.GameWorker;
import com.mobilez365.puzzly.puzzles.PuzzleGame;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;

public class PuzzleGameActivity extends Activity {

    private int mGameType;
    private int mGameNumber;
    private AutoResizeImageView ivResultImage;
    private final int resultImageId = 1001;
    private TextView gameText;
    private String mPuzzleWord;

    private final GameWorker.GameCallBacks gameCallBacks = new GameWorker.GameCallBacks() {
        @Override
        public void onAllPuzzleLocked() {
            ((PuzzlesApplication) getApplication()).increaseStartedGamesCount();
            AppHelper.setMaxGame(getApplicationContext(), mGameNumber + 1, mGameType);
            AppHelper.setGameAchievement(getApplicationContext(), AppHelper.getGameAchievement(getApplicationContext()) + 1);

            gameText.setVisibility(View.VISIBLE);
            gameText.setText(mPuzzleWord);
            showCandyToBasketAnimation();

            SoundManager.playWordSound();
        }

        @Override
        public void onGameFinish(AutoResizeImageView resultImage) {
            ivResultImage = resultImage;
            ivResultImage.setId(resultImageId);
            ivResultImage.setOnClickListener(mOnClickListener);
            showMoveToCenterAnimation();

            if (AppHelper.getNextGame(getApplicationContext(), mGameType) != -1)
                findViewById(R.id.btnNextAGF).setVisibility(View.VISIBLE);

            if (mGameNumber != 0)
                findViewById(R.id.btnPreviousAGF).setVisibility(View.VISIBLE);

            findViewById(R.id.ivBasketAGF).setVisibility(View.GONE);
            findViewById(R.id.ivCandyAFG).setVisibility(View.GONE);
        }
    };

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnNextAGF:
                    switchGame(true);
                    break;
                case R.id.btnPreviousAGF:
                    switchGame(false);
                    break;
                case resultImageId:
                    SoundManager.playWordSound();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InterstitialAD.showFullAD(getApplicationContext());

        setContentView(R.layout.activity_puzzle_game);

        mGameType = getIntent().getIntExtra("type", 0);
        mGameNumber = getIntent().getIntExtra("gameNumber", 0);
        AppHelper.setCurrentGame(getApplicationContext(), mGameNumber, mGameType);

        PuzzleGame mPuzzleGame = PuzzlesDB.getPuzzle(mGameNumber, mGameType, getApplicationContext());
        mPuzzleWord = mPuzzleGame.getWord(getApplicationContext());
        SoundManager.initWordSound(getApplicationContext(), mPuzzleGame.getItemName());
        sendAnalytics(mPuzzleGame.getItemName());

        GameWorker gameWorker = new GameWorker(getApplicationContext(), mPuzzleGame, gameCallBacks);
        gameWorker.startGame((RelativeLayout) findViewById(R.id.rlForGame));

        gameText = (TextView) findViewById(R.id.tvWordAFG);

        findViewById(R.id.btnNextAGF).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnPreviousAGF).setOnClickListener(mOnClickListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SoundManager.releaseWordPlayer();
    }

    private void sendAnalytics(String puzzleName) {
        if (mGameType == 0)
            AnalyticsGoogle.fireScreenEvent(getApplicationContext(), getString(R.string.activity_fill_game));
        else
            AnalyticsGoogle.fireScreenEvent(getApplicationContext(), getString(R.string.activity_reveal_game));

        AnalyticsGoogle.fireLevelStartedEvent(getApplicationContext(), puzzleName);
    }

    private void switchGame(boolean isNextGame) {
        Class nextGameClass = AppHelper.getNextGameClass(getApplicationContext());

        Intent gameIntent = new Intent(this, nextGameClass);
        gameIntent.putExtra("type", mGameType);
        if (isNextGame)
            gameIntent.putExtra("gameNumber", AppHelper.getNextGame(getApplicationContext(), mGameType));
        else
            gameIntent.putExtra("gameNumber", AppHelper.getPreviousGame(getApplicationContext(), mGameType));
        startActivity(gameIntent);

        finish();
    }

    private void showCandyToBasketAnimation() {
        RelativeLayout basketLayout = (RelativeLayout) findViewById(R.id.rlBasketsAFG);
        basketLayout.setVisibility(View.VISIBLE);

        ImageView basket = (ImageView) findViewById(R.id.ivBasketAGF);
        ImageView candy = (ImageView) findViewById(R.id.ivCandyAFG);

        float basketMiddleYPos = basket.getY() + basket.getHeight() / 2;
        ObjectAnimator moveCandyYAnimator = ObjectAnimator.ofFloat(candy, "translationY", candy.getY(), basketMiddleYPos);
        moveCandyYAnimator.setDuration(1000);
        moveCandyYAnimator.start();
    }

    private void showMoveToCenterAnimation() {
        Point screenSize = ((PuzzlesApplication) getApplication()).getScreenSize();

        ObjectAnimator moveTextXAnimator = ObjectAnimator.ofFloat(gameText, "translationX", gameText.getX(), screenSize.x / 2 - gameText.getWidth() / 2);
        moveTextXAnimator.setDuration(1000);
        moveTextXAnimator.start();

        int newFigureSize = (int) (screenSize.y - ((screenSize.y - gameText.getY()) * 2));
        float koef = newFigureSize / (float) ivResultImage.getHeight();
        if (koef > 1)
            koef = 1;
        ObjectAnimator moveImageXAnimator = ObjectAnimator.ofFloat(ivResultImage, "translationX", ivResultImage.getX(), screenSize.x / 2 - ivResultImage.getWidth() / 2);
        ObjectAnimator scaleImageXAnimator = ObjectAnimator.ofFloat(ivResultImage, "scaleX", 1f, koef);
        ObjectAnimator scaleImageYAnimator = ObjectAnimator.ofFloat(ivResultImage, "scaleY", 1f, koef);

        AnimatorSet set = new AnimatorSet();
        set.play(moveImageXAnimator).with(scaleImageXAnimator).with(scaleImageYAnimator);
        set.setDuration(1000);
        set.start();
    }

}
