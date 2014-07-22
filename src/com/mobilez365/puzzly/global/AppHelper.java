package com.mobilez365.puzzly.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;
import com.mobilez365.puzzly.screens.*;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Denis on 14.05.14.
 */
public class AppHelper {

    public static void setDefaultFont(Context context) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                "Roboto-Regular.ttf");
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

    public static void changeLanguage(Context _context, String _language) {
        try {
            Locale locale = new Locale(_language);
            Locale.setDefault(locale);
            Configuration config = _context.getResources().getConfiguration();
            config.locale = locale;
            _context.getResources().updateConfiguration(config, _context.getResources().getDisplayMetrics());
        } catch (Exception e) {
            Locale locale = new Locale(Constans.Languages.en.name());
            Locale.setDefault(locale);
            Configuration config = _context.getResources().getConfiguration();
            config.locale = locale;
            _context.getResources().updateConfiguration(config, _context.getResources().getDisplayMetrics());
        }
    }

    public static Constans.Languages getLocaleLanguage(Context _context, int type) {
        String lang;
        if (type == Constans.APP_LANGUAGE)
            lang = getLocalizeAppLanguage(_context);
        else
            lang = getLocalizeStudyLanguage(_context);

        if (lang.equals("en"))
            return Constans.Languages.en;
        else if (lang.equals("uk"))
            return Constans.Languages.uk;
        else if (lang.equals("ru"))
            return Constans.Languages.ru;
        else if (lang.equals("hu"))
            return Constans.Languages.hu;
        else if (lang.equals("de"))
            return Constans.Languages.de;
        else if (lang.equals("fr"))
            return Constans.Languages.fr;
        else if (lang.equals("es"))
            return Constans.Languages.es;
        else if (lang.equals("zh"))
            return Constans.Languages.zh;
        else if (lang.equals("ar"))
            return Constans.Languages.ar;
        else if (lang.equals("hi"))
            return Constans.Languages.hi;

        return Constans.Languages.en;
    }

    public static VideoView showVideoTutorial(Context _context, ViewGroup _parentView) {
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

        tutorial_video.setVideoURI(uri);
        tutorial_video.start();
        return tutorial_video;
    }

    public static void setGameAchievement(Context _context, int _count) {
        SharedPreferences.Editor edit = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        edit.putInt(Constans.GAME_ACHIEVEMENT, _count);
        edit.commit();
    }

    public static int getGameAchievement(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getInt(Constans.GAME_ACHIEVEMENT, 0);
    }

    public static int getNextGame(Context _context, int type) {
        int currentGame = getCurrentGame(_context, type);
        int gameCount = PuzzlesDB.getPuzzleGameCount(_context, type);
        int nextGame;
        if (gameCount > currentGame + 1)
            nextGame = currentGame + 1;
        else
            nextGame = -1;
        return nextGame;
    }

    public static int getPreviousGame(Context _context, int type) {
        int currentGame = getCurrentGame(_context, type);
        int previousGame;
        if (currentGame > 0)
            previousGame = currentGame - 1;
        else
            previousGame = -1;
        return previousGame;
    }

    public static Class getNextGameClass(Context context) {
        PuzzlesApplication app = (PuzzlesApplication) context.getApplicationContext();
        boolean isNextGameBonus =  app.getStartedGamesCount() % 3 == 0;
        Class nextLevelActivity = null;
        if(isNextGameBonus) {
            Random r = new Random();
            int bonusLevelIndex = r.nextInt(5);

            switch (bonusLevelIndex) {
                case 0:
                    nextLevelActivity = BonusLevelTreeActivity.class;
                    break;
                case 1:
                    nextLevelActivity = BonusLevelShakeActivity.class;
                    break;
                case 2:
                    nextLevelActivity = BonusLevelFlowerActivity.class;
                    break;
                case 3:
                    nextLevelActivity = BonusLevelHedgehogActivity.class;
                    break;
                case 4:
                    nextLevelActivity = BonusLevelMoveActivity.class;
                    break;
            }
        }
        else nextLevelActivity = PuzzleGameActivity.class;
        return nextLevelActivity;
    }

    public static int getStartCount(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getInt(Constans.START_COUNT, 0);
    }

    public static void increaseStartCount(Context _context) {
        int startCount = getStartCount(_context) + 1;
        SharedPreferences.Editor edit = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        edit.putInt(Constans.START_COUNT, startCount);
        edit.commit();
    }

    public static void decreaseStartCount(Context _context) {
        int startCount = getStartCount(_context) - 3;
        SharedPreferences.Editor edit = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        edit.putInt(Constans.START_COUNT, startCount);
        edit.commit();
    }

    public static int getMaxGame(Context _context, int type) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);

        if (type == 0)
            return prefs.getInt(Constans.MAX_GAME_FILL_IN_LANG.getMaxGameField(getLocaleLanguage(_context, Constans.GAME_LANGUAGE)), 0);
        else
            return prefs.getInt(Constans.MAX_GAME_REVEAL_IN_LANG.getMaxGameField(getLocaleLanguage(_context, Constans.GAME_LANGUAGE)), 0);
    }

    public static void setMaxGame(Context _context, int _gameNumber, int type) {
        SharedPreferences.Editor edit = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();

        if (type == 0 && getMaxGame(_context, type) <= _gameNumber)
            edit.putInt(Constans.MAX_GAME_FILL_IN_LANG.getMaxGameField(getLocaleLanguage(_context, Constans.GAME_LANGUAGE)), _gameNumber);
        else if (type == 1 && getMaxGame(_context, type) <= _gameNumber)
            edit.putInt(Constans.MAX_GAME_REVEAL_IN_LANG.getMaxGameField(getLocaleLanguage(_context, Constans.GAME_LANGUAGE)), _gameNumber);
        edit.commit();
    }

    public static void checkMaxCountFromPreviousVersion(Context _context, int type){
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);

        int previousMaxCount;
        if (type == 0)
            previousMaxCount = prefs.getInt(Constans.MAX_GAME_FILL, 0);
        else
            previousMaxCount = prefs.getInt(Constans.MAX_GAME_REVEAL, 0);

        if(previousMaxCount != 0) {
            setMaxGame(_context, previousMaxCount, type);
            SharedPreferences.Editor edit = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
            if (type == 0)
                edit.putInt(Constans.MAX_GAME_FILL, 0);
            else
                edit.putInt(Constans.MAX_GAME_REVEAL, 0);
            edit.commit();
        }

    }

    public static int getCurrentGame(Context _context, int type) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        if (type == 0)
            return prefs.getInt(Constans.CURRENT_GAME_FILL_IN_LANG.getCurrentGameField(getLocaleLanguage(_context, Constans.GAME_LANGUAGE)), 0);
        else
            return prefs.getInt(Constans.CURRENT_GAME_REVEAL_IN_LANG.getCurrentGameField(getLocaleLanguage(_context, Constans.GAME_LANGUAGE)), 0);
    }

    public static void setCurrentGame(Context _context, int _gameNumber, int type) {
        SharedPreferences.Editor edit = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        if (type == 0)
            edit.putInt(Constans.CURRENT_GAME_FILL_IN_LANG.getCurrentGameField(getLocaleLanguage(_context, Constans.GAME_LANGUAGE)), _gameNumber);
        else
            edit.putInt(Constans.CURRENT_GAME_REVEAL_IN_LANG.getCurrentGameField(getLocaleLanguage(_context, Constans.GAME_LANGUAGE)), _gameNumber);
        edit.commit();
    }

    public static void checkCurrentCountFromPreviousVersion(Context _context, int type){
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);

        int previousNumber;
        if (type == 0)
            previousNumber = prefs.getInt(Constans.CURRENT_GAME_FILL, 0);
        else
            previousNumber = prefs.getInt(Constans.CURRENT_GAME_REVEAL, 0);

        if(previousNumber != 0) {
            setCurrentGame(_context, previousNumber, type);
            SharedPreferences.Editor edit = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
            if (type == 0)
                edit.putInt(Constans.CURRENT_GAME_FILL, 0);
            else
                edit.putInt(Constans.CURRENT_GAME_REVEAL, 0);
            edit.commit();
        }

    }

    public static void setPuzzlesInit(Context _context, boolean _initialized) {
        SharedPreferences.Editor edit = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        edit.putBoolean(Constans.PUZZLES_INITIALIZED, _initialized);
        edit.commit();
    }

    public static boolean getPuzzlesInit(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.PUZZLES_INITIALIZED, false);
    }

    public static void setPlayBackgroundMusic(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.PLAY_BACKGROUND_MUSIC, _state);
        editor.commit();
    }

    public static boolean getPlayBackgroundMusic(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.PLAY_BACKGROUND_MUSIC, true);
    }

    public static void setShowImageBorder(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.DISPLAY_INNER_BORDERS, _state);
        editor.commit();
    }

    public static boolean getShowImageBorder(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.DISPLAY_INNER_BORDERS, true);
    }

    public static void setPlaySound(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.PLAY_SOUND, _state);
        editor.commit();
    }

    public static boolean getPlaySound(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.PLAY_SOUND, true);
    }

    public static void setVibrate(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.VIBRATE, _state);
        editor.commit();
    }

    public static boolean getVibrate(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.VIBRATE, true);
    }

    public static void setAnalytics(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.GOOGLE_ANALYTICS, _state);
        editor.commit();
    }

    public static boolean getAnalytics(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.GOOGLE_ANALYTICS, true);
    }

    public static void setLocalizeAppLanguage(Context _context, String _language) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(Constans.LOCALIZE_APP_LANGUAGE, _language);
        editor.commit();
    }

    public static String getLanguageFromInt(int lang_code){
        switch (lang_code) {
            case 0:
                return "en";
            case 1:
                return "uk";
            case 2:
                return "ru";
            case 3:
                return "hu";
            case 4:
                return "de";
            case 5:
                return "fr";
            case 6:
                return "es";
            case 7:
                return "zh";
            case 8:
                return "ar";
            case 9:
                return "hi";
        }
        return "en";
    }

    public static String getLocalizeAppLanguage(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        String lang = null;
        try {
            lang = prefs.getString(Constans.LOCALIZE_APP_LANGUAGE, null);
        } catch (Exception exception) {
           int lang_code = prefs.getInt(Constans.LOCALIZE_APP_LANGUAGE, -1);
            lang = getLanguageFromInt(lang_code);
        } finally {
            if (lang == null) {
                String value = Locale.getDefault().getLanguage();
                if (Constans.Languages.contains(value)) lang = value;
                else lang = "en";
                setLocalizeAppLanguage(_context, lang);
            }
        }
        return lang;
    }

    public static void setLocalizeStudyLanguage(Context _context, String _language) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(Constans.LOCALIZE_STUDY_LANGUAGE, _language);
        editor.commit();
    }

    public static String getLocalizeStudyLanguage(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        String lang = null;
        try {
            lang = prefs.getString(Constans.LOCALIZE_STUDY_LANGUAGE, null);
        } catch (Exception exception) {
           int lang_code = prefs.getInt(Constans.LOCALIZE_STUDY_LANGUAGE, -1);
            lang = getLanguageFromInt(lang_code);
        } finally {
            if (lang == null) {
                String value = Locale.getDefault().getLanguage();
                if (Constans.Languages.contains(value)) lang = value;
                else lang = "en";
                setLocalizeStudyLanguage(_context, lang);
            }
        }
        return lang;
    }

    public static void setBonusFlower(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.BONUS_FLOWER, _state);
        editor.commit();
    }

    public static boolean getBonusFlower(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.BONUS_FLOWER, false);
    }

    public static void setBonusShake(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.BONUS_SHAKE, _state);
        editor.commit();
    }

    public static boolean getBonusShake(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.BONUS_SHAKE, false);
    }

    public static void setBonusTree(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.BONUS_TREE, _state);
        editor.commit();
    }

    public static boolean getBonusTree(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.BONUS_TREE, false);
    }

    public static void setLeftHandTutorial(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.MENU_LEFT_HAND_TUTORIAL, _state);
        editor.commit();
    }

    public static boolean getLeftHandTutorial(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.MENU_LEFT_HAND_TUTORIAL, false);
    }

    public static void setRightHandTutorial(Context _context, boolean _state) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE).edit();
        editor.putBoolean(Constans.MENU_RIGHT_HAND_TUTORIAL, _state);
        editor.commit();
    }

    public static boolean getRightHandTutorial(Context _context) {
        SharedPreferences prefs = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return prefs.getBoolean(Constans.MENU_RIGHT_HAND_TUTORIAL, false);
    }

    public static boolean isAdsDisabled(Context _context) {
        SharedPreferences settings = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        return settings.getBoolean(Constans.TAG_DISABLED_ADS, false);
    }

    public static void savePurchase(Context _context, Constans.Purchases p, boolean v) {
        SharedPreferences settings = _context.getSharedPreferences(Constans.PREFERENCES_NAME, _context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        switch (p) {
            case DISABLE_ADS:
                editor.putBoolean(Constans.TAG_DISABLED_ADS, v);
                break;
        }
        editor.commit();
    }
}
