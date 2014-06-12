package com.mobilez365.puzzly.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.puzzles.PuzzleFillGame;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;
import com.mobilez365.puzzly.screens.GameFillActivity;

/**
 * Created by andrewtivodar on 28.05.2014.
 */
public class ChooseGamePagerAdapter extends PagerAdapter implements View.OnClickListener {

    private Activity mActivity;
    private int mGameCount;
    private int mPagesCount;
    private int mGameType;
    private int maxFigureHeight;
    private int maxFigureWidth;
    public boolean clickEnable;

    public ChooseGamePagerAdapter(Activity _activity, int _gameType) {
        mActivity = _activity;
        mGameType = _gameType;
        mGameCount = PuzzlesDB.getPuzzleGameCount(_activity, _gameType);
        mPagesCount = (int) Math.ceil(mGameCount / 4f);

        Display display = ((Activity) _activity).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        maxFigureWidth = (int) (size.x * 0.3f);
        maxFigureHeight = (int) (size.y * 0.3f);
    }

    @Override
    public int getCount() {
        return mPagesCount;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;
        view = inflater.inflate(R.layout.item_choose_puzzle, null);

        int levelPosition = position * 4;
        if (levelPosition < mGameCount) {
            PuzzleFillGame game = PuzzlesDB.getPuzzle(levelPosition, mGameType, (Activity) mActivity);

            TextView gameWord = (TextView) view.findViewById(R.id.tvFirstWordICP);
            ImageView gameFigure = (ImageView) view.findViewById(R.id.ivFirstFigureICP);
            RelativeLayout gameLayout = (RelativeLayout) view.findViewById(R.id.rlFirstFigureICP);

            gameLayout.setTag(levelPosition);
            gameLayout.setOnClickListener(this);

            loadLevelPictures(game, gameFigure, gameLayout, levelPosition);
            loadGameWord(game, gameWord, levelPosition);
        }

        levelPosition = position * 4 + 1;
        if (levelPosition < mGameCount) {
            PuzzleFillGame game = PuzzlesDB.getPuzzle(levelPosition, mGameType, (Activity) mActivity);

            TextView gameWord = (TextView) view.findViewById(R.id.tvSecondWordICP);
            ImageView gameFigure = (ImageView) view.findViewById(R.id.ivSecondFigureICP);
            RelativeLayout gameLayout = (RelativeLayout) view.findViewById(R.id.rlSecondFigureICP);

            gameLayout.setTag(levelPosition);
            gameLayout.setOnClickListener(this);

            loadLevelPictures(game, gameFigure, gameLayout, levelPosition);
            loadGameWord(game, gameWord, levelPosition);
        }

        levelPosition = position * 4 + 2;
        if (levelPosition < mGameCount) {
            PuzzleFillGame game = PuzzlesDB.getPuzzle(levelPosition, mGameType, (Activity) mActivity);

            TextView gameWord = (TextView) view.findViewById(R.id.tvThirdWordICP);
            ImageView gameFigure = (ImageView) view.findViewById(R.id.ivThirdFigureICP);
            RelativeLayout gameLayout = (RelativeLayout) view.findViewById(R.id.rlThirdFigureICP);

            gameLayout.setTag(levelPosition);
            gameLayout.setOnClickListener(this);

            loadLevelPictures(game, gameFigure, gameLayout, levelPosition);
            loadGameWord(game, gameWord, levelPosition);
        }

        levelPosition = position * 4 + 3;
        if (levelPosition < mGameCount) {
            PuzzleFillGame game = PuzzlesDB.getPuzzle(levelPosition, mGameType, (Activity) mActivity);

            TextView gameWord = (TextView) view.findViewById(R.id.tvFourthWordICP);
            ImageView gameFigure = (ImageView) view.findViewById(R.id.ivFourthFigureICP);
            RelativeLayout gameLayout = (RelativeLayout) view.findViewById(R.id.rlFourthFigureICP);

            gameLayout.setTag(levelPosition);
            gameLayout.setOnClickListener(this);

            loadLevelPictures(game, gameFigure, gameLayout, levelPosition);
            loadGameWord(game, gameWord, levelPosition);
        }

        viewGroup.addView(view, 0);

        return view;
    }

    private void loadLevelPictures(PuzzleFillGame game, final ImageView image, RelativeLayout rl, int position) {
        int passedGameCount = AppHelper.getMaxGame(mActivity, mGameType);
        if (position < passedGameCount) {
            String imageName = game.getResultImage();
            rl.setBackgroundResource(R.drawable.background_done_level);

            ParseSvgAsyncTask.ParseListener listener = new ParseSvgAsyncTask.ParseListener() {
                @Override
                public void onParseDone(Bitmap bitmap) {
                    image.setImageBitmap(bitmap);
                }
            };

            ParseSvgAsyncTask parseSvgAsyncTask = new ParseSvgAsyncTask(mActivity, listener, maxFigureWidth, maxFigureHeight);
            parseSvgAsyncTask.execute(imageName);
        } else if(position == passedGameCount){
            rl.setBackgroundResource(R.drawable.background_new_level);
            image.setImageResource(R.drawable.img_question_mark);
        }
        else {
            rl.setBackgroundResource(R.drawable.background_new_level);
            image.setImageResource(R.drawable.img_locked);
        }
    }

    private void loadGameWord(PuzzleFillGame game, TextView tv, int position) {
        AppHelper.changeLanguage(mActivity, AppHelper.getLocaleLanguage(mActivity, Constans.GAME_LANGUAGE).name());
        int passedGameCount = AppHelper.getMaxGame(mActivity, mGameType);
        if(position < passedGameCount) {
            tv.setText(game.getWord(mActivity));
        }
        else if(position == passedGameCount)
            tv.setText(mActivity.getString(R.string.choose_level_new_level));
        else
            tv.setText(mActivity.getString(R.string.choose_level_locked_level));
    }

    @Override
    public void onClick(View v) {
        if (clickEnable) {
            int levelPosition = (Integer) v.getTag();
            if (levelPosition <= AppHelper.getMaxGame(mActivity, mGameType)) {
                Intent gameIntent = new Intent(mActivity, GameFillActivity.class);
                gameIntent.putExtra("type", mGameType);
                gameIntent.putExtra("gameNumber", levelPosition);
                mActivity.startActivity(gameIntent);
                clickEnable = false;
            }
        }
    }
}