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
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.puzzles.PuzzleFillGame;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;
import com.mobilez365.puzzly.screens.GameFillActivity;

/**
 * Created by andrewtivodar on 28.05.2014.
 */
public class ChooseGamePagerAdapter extends PagerAdapter implements View.OnClickListener{

    private Context mContext;
    private int mGameCount;
    private int mPagesCount;
    private int mGameType;
    private int maxFigureHeight;
    private int maxFigureWidth;

    public ChooseGamePagerAdapter(Context _context, int _gameType) {
        mContext = _context;
        mGameType = _gameType;
        mGameCount = PuzzlesDB.getPuzzleGameCount(_context, _gameType);
        mPagesCount = (int) Math.ceil(mGameCount / 4f);

        Display display = ((Activity)_context).getWindowManager().getDefaultDisplay();
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
        ImageView firstGameFigure = (ImageView) view.findViewById(R.id.ivFirstFigureICP);
        RelativeLayout firstGameLayout = (RelativeLayout) view.findViewById(R.id.rlFirstFigureICP);
        firstGameLayout.setTag(levelPosition);
        firstGameLayout.setOnClickListener(this);
        loadLevelPictures(firstGameFigure, firstGameLayout, levelPosition);

        levelPosition = position * 4 + 1;
        ImageView secondGameFigure = (ImageView) view.findViewById(R.id.ivSecondFigureICP);
        RelativeLayout secondGameLayout = (RelativeLayout) view.findViewById(R.id.rlSecondFigureICP);
        secondGameLayout.setTag(levelPosition);
        secondGameLayout.setOnClickListener(this);
        loadLevelPictures(secondGameFigure, secondGameLayout, levelPosition);

        levelPosition = position * 4 + 2;
        ImageView thirdGameFigure = (ImageView) view.findViewById(R.id.ivThirdFigureICP);
        thirdGameFigure.setTag(levelPosition);
        thirdGameFigure.setOnClickListener(this);
        RelativeLayout thirdGameLayout = (RelativeLayout) view.findViewById(R.id.rlThirdFigureICP);
        loadLevelPictures(thirdGameFigure, thirdGameLayout, levelPosition);

        levelPosition = position * 4 + 3;
        ImageView fourthGameFigure = (ImageView) view.findViewById(R.id.ivFourthFigureICP);
        RelativeLayout fourthGameLayout = (RelativeLayout) view.findViewById(R.id.rlFourthFigureICP);
        fourthGameLayout.setTag(levelPosition);
        fourthGameLayout.setOnClickListener(this);
        loadLevelPictures(fourthGameFigure, fourthGameLayout, levelPosition);

        viewGroup.addView(view, 0);

        return view;
    }

    private void loadLevelPictures(final ImageView image, RelativeLayout rl, int position){
        int passedGameCount = AppHelper.getMaxGame((Activity) mContext, mGameType);
        if(position < mGameCount && position <= passedGameCount) {
            PuzzleFillGame game = PuzzlesDB.getPuzzle(position, mGameType, (Activity) mContext);
            String imageName;

            if (position < passedGameCount) {
                rl.setBackgroundResource(R.drawable.background_done_level);
                imageName = game.getResultImage();
            }
            else {
                rl.setBackgroundResource(R.drawable.background_new_level);
                imageName = game.getImage();
            }

            ParseSvgAsyncTask.ParseListener listener = new ParseSvgAsyncTask.ParseListener() {
                @Override
                public void onParseDone(Bitmap bitmap) {
                    image.setImageBitmap(bitmap);
                }
            };

            ParseSvgAsyncTask parseSvgAsyncTask = new ParseSvgAsyncTask(mContext, listener, maxFigureWidth, maxFigureHeight);
            parseSvgAsyncTask.execute(imageName);
        }
        else if(position < mGameCount && position > passedGameCount) {
            rl.setBackgroundResource(R.drawable.background_new_level);
            image.setImageResource(R.drawable.settings_icon);
        }

    }

    @Override
    public void onClick(View v) {
        int levelPosition = (Integer) v.getTag();
        Intent gameIntent = new Intent(mContext, GameFillActivity.class);
        gameIntent.putExtra("type", mGameType);
        gameIntent.putExtra("gameNumber", levelPosition);
        mContext.startActivity(gameIntent);
    }
}