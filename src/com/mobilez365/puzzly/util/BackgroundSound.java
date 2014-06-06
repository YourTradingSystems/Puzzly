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
    private boolean isCreate;

    public BackgroundSound(Activity _activity, String _name) {
        mActivity = _activity;
        mName = _name;
        mPlayer = new MediaPlayer();
    }

    @Override
    protected Void doInBackground(Void... params) {
        mPlayer = AppHelper.initSound(mActivity, mName);
        mPlayer.setLooping(true);
        mPlayer.setVolume(25, 25);
        mPlayer.start();

        isCreate = true;
        isPlay = true;
        return null;
    }

    public final void pause(boolean _state) {
        if (mPlayer != null) {
            if (_state) {
                mPlayer.pause();
                isPlay = false;
            }
            else {
                mPlayer.start();
                isPlay = true;
            }
        }
    }

    public final MediaPlayer getPlayer() {
        return mPlayer;
    }

    public final boolean isPlay() {
        return isPlay;
    }

    public final boolean isInit() {
        return isCreate;
    }
}
