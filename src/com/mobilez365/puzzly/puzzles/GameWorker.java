package com.mobilez365.puzzly.puzzles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import com.mobilez365.puzzly.ParseSvgAsyncTask;
import com.mobilez365.puzzly.customViews.AutoResizeImageView;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.PuzzlesApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrewtivodar on 15.07.2014.
 */
public class GameWorker {

    public interface GameCallBacks {
        public void onAllPuzzleLocked();

        public void onGameFinish(AutoResizeImageView resultImage);
    }

    private final ParseSvgAsyncTask.ParseListener parseListener = new ParseSvgAsyncTask.ParseListener() {
        @Override
        public void onParseDone(Drawable drawable) {
            resultPuzzleDrawable = drawable;
        }
    };

    private final AnimatorListenerAdapter resultImageAnimationListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            for (GameSprite gamePart : gameParts)
                gamePart.getPuzzlePart().setVisibility(View.GONE);
            gameCallBacksListener.onGameFinish(wholePuzzleImage);
        }
    };

    private final View.OnTouchListener puzzlePartsMoveListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int X = (int) event.getRawX();
            int Y = (int) event.getRawY();

            int partNum = (Integer) v.getTag();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    gameParts.get(partNum).deltaX = (int) event.getX();
                    gameParts.get(partNum).deltaY = (int) event.getY();
                    v.bringToFront();
                    v.getParent().requestLayout();
                    vibrate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    v.bringToFront();
                    v.getParent().requestLayout();

                    int deltaX = gameParts.get(partNum).deltaX;
                    int deltaY = gameParts.get(partNum).deltaY;

                    int newX = X - deltaX;
                    int newY = Y - deltaY;

                    int maxXPos = screenSize.x - v.getWidth();
                    int maxYPos = screenSize.y - v.getHeight();

                    if (newX >= 0 && newX <= maxXPos)
                        v.setX(X - deltaX);
                    else
                        gameParts.get(partNum).deltaX = (int) event.getX();

                    if (newY >= 0 && newY <= maxYPos)
                        v.setY(Y - deltaY);
                    else
                        gameParts.get(partNum).deltaY = (int) event.getY();

                    if (checkPuzzlePartPosition(partNum, newX, newY)) {
                        vibrate();
                        checkAllPartsLocked();
                    }

                    break;
            }
            return true;
        }
    };

    private AutoResizeImageView wholePuzzleImage;
    private Drawable resultPuzzleDrawable;
    private List<GameSprite> gameParts;
    private Point screenSize;
    private Context context;
    private Vibrator mVibrator;
    private static final int CATCH_DISTANCE = 15;

    private GameCallBacks gameCallBacksListener;
    PuzzleGame puzzleGame;

    public GameWorker(Context _context, PuzzleGame _puzzleGame, GameCallBacks _gameCallBacksListener) {
        this.context = _context;
        this.gameCallBacksListener = _gameCallBacksListener;
        this.puzzleGame = _puzzleGame;
        gameParts = new ArrayList<GameSprite>();

        screenSize = ((PuzzlesApplication) context.getApplicationContext()).getScreenSize();

        ParseSvgAsyncTask parseSvgAsyncTask = new ParseSvgAsyncTask(context, parseListener);
        parseSvgAsyncTask.execute(puzzleGame.getResultImage());
    }

    public Point resizePartFinalPosition(Point oldPos){
        int newPosY = Math.round(oldPos.y / AutoResizeImageView.baseHeight * screenSize.y);
        int newPosX = Math.round(screenSize.y / AutoResizeImageView.baseHeight * oldPos.x);
        return new Point(newPosX, newPosY);
    }

    private void vibrate() {
        if (AppHelper.getVibrate(context)) {
            if (mVibrator == null)
                mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            mVibrator.vibrate(100);
        }
    }

    public void startGame(RelativeLayout gameLayout) {
        wholePuzzleImage = new AutoResizeImageView(context, puzzleGame.getFigurePos());
        wholePuzzleImage.setImageFromNameInThread(puzzleGame.getImage());
        gameLayout.addView(wholePuzzleImage);

        int i = 0;
        for (PuzzlesPart puzzlesPart : puzzleGame.getParts()) {
            AutoResizeImageView ivPart = new AutoResizeImageView(context, puzzlesPart.currentPartLocation);
            ivPart.setImageFromNameInThread(puzzlesPart.partImage);
            ivPart.setOnTouchListener(puzzlePartsMoveListener);
            ivPart.setTag(i);
            gameLayout.addView(ivPart);

            Point finalPartPos = resizePartFinalPosition(puzzlesPart.finalPartLocation);
            GameSprite puzzlePartSprite = new GameSprite(ivPart, finalPartPos.x,
                    finalPartPos.y);
            if (puzzleGame.getGameType() == 1 && i > 0)
                puzzlePartSprite.setPieceLocked(true);
            gameParts.add(puzzlePartSprite);
            i++;
        }

        if (puzzleGame.getGameType() == 1) {
            AutoResizeImageView ivRevealGamePart = new AutoResizeImageView(context, puzzleGame.getFigurePos());
            ivRevealGamePart.setImageFromNameInThread(puzzleGame.getImage());
            gameLayout.addView(ivRevealGamePart);
            GameSprite puzzlePartSprite = new GameSprite(ivRevealGamePart, 0, 0);
            puzzlePartSprite.setPieceLocked(true);
            gameParts.add(puzzlePartSprite);
        }
    }

    private void hideFakeParts(){
        for (int i = 1; i < gameParts.size() - 1; i++)
            gameParts.get(i).getPuzzlePart().setVisibility(View.GONE);
    }

    private boolean checkPuzzlePartPosition(int partNum, int puzzlePosX, int puzzlePosY) {
        GameSprite currentSprite = gameParts.get(partNum);
        int puzzleFinalPosX = currentSprite.lockedX + (int)wholePuzzleImage.getX();
        int puzzleFinalPosY = currentSprite.lockedY + (int)wholePuzzleImage.getY();
        if ((puzzleFinalPosX > puzzlePosX - CATCH_DISTANCE && puzzleFinalPosX < puzzlePosX + CATCH_DISTANCE
                && puzzleFinalPosY > puzzlePosY - CATCH_DISTANCE && puzzleFinalPosY < puzzlePosY + CATCH_DISTANCE)) {
            onPartLock(currentSprite, puzzleFinalPosX, puzzleFinalPosY);
            return true;
        }
        return false;
    }

    private void checkAllPartsLocked() {
        boolean allPuzzleLocked = true;
        for (GameSprite gamePart : gameParts)
            if (!gamePart.isPieceLocked()) {
                allPuzzleLocked = false;
                break;
            }
        if (allPuzzleLocked)
            onGameFinish();
    }

    private void onPartLock(GameSprite currentSprite, int finalPartPosX, int finalPartPosY) {
        AutoResizeImageView lockedPart = currentSprite.getPuzzlePart();
        lockedPart.setX(finalPartPosX);
        lockedPart.setY(finalPartPosY);
        currentSprite.setPieceLocked(true);
        lockedPart.setOnTouchListener(null);
    }

    private void onGameFinish() {
        if(puzzleGame.getGameType() == 1)
            hideFakeParts();

        wholePuzzleImage.setImageDrawable(resultPuzzleDrawable);
        wholePuzzleImage.bringToFront();

        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(wholePuzzleImage, "alpha", 0, 1, 1);
        alphaAnim.setDuration(1000);
        alphaAnim.addListener(resultImageAnimationListener);
        alphaAnim.start();
        gameCallBacksListener.onAllPuzzleLocked();
    }
}
