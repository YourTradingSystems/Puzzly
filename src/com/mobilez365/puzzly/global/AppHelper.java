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
    public static Integer adware = 2;
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

    public static final void changeLanguage(Activity _activity, String _language) {
        try {
            Locale locale = new Locale(_language);
            Locale.setDefault(locale);
            Configuration config = _activity.getBaseContext().getResources().getConfiguration();
            config.locale = locale;
            _activity.getBaseContext().getResources().updateConfiguration(config, _activity.getBaseContext().getResources().getDisplayMetrics());
            locale = null;
            config = null;
        } catch (Exception e) {
            Locale locale = new Locale(Languages.en.name());
            Locale.setDefault(locale);
            Configuration config = _activity.getBaseContext().getResources().getConfiguration();
            config.locale = locale;
            _activity.getBaseContext().getResources().updateConfiguration(config, _activity.getBaseContext().getResources().getDisplayMetrics());
            locale = null;
            config = null;
        }
    }

    public static final void changeLanguageRefresh(Activity _activity, String _language, int scrollPos) {
        changeLanguage(_activity, _language);
        _activity.finish();
        Intent mIntent = new Intent(_activity, _activity.getClass());
        mIntent.putExtra("scrollPos" , scrollPos);
        _activity.startActivity(mIntent);
        _activity.overridePendingTransition(0, 0);
    }

    public static final Languages getLocaleLanguage(Activity _activity, int type) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
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

            case 8:
                return Languages.ar;

            //case 9:
                //return Languages.he;

            case 9:
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

    public static boolean isScreenOff(final Activity _activity) {
        PowerManager powerManager = (PowerManager) _activity.getSystemService(_activity.POWER_SERVICE);
        return !powerManager.isScreenOn();
    }

    public static final VideoView showVideoTutorial(Activity _activity, ViewGroup _parentView) {
        String uriPath = "android.resource://" + _activity.getPackageName() + "/raw/" + R.raw.tutorial_1;
        final VideoView tutorial_video = new VideoView(_activity);

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

    public static final MediaPlayer playSound(Activity _activity, String _fileName) {
        int id = _activity.getResources().getIdentifier(_fileName, "raw", _activity.getPackageName());
        mItemSound = MediaPlayer.create(_activity, id);
        mItemSound.start();
        return mItemSound;
    }

    public static final void startBackgroundSound(Activity _activity, String _name) {
        if (mBackgroundSound != null && mBackgroundSound.isPlay()) {
            if (mBackgroundSound.getPlayer() != null) {
                mBackgroundSound.getPlayer().stop();
                mBackgroundSound.getPlayer().release();
            }
        }

        if (mBackgroundSound != null)
            mBackgroundSound.initSound(_activity, _name);
        else {
            mBackgroundSound = new BackgroundSound(_activity, _name);
            mBackgroundSound.execute();
        }
    }

    public static final BackgroundSound getBackgroundSound() {
        if (mBackgroundSound == null)
            mBackgroundSound = new BackgroundSound(null, "");

        return mBackgroundSound;
    }
    
    public static final void setGameAchievement(Activity _activity, int _count) {
        SharedPreferences.Editor edit = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        edit.putInt(Constans.GAME_ACHIEVEMENT, _count);
        edit.commit();
    }

    public static final int getGameAchievement(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getInt(Constans.GAME_ACHIEVEMENT, 0);
    }

    public static final int getNextGame(Activity _activity, int type) {
        int currentGame = getCurrentGame(_activity, type);
        int gameCount = PuzzlesDB.getPuzzleGameCount(_activity, type);
        int nextGame;
        if(gameCount > currentGame + 1)
            nextGame = currentGame + 1;
        else
            nextGame = -1;
        return nextGame;
    }

    public static final int getPreviousGame(Activity _activity, int type) {
        int currentGame = getCurrentGame(_activity, type);
        int previousGame;
        if(currentGame > 0)
            previousGame = currentGame - 1;
        else
            previousGame = -1;
        return previousGame;
    }

    public static final void setCurrentGame(Activity _activity, int _gameNumber, int type) {
        SharedPreferences.Editor edit = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        if(type == 0)
            edit.putInt(Constans.CURRENT_GAME_FILL, _gameNumber);
        else
            edit.putInt(Constans.CURRENT_GAME_REVEAL, _gameNumber);
        edit.commit();
    }

    public static final int getMaxGame(Activity _activity, int type) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        if(type == 0)
            return prefs.getInt(Constans.MAX_GAME_FILL, 0);
        else
            return prefs.getInt(Constans.MAX_GAME_REVEAL, 0);
    }

    public static final void setMaxGame(Activity _activity, int _gameNumber, int type) {
        SharedPreferences.Editor edit = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();

        if(type == 0 && getMaxGame(_activity, type) <= _gameNumber)
            edit.putInt(Constans.MAX_GAME_FILL, _gameNumber);
        else if(type == 1 && getMaxGame(_activity, type) <= _gameNumber)
            edit.putInt(Constans.MAX_GAME_REVEAL, _gameNumber);
        edit.commit();
    }

    public static final int getCurrentGame(Activity _activity, int type) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
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

    public static final void setPuzzlesInit(Activity _activity, boolean _initialized) {
        SharedPreferences.Editor edit = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        edit.putBoolean(Constans.PUZZLES_INITIALIZED, _initialized);
        edit.commit();
    }

    public static final boolean getPuzzlesInit(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.PUZZLES_INITIALIZED, false);
    }

    public static final void setPlayBackgroundMusic(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.PLAY_BACKGROUND_MUSIC, _state);
        editor.commit();
    }

    public static final boolean getPlayBackgroundMusic(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.PLAY_BACKGROUND_MUSIC, true);
    }

    public static final void setShowImageBorder(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.DISPLAY_INNER_BORDERS, _state);
        editor.commit();
    }

    public static final boolean getShowImageBorder(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.DISPLAY_INNER_BORDERS, true);
    }

    public static final void setPlaySound(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.PLAY_SOUND, _state);
        editor.commit();
    }

    public static final boolean getPlaySound(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.PLAY_SOUND, true);
    }

    public static final void setVibrate(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.VIBRATE, _state);
        editor.commit();
    }

    public static final boolean getVibrate(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.VIBRATE, true);
    }

    public static final void setLocalizeAppLanguage(Activity _activity, int _language) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putInt(Constans.LOCALIZE_APP_LANGUAGE, _language);
        editor.commit();
    }

    public static final int getLocalizeAppLanguage(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getInt(Constans.LOCALIZE_APP_LANGUAGE, 0);
    }

    public static final void setLocalizeStudyLanguage(Activity _activity, int _language) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putInt(Constans.LOCALIZE_STUDY_LANGUAGE, _language);
        editor.commit();
    }

    public static final int getLocalizeStudyLanguage(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getInt(Constans.LOCALIZE_STUDY_LANGUAGE, 0);
    }

    public static final void setBonusFlower(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.BONUS_FLOWER, _state);
        editor.commit();
    }

    public static final boolean getBonusFlower(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.BONUS_FLOWER, false);
    }

    public static final void setBonusShake(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.BONUS_SHAKE, _state);
        editor.commit();
    }

    public static final boolean getBonusShake(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.BONUS_SHAKE, false);
    }

    public static final void setBonusTree(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.BONUS_TREE, _state);
        editor.commit();
    }

    public static final boolean getBonusTree(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.BONUS_TREE, false);
    }

    public static final void setLeftHandTutorial(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.MENU_LEFT_HAND_TUTORIAL, _state);
        editor.commit();
    }

    public static final boolean getLeftHandTutorial(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.MENU_LEFT_HAND_TUTORIAL, false);
    }

    public static final void setRightHandTutorial(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.MENU_RIGHT_HAND_TUTORIAL, _state);
        editor.commit();
    }

    public static final boolean getRightHandTutorial(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.MENU_RIGHT_HAND_TUTORIAL, false);
    }
}
