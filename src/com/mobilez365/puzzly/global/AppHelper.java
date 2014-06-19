package com.mobilez365.puzzly.global;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PowerManager;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;

import com.mobilez365.puzzly.util.BackgroundSound;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

/**
 * Created by Denis on 14.05.14.
 */
public class AppHelper {

    private static BackgroundSound mBackgroundSound;
    private static MediaPlayer mItemSound;
    public static Integer appStatus;
    private static int passedGame = 0;

    public static enum Languages {
        en,
        uk,
        ru,
        hu,
        de,
        fr,
        es,
        zh,
        ar,
        //he,
        hi
    }

    public static void setDefaultFont(Context context) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                "default_font.ttf");
        try {
            final Field StaticField = Typeface.class
                    .getDeclaredField("MONOSPACE");
            StaticField.setAccessible(true);
            StaticField.set(null, regular);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static final void changeLanguage(Context _context, String _language) {
        try {
            Locale locale = new Locale(_language);
            Locale.setDefault(locale);
            Configuration config = _context.getResources().getConfiguration();
            config.locale = locale;
            _context.getResources().updateConfiguration(config, _context.getResources().getDisplayMetrics());
        } catch (Exception e) {
            Locale locale = new Locale(Languages.en.name());
            Locale.setDefault(locale);
            Configuration config = _context.getResources().getConfiguration();
            config.locale = locale;
            _context.getResources().updateConfiguration(config, _context.getResources().getDisplayMetrics());
        }
    }

    public static final Languages getLocaleLanguage(Context _contex, int type) {
        SharedPreferences prefs = _contex.getSharedPreferences(Constans.PREFERENCES_NAME, _contex.MODE_PRIVATE);
        int lang;
        if(type == Constans.APP_LANGUAGE)
            lang = prefs.getInt(Constans.LOCALIZE_APP_LANGUAGE, 0);
        else
            lang = prefs.getInt(Constans.LOCALIZE_STUDY_LANGUAGE, 0);

        switch (lang) {
            case 0:
                return Languages.en;

            case 1:
                return Languages.uk;

            case 2:
                return Languages.ru;

            case 3:
                return Languages.hu;

            case 4:
                return Languages.de;

            case 5:
                return Languages.fr;

            case 6:
                return Languages.es;

            case 7:
                return Languages.zh;

           // case 8:
            //    return Languages.ar;

            //case 9:
                //return Languages.he;

            case 8:
                return Languages.hi;
        }

        return Languages.en;
    }

    public static boolean isAppInBackground(final Context _context) {
        ActivityManager am = (ActivityManager) _context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(_context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isScreenOff(final Context _context) {
        PowerManager powerManager = (PowerManager) _context.getSystemService(_context.POWER_SERVICE);
        return !powerManager.isScreenOn();
    }

    public static final VideoView showVideoTutorial(Context _context, ViewGroup _parentView) {
        String uriPath = "android.resource://" + _context.getPackageName() + "/raw/" + R.raw.tutorial_1;
        final VideoView tutorial_video = new VideoView(_context);

        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(400, 300);
        relativeParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        _parentView.addView(tutorial_video, relativeParams);

        Uri uri = Uri.parse(uriPath);

        tutorial_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        tutorial_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

                tutorial_video.resume();
                tutorial_video.start();

            }
        });

        tutorial_video.setVideoURI(uri);
        tutorial_video.start();
        return tutorial_video;
    }

    public static final MediaPlayer playSound(Context _context, String _fileName) {
        int id = _context.getResources().getIdentifier(_fileName, "raw", _context.getPackageName());
        mItemSound = MediaPlayer.create(_context, id);
        mItemSound.start();
        return mItemSound;
    }

    public static final void startBackgroundSound(Context _context, String _name) {
        if (mBackgroundSound != null && mBackgroundSound.isPlay()) {
            if (mBackgroundSound.getPlayer() != null) {
                mBackgroundSound.getPlayer().stop();
                mBackgroundSound.getPlayer().release();
            }
        }

        if (mBackgroundSound != null)
            mBackgroundSound.initSound(_context, _name);
        else {
            mBackgroundSound = new BackgroundSound( _name);
            mBackgroundSound.execute(_context.getApplicationContext());
        }
    }

    public static final BackgroundSound getBackgroundSound() {
        if (mBackgroundSound == null)
            mBackgroundSound = new BackgroundSound("");

        return mBackgroundSound;
    }
    
    public static final void setGameAchievement(Context _context, int _count) {
        SharedPreferences.Editor edit = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        edit.putInt(Constans.GAME_ACHIEVEMENT, _count);
        edit.commit();
    }

    public static final int getGameAchievement(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getInt(Constans.GAME_ACHIEVEMENT, 0);
    }

    public static final int getNextGame(Context _context, int type) {
        int currentGame = getCurrentGame(_context, type);
        int gameCount = PuzzlesDB.getPuzzleGameCount(_context, type);
        int nextGame;
        if(gameCount > currentGame + 1)
            nextGame = currentGame + 1;
        else
            nextGame = -1;
        return nextGame;
    }

    public static final int getPreviousGame(Context _context, int type) {
        int currentGame = getCurrentGame(_context, type);
        int previousGame;
        if(currentGame > 0)
            previousGame = currentGame - 1;
        else
            previousGame = -1;
        return previousGame;
    }

    public static final void setCurrentGame(Context _context, int _gameNumber, int type) {
        SharedPreferences.Editor edit = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        if(type == 0)
            edit.putInt(Constans.CURRENT_GAME_FILL, _gameNumber);
        else
            edit.putInt(Constans.CURRENT_GAME_REVEAL, _gameNumber);
        edit.commit();
    }

    public static final int getMaxGame(Context _context, int type) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        if(type == 0)
            return prefs.getInt(Constans.MAX_GAME_FILL, 0);
        else
            return prefs.getInt(Constans.MAX_GAME_REVEAL, 0);
    }

    public static final void setMaxGame(Context _context, int _gameNumber, int type) {
        SharedPreferences.Editor edit = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();

        if(type == 0 && getMaxGame(_context, type) <= _gameNumber)
            edit.putInt(Constans.MAX_GAME_FILL, _gameNumber);
        else if(type == 1 && getMaxGame(_context, type) <= _gameNumber)
            edit.putInt(Constans.MAX_GAME_REVEAL, _gameNumber);
        edit.commit();
    }

    public static final int getCurrentGame(Context _context, int type) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        if(type == 0)
            return prefs.getInt(Constans.CURRENT_GAME_FILL, 0);
        else
            return prefs.getInt(Constans.CURRENT_GAME_REVEAL, 0);
    }

    public static final void increasePassedGames() {
        if(passedGame == 3)
            passedGame = 0;
        else
            passedGame++;
    }

    public static final void resetPassedGames() {
        passedGame = 0;
    }

    public static final int getPassedGames() {
        return passedGame;
    }

    public static final void setPuzzlesInit(Context _context, boolean _initialized) {
        SharedPreferences.Editor edit = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        edit.putBoolean(Constans.PUZZLES_INITIALIZED, _initialized);
        edit.commit();
    }

    public static final boolean getPuzzlesInit(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.PUZZLES_INITIALIZED, false);
    }

    public static final void setPlayBackgroundMusic(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.PLAY_BACKGROUND_MUSIC, _state);
        editor.commit();
    }

    public static final boolean getPlayBackgroundMusic(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.PLAY_BACKGROUND_MUSIC, true);
    }

    public static final void setShowImageBorder(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.DISPLAY_INNER_BORDERS, _state);
        editor.commit();
    }

    public static final boolean getShowImageBorder(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.DISPLAY_INNER_BORDERS, true);
    }

    public static final void setPlaySound(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.PLAY_SOUND, _state);
        editor.commit();
    }

    public static final boolean getPlaySound(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.PLAY_SOUND, true);
    }

    public static final void setVibrate(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.VIBRATE, _state);
        editor.commit();
    }

    public static final boolean getVibrate(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.VIBRATE, true);
    }

    public static final void setLocalizeAppLanguage(Context _context, int _language) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putInt(Constans.LOCALIZE_APP_LANGUAGE, _language);
        editor.commit();
    }

    public static final int getLocalizeAppLanguage(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getInt(Constans.LOCALIZE_APP_LANGUAGE, 0);
    }

    public static final void setLocalizeStudyLanguage(Context _context, int _language) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putInt(Constans.LOCALIZE_STUDY_LANGUAGE, _language);
        editor.commit();
    }

    public static final int getLocalizeStudyLanguage(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getInt(Constans.LOCALIZE_STUDY_LANGUAGE, 0);
    }

    public static final void setBonusFlower(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.BONUS_FLOWER, _state);
        editor.commit();
    }

    public static final boolean getBonusFlower(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.BONUS_FLOWER, false);
    }

    public static final void setBonusShake(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.BONUS_SHAKE, _state);
        editor.commit();
    }

    public static final boolean getBonusShake(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.BONUS_SHAKE, false);
    }

    public static final void setBonusTree(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.BONUS_TREE, _state);
        editor.commit();
    }

    public static final boolean getBonusTree(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.BONUS_TREE, false);
    }

    public static final void setLeftHandTutorial(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.MENU_LEFT_HAND_TUTORIAL, _state);
        editor.commit();
    }

    public static final boolean getLeftHandTutorial(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.MENU_LEFT_HAND_TUTORIAL, false);
    }

    public static final void setRightHandTutorial(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.MENU_RIGHT_HAND_TUTORIAL, _state);
        editor.commit();
    }

    public static final boolean getRightHandTutorial(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.MENU_RIGHT_HAND_TUTORIAL, false);
    }
}
