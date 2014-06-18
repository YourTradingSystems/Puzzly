package com.mobilez365.puzzly.util;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;

/**
 * Created by Denis on 20.05.14.
 */
public class BackgroundSound extends AsyncTask<Context, Void, Void> {

    private MediaPlayer mPlayer;
    private String mName;
    private boolean isPlay;
    private boolean isStop;

    public BackgroundSound(String _name) {
        mName = _name;
        mPlayer = new MediaPlayer();
    }

    public final void initSound(Context _context, String _fileName) {
        mName = _fileName;
        isPlay = true;
        isStop = false;
        int id = _context.getResources().getIdentifier(_fileName.toLowerCase(), "raw", _context.getPackageName());
        mPlayer = MediaPlayer.create(_context, id);
        mPlayer.setLooping(true);
        mPlayer.start();
    }

    public final void pause(boolean _state) {
        if (mPlayer != null) {
            if (_state && isPlay) {
                mPlayer.pause();
                isPlay = false;
            }
            else if (!_state && !isStop) {
                mPlayer.start();
                isPlay = true;
            }
        }
    }

    public final void stop() {
        if(isPlay) {
            mPlayer.stop();
            mPlayer.release();
        }
        isPlay = false;
        isStop = true;
    }

    public final MediaPlayer getPlayer() {
        return mPlayer;
    }

    public final boolean isPlay() {
        return isPlay;
    }

    public final String getName() {
        return mName;
    }

    @Override
    protected Void doInBackground(Context... params) {
        initSound(params[0], mName);
        isPlay = true;
        return null;
    }
}
