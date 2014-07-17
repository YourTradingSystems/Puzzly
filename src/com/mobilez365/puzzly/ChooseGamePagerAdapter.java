package com.mobilez365.puzzly;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.puzzles.PuzzleGame;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;
import com.mobilez365.puzzly.screens.PuzzleGameActivity;

/**
 * Created by andrewtivodar on 28.05.2014.
 */
public class ChooseGamePagerAdapter extends PagerAdapter {
    private int mGameCount;
    private int mPagesCount;
    private int mGameType;
    public boolean clickEnable;
    private  boolean rldr;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (clickEnable) {
                Context appContext = v.getContext().getApplicationContext();
                int levelPosition = (Integer) v.getTag();
                if (levelPosition <= AppHelper.getMaxGame(appContext, mGameType)) {
                    Intent gameIntent = new Intent(appContext, PuzzleGameActivity.class);
                    gameIntent.putExtra("type", mGameType);
                    gameIntent.putExtra("gameNumber", levelPosition);
                    gameIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    appContext.startActivity(gameIntent);
                    clickEnable = false;
                }
            }
        }
    };

    public ChooseGamePagerAdapter(Context _context, int _gameType, Point size) {
        mGameType = _gameType;
        mGameCount = PuzzlesDB.getPuzzleGameCount(_context, _gameType);
        mPagesCount = (int) Math.ceil(mGameCount / 4f);
        //Reverse for Arabic
        rldr = AppHelper.getLocalizeStudyLanguage(_context).equals("ar") ? true : false;
    }

    @Override
    public int getCount() {
        return mPagesCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Context appContext = viewGroup.getContext().getApplicationContext();
        View view;
        view = inflater.inflate(R.layout.item_choose_puzzle, null);

        int levelPosition = rldr ?  mPagesCount * 4 - 3 - position * 4 : position * 4;
        if (levelPosition < mGameCount) {
            PuzzleGame game = PuzzlesDB.getPuzzle(levelPosition, mGameType, appContext);

            TextView gameWord = (TextView) view.findViewById(R.id.tvFirstWordICP);
            ImageView gameFigure = (ImageView) view.findViewById(R.id.ivFirstFigureICP);
            RelativeLayout gameLayout = (RelativeLayout) view.findViewById(R.id.rlFirstFigureICP);

            gameLayout.setTag(levelPosition);
            gameLayout.setOnClickListener(mOnClickListener);

            loadLevelPictures(appContext, game, gameFigure, gameLayout, levelPosition);
            loadGameWord(appContext, game, gameWord, levelPosition);
        }

        levelPosition = rldr ? mPagesCount * 4 - 4 - position * 4 : position * 4 + 1;
        if (levelPosition < mGameCount) {
            PuzzleGame game = PuzzlesDB.getPuzzle(levelPosition, mGameType, appContext);

            TextView gameWord = (TextView) view.findViewById(R.id.tvSecondWordICP);
            ImageView gameFigure = (ImageView) view.findViewById(R.id.ivSecondFigureICP);
            RelativeLayout gameLayout = (RelativeLayout) view.findViewById(R.id.rlSecondFigureICP);

            gameLayout.setTag(levelPosition);
            gameLayout.setOnClickListener(mOnClickListener);

            loadLevelPictures(appContext, game, gameFigure, gameLayout, levelPosition);
            loadGameWord(appContext, game, gameWord, levelPosition);
        }

        levelPosition = rldr ?  mPagesCount * 4 - 1 -  position * 4 : position * 4 + 2;
        if (levelPosition < mGameCount) {
            PuzzleGame game = PuzzlesDB.getPuzzle(levelPosition, mGameType, appContext);

            TextView gameWord = (TextView) view.findViewById(R.id.tvThirdWordICP);
            ImageView gameFigure = (ImageView) view.findViewById(R.id.ivThirdFigureICP);
            RelativeLayout gameLayout = (RelativeLayout) view.findViewById(R.id.rlThirdFigureICP);

            gameLayout.setTag(levelPosition);
            gameLayout.setOnClickListener(mOnClickListener);

            loadLevelPictures(appContext, game, gameFigure, gameLayout, levelPosition);
            loadGameWord(appContext, game, gameWord, levelPosition);
        }

        levelPosition = rldr ?  mPagesCount * 4 - 2 - position * 4 : position * 4 + 3;
        if (levelPosition < mGameCount) {
            PuzzleGame game = PuzzlesDB.getPuzzle(levelPosition, mGameType, appContext);

            TextView gameWord = (TextView) view.findViewById(R.id.tvFourthWordICP);
            ImageView gameFigure = (ImageView) view.findViewById(R.id.ivFourthFigureICP);
            RelativeLayout gameLayout = (RelativeLayout) view.findViewById(R.id.rlFourthFigureICP);

            gameLayout.setTag(levelPosition);
            gameLayout.setOnClickListener(mOnClickListener);

            loadLevelPictures(appContext, game, gameFigure, gameLayout, levelPosition);
            loadGameWord(appContext, game, gameWord, levelPosition);
        }

        viewGroup.addView(view, 0);

        return view;
    }

    private void loadLevelPictures(Context context, PuzzleGame game, final ImageView image, RelativeLayout rl, int position) {
        int passedGameCount = AppHelper.getMaxGame(context, mGameType);
        if (position < passedGameCount) {
            String imageName = game.getResultImage();
            rl.setBackgroundResource(R.drawable.background_done_level);

            ParseSvgAsyncTask.ParseListener listener = new ParseSvgAsyncTask.ParseListener() {
                @Override
                public void onParseDone(Drawable drawable) {
                    image.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    image.setImageDrawable(drawable);
                }
            };

            ParseSvgAsyncTask parseSvgAsyncTask = new ParseSvgAsyncTask(context, listener);
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

    private void loadGameWord(Context context, PuzzleGame game, TextView tv, int position) {
        int passedGameCount = AppHelper.getMaxGame(context, mGameType);
        if(position < passedGameCount) {
            tv.setText(game.getWord(context));
        }
        else if(position == passedGameCount)
            tv.setText(context.getString(R.string.choose_level_new_level));
        else
            tv.setText(context.getString(R.string.choose_level_locked_level));
    }

}