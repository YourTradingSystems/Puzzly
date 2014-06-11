package com.mobilez365.puzzly.util;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;

/**
 * Created by Denis on 20.05.14.
 */
public class BackgroundSound extends AsyncTask<Void, Void, Void> {

    private MediaPlayer mPlayer;
    private Activity mActivity;
    private String mName;
    private boolean isPlay;
    private boolean isStop;

    public BackgroundSound(Activity _activity, String _name) {
        mActivity = _activity;
        mName = _name;
        mPlayer = new MediaPlayer();
    }

    @Override
    protected Void doInBackground(Void... params) {
        initSound(mActivity, mName);
        isPlay = true;
        return null;
    }

    public final void initSound(Activity _activity, String _fileName) {
        mName = _fileName;
        isPlay = true;
        isStop = false;
        int id = _activity.getResources().getIdentifier(_fileName.toLowerCase(), "raw", _activity.getPackageName());
        mPlayer = MediaPlayer.create(_activity, id);
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
        mPlayer.stop();
        mPlayer.release();
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
}
