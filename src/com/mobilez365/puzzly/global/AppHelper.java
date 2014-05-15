package com.mobilez365.puzzly.global;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.app.Activity;

import java.util.Locale;

/**
 * Created by Denis on 14.05.14.
 */
public class AppHelper {

    public static enum Languages {
        us,
        ru
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

    public static final void setGameAchievement(Activity _activity, int _count) {
        SharedPreferences.Editor edit = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE).edit();
        edit.putInt(Constans.GAME_ACHIEVEMENT, _count);
        edit.commit();
    }

    public static final int getGameAchievement(Activity _activity) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constans.PREFERENCES_NAME, _activity.MODE_PRIVATE);
        return prefs.getInt(Constans.GAME_ACHIEVEMENT, 0);
    }
}
