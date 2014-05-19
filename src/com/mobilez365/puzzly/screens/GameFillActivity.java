package com.mobilez365.puzzly.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.customViews.GameView;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.puzzles.PuzzleFillGame;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;

import java.util.Random;

public class GameFillActivity extends Activity implements GameView.GameCallBacks, View.OnClickListener {

    private int mGameNumber;
    private Vibrator mVibrator;
    private ImageButton nextGame;
    private ImageButton previousGame;
    private TextView gameText;
    private PuzzleFillGame mPuzzleFillGame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_fill);

        mGameNumber = AppHelper.getCurrentGame(this);

        mPuzzleFillGame = PuzzlesDB.getPuzzle(mGameNumber, this);
        ((FrameLayout) findViewById(R.id.rlForGame)).addView(new GameView(this, mPuzzleFillGame, this));

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        nextGame = (ImageButton) findViewById(R.id.btnNextAGF);
        previousGame = (ImageButton) findViewById(R.id.btnPreviousAFG);
        gameText = (TextView) findViewById(R.id.tvWordAFG);

        nextGame.setOnClickListener(this);
        previousGame.setOnClickListener(this);
    }

    private void switchGame(int gameNum) {
        AppHelper.setCurrentGame(this, gameNum);

        int passedGame =  AppHelper.getPassedGames(this);
        if(passedGame != 3)
            startActivity(new Intent(this, GameFillActivity.class));
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

            startActivity(new Intent(this, bonusLevelActivity.getClass()));
        }

        finish();
    }

    @Override
    public void onGameFinish() {
        AppHelper.increasePassedGames(this);
        AppHelper.setGameAchievement(this, AppHelper.getGameAchievement(this) + 1);

        if (mGameNumber > 0)
            previousGame.setVisibility(View.VISIBLE);

        if (PuzzlesDB.getPuzzleGameCount(this) > mGameNumber + 1)
            nextGame.setVisibility(View.VISIBLE);

        if (AppHelper.getDisplayWords(this)) {
            gameText.setVisibility(View.VISIBLE);
            gameText.setText(mPuzzleFillGame.getWord());
        }
    }

    @Override
    public void onPartMove() {
        if (AppHelper.getVibrateDragPuzzles(this))
            mVibrator.vibrate(100);
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
}