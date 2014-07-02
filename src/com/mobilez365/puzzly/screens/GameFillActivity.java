package com.mobilez365.puzzly.screens;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.*;
import android.util.Log;
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

public class GameFillActivity extends RestartActivty {

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
    private boolean mIsPlaySound;
    private GameView gameView;
    private Point displaySize;
    private int resultImageXPos;
    private int resultImageYPos;

    private MediaPlayer mExcellentWord;
    private MediaPlayer mItemWord;
    private Bitmap resImage;

    private final ParseSvgAsyncTask.ParseListener mParseDoneListener = new ParseSvgAsyncTask.ParseListener() {
        @Override
        public void onParseDone(Bitmap bitmap) {
            gameText.setVisibility(View.VISIBLE);

            showBasketAnimation();
            showResultImageAnimation(bitmap, resultImageXPos, resultImageYPos);

            gameText.setText(mPuzzleFillGame.getWord(getApplicationContext()));

            if (AppHelper.getPlaySound(getApplicationContext())) {
                mPlayer = AppHelper.playSound(getApplicationContext(), mPuzzleFillGame.getItemName());
                mIsPlaySound = true;
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mIsPlaySound = false;
                        ivResultImage.setClickable(true);
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
    };

    private final GameView.GameCallBacks gameCallBacks = new GameView.GameCallBacks() {
        @Override
        public void onGameFinish(String resultImage, int x, int y, int width, int height) {
            if (!gameIsFinished) {
                AppHelper.increasePassedGames();
                AppHelper.setMaxGame(getApplicationContext(), mGameNumber + 1, mGameType);
                AppHelper.setGameAchievement(getApplicationContext(), AppHelper.getGameAchievement(getApplicationContext()) + 1);
                resultImageXPos = x;
                resultImageYPos = y;

                ParseSvgAsyncTask parseSvgAsyncTask = new ParseSvgAsyncTask(getApplicationContext(), mParseDoneListener, width, height);
                parseSvgAsyncTask.execute(resultImage);

                gameIsFinished = true;
            }
        }

        @Override
        public void onPartMove() {
            if (AppHelper.getVibrate(getApplicationContext()))
                mVibrator.vibrate(100);
        }

        @Override
        public void onPartsLock() {
            if (AppHelper.getVibrate(getApplicationContext()))
                mVibrator.vibrate(100);
        }
    };

    private final AnimationEndListener.AnimEndListener mAnimEndListener = new AnimationEndListener.AnimEndListener() {
        @Override
        public void OnAnimEnd(View v) {
            findViewById(R.id.rlBackgroundAFG).setBackgroundColor(getResources().getColor(R.color.background_game));
            showMoveToCenterAnimation();

            if (AppHelper.getNextGame(getApplicationContext(), mGameType) != -1)
                nextGame.setVisibility(View.VISIBLE);

            if (mGameNumber != 0)
                previousGame.setVisibility(View.VISIBLE);

            v.setVisibility(View.GONE);
            findViewById(R.id.ivBasketAGF).setVisibility(View.GONE);
        }
    };

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.isClickable()) {
                switch (v.getId()) {
                    case R.id.btnNextAGF:
                        nextGame.setClickable(false);
                        previousGame.setClickable(false);
                        if(mPlayer != null) {
                            mPlayer.stop();
                            mPlayer.release();
                        }
                        switchGame(true);
                        break;
                    case R.id.btnPreviousAGF:
                        nextGame.setClickable(false);
                        previousGame.setClickable(false);
                        if(mPlayer != null) {
                            mPlayer.stop();
                            mPlayer.release();
                        }
                        switchGame(false);
                        break;
                    case R.id.ivResultImageAGF:
                        ivResultImage.setClickable(false);
                        mIsPlaySound = true;

                        AppHelper.changeLanguage(getApplicationContext(), AppHelper.getLocaleLanguage(getApplicationContext(), Constans.GAME_LANGUAGE).name());
                        mPlayer = AppHelper.playSound(getApplicationContext(), mPuzzleFillGame.getItemName());
                        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                ivResultImage.setClickable(true);
                                mIsPlaySound = false;
                            }
                        });
                        break;
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_fill);

        if (AppHelper.getPlayBackgroundMusic(getApplicationContext()) && !AppHelper.getBackgroundSound().getName().equals(Constans.GAME_BACKGROUND_MUSIC))
            AppHelper.startBackgroundSound(getApplicationContext(), Constans.GAME_BACKGROUND_MUSIC);

        mGameType = getIntent().getIntExtra("type", 0);
        mGameNumber = getIntent().getIntExtra("gameNumber", 0);
        AppHelper.setCurrentGame(getApplicationContext(), mGameNumber, mGameType);

        mPuzzleFillGame = PuzzlesDB.getPuzzle(mGameNumber, mGameType, getApplicationContext());

        gameView = new GameView(getApplicationContext(), mPuzzleFillGame, gameCallBacks);

        ((FrameLayout) findViewById(R.id.rlForGame)).addView(gameView);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        nextGame = (ImageButton) findViewById(R.id.btnNextAGF);
        previousGame = (ImageButton) findViewById(R.id.btnPreviousAGF);
        gameText = (TextView) findViewById(R.id.tvWordAFG);
        ivResultImage = (ImageView) findViewById(R.id.ivResultImageAGF);
        findViewById(R.id.rlBackgroundAFG).setBackgroundColor(Color.TRANSPARENT);

        nextGame.setOnClickListener(mOnClickListener);
        previousGame.setOnClickListener(mOnClickListener);
        ivResultImage.setOnClickListener(mOnClickListener);
        ivResultImage.setClickable(false);

        nextGame.setClickable(true);
        previousGame.setClickable(true);

        Display display = getWindowManager().getDefaultDisplay();
        displaySize = new Point();
        display.getSize(displaySize);

        mIsPlaySound = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!AppHelper.isAppInBackground(getApplicationContext())) {
            AppHelper.getBackgroundSound().pause(false);

            if (mIsPlaySound && mPlayer != null)
                mPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (AppHelper.isAppInBackground(getApplicationContext()) || AppHelper.isScreenOff(getApplicationContext())) {
            AppHelper.getBackgroundSound().pause(true);

            if (mIsPlaySound && mPlayer != null)
                mPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null)
            mPlayer.release();

        if (gameView != null)
            gameView.release();

        if (ivResultImage != null && ivResultImage.getDrawable() != null && ((BitmapDrawable) ivResultImage.getDrawable()).getBitmap() != null) {
            ((BitmapDrawable) ivResultImage.getDrawable()).getBitmap().recycle();
            ivResultImage.setImageDrawable(null);
        }

        if(resImage != null)
            resImage.recycle();
    }

    private void switchGame(boolean nextGame) {
        int passedGame = AppHelper.getPassedGames();
        if (passedGame % 3 != 0) {
            Intent gameIntent = new Intent(this, GameFillActivity.class);
            gameIntent.putExtra("type", mGameType); if(nextGame)
                gameIntent.putExtra("gameNumber", AppHelper.getNextGame(getApplicationContext(), mGameType));
            else
                gameIntent.putExtra("gameNumber", AppHelper.getPreviousGame(getApplicationContext(), mGameType));
            startActivity(gameIntent);
        } else {
            Random r = new Random();
            int bonusLevelIndex = r.nextInt(4);

            Class bonusLevelActivity = null;
            switch (bonusLevelIndex) {
                case 0:
                    bonusLevelActivity = BonusLevelTreeActivity.class;
                    break;
                case 1:
                    bonusLevelActivity = BonusLevelShakeActivity.class;
                    break;
                case 2:
                    bonusLevelActivity = BonusLevelFlowerActivity.class;
                    break;
                case 3:
                    bonusLevelActivity = BonusLevelHedgehogActivity.class;
                break;
            }
            Intent gameIntent = new Intent(this, bonusLevelActivity);
            gameIntent.putExtra("type", mGameType);
            if(nextGame)
                gameIntent.putExtra("gameNumber", AppHelper.getNextGame(getApplicationContext(), mGameType));
            else
                gameIntent.putExtra("gameNumber", AppHelper.getPreviousGame(getApplicationContext(), mGameType));
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
        moveYAnimator.addListener(new AnimationEndListener(candy, mAnimEndListener));
        moveYAnimator.start();
    }

    private void showResultImageAnimation(Bitmap resultImage, int x, int y) {
        resImage = resultImage;
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

}
