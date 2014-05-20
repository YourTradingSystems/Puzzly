package com.mobilez365.puzzly.global;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.media.MediaPlayer;

import com.mobilez365.puzzly.util.BackgroundSound;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Created by Denis on 14.05.14.
 */
public class AppHelper {

    private static BackgroundSound mBackgroundSound;

    public static enum Languages {
        us,
        ru
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
        Locale locale = new Locale(_language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        _activity.getResources().updateConfiguration(config, _activity.getResources().getDisplayMetrics());
    }

    public static final void changeLanguageRefresh(Activity _activity, String _language) {
        Locale locale = new Locale(_language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        _activity.getResources().updateConfiguration(config, _activity.getResources().getDisplayMetrics());
        _activity.startActivity(_activity.getIntent());
        _activity.finish();
    }

    public static final Languages getLocaleLanguage(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        int lang = prefs.getInt(Constans.LOCALIZE_LANGUAGE, 0);

        switch (lang) {
            case 0:
                return Languages.us;

            case 1:
                return Languages.ru;
        }

        return Languages.us;
    }

    public static final MediaPlayer initSound(Activity _activity, String _fileName) {
        int id = _activity.getResources().getIdentifier(_fileName.toLowerCase(), "raw", _activity.getPackageName());
        MediaPlayer mediaPayer = MediaPlayer.create(_activity, id);
        return mediaPayer;
    }

    public static final MediaPlayer playSound(Activity _activity, String _fileName) {
        int id = _activity.getResources().getIdentifier(_fileName.toLowerCase(), "raw", _activity.getPackageName());
        MediaPlayer mediaPayer = MediaPlayer.create(_activity, id);
        mediaPayer.start();
        return mediaPayer;
    }

    public static final void startBackgroundSound(Activity _activity) {
        mBackgroundSound = new BackgroundSound(_activity);
        if (!mBackgroundSound.isInit())
            mBackgroundSound.execute(null);
    }

    public static final BackgroundSound getBackgroundSound() {
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

    public static final void setCurrentGame(Activity _activity, int _gameNumber, int type) {
        SharedPreferences.Editor edit = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        if(type == 0)
            edit.putInt(Constans.CURRENT_GAME_FILL, _gameNumber);
        else
            edit.putInt(Constans.CURRENT_GAME_REVEAL, _gameNumber);
        edit.commit();
    }

    public static final int getCurrentGame(Activity _activity, int type) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        if(type == 0)
            return prefs.getInt(Constans.CURRENT_GAME_FILL, 0);
        else
            return prefs.getInt(Constans.CURRENT_GAME_REVEAL, 0);
    }

    public static final void increasePassedGames(Activity _activity) {
        SharedPreferences.Editor edit = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        int passedGame = getPassedGames(_activity);
        if(passedGame == 3)
            passedGame = 0;
        else
            passedGame++;
        edit.putInt(Constans.PASSED_GAME, passedGame);
        edit.commit();
    }

    public static final int getPassedGames(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getInt(Constans.PASSED_GAME, 0);
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

    public static final void setPlaySoundImageAppear(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.PLAY_SOUND_WHEN_IMAGE_APPEAR, _state);
        editor.commit();
    }

    public static final boolean getPlaySoundImageAppear(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.PLAY_SOUND_WHEN_IMAGE_APPEAR, true);
    }

    public static final void setDisplayWords(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.DISPLAY_WORDS, _state);
        editor.commit();
    }

    public static final boolean getDisplayWords(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.DISPLAY_WORDS, true);
    }

    public static final void setVoiceForDisplayWords(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.VOICE_FOR_DISPLAY_WORDS, _state);
        editor.commit();
    }

    public static final boolean getVoiceForDisplayWords(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.VOICE_FOR_DISPLAY_WORDS, true);
    }

    public static final void setVibrateDragPuzzles(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.VIBRATE_WHEN_DRAG_PUZZLES, _state);
        editor.commit();
    }

    public static final boolean getVibrateDragPuzzles(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.VIBRATE_WHEN_DRAG_PUZZLES, true);
    }

    public static final void setVibratePieceInPlace(Activity _activity, boolean _state) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.VIBRATE_WHEN_A_PIECE_IN_PLACE, _state);
        editor.commit();
    }

    public static final boolean getVibratePieceInPlace(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getBoolean(Constans.VIBRATE_WHEN_A_PIECE_IN_PLACE, true);
    }

    public static final void setLocalizeLanguage(Activity _activity, int _language) {
        SharedPreferences.Editor editor = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        editor.putInt(Constans.LOCALIZE_LANGUAGE, _language);
        editor.commit();
    }

    public static final int getLocalizeLanguage(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getInt(Constans.LOCALIZE_LANGUAGE, 0);
    }
}
