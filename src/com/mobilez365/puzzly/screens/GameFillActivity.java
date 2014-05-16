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
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.puzzles.PuzzleFillGame;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;

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

        mGameNumber = getIntent().getIntExtra(Constans.GAME_NUMBER_EXTRA, 0);

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
        Intent gameIntent = new Intent(this, GameFillActivity.class);
        gameIntent.putExtra(Constans.GAME_NUMBER_EXTRA, gameNum);
        startActivity(gameIntent);
        finish();
    }

    @Override
    public void onGameFinish() {
        if (mGameNumber > 0)
            previousGame.setVisibility(View.VISIBLE);

        if (PuzzlesDB.getPuzzleGameCount(this) > mGameNumber + 1)
            nextGame.setVisibility(View.VISIBLE);

        gameText.setVisibility(View.VISIBLE);
        gameText.setText(mPuzzleFillGame.getWord());
    }

    @Override
    public void onPartMove() {
        mVibrator.vibrate(100);
    }

    @Override
    public void onPartsLock() {
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