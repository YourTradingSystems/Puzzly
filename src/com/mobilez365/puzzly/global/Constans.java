package com.mobilez365.puzzly.global;

/**
 * Created by Denis on 12.05.14.
 */
public class Constans {


    public static final int SHAKE_THRESHOLD = 600;
    public static final String GAME_NUMBER_EXTRA = "game_number";
    public static final String REVIEW_URL = "https://play.google.com/store/apps/details?id=com.mobilez365.puzzly";
    /**
     * Settings name
     */
    public static final String PREFERENCES_NAME = "my_prefs";                                   //Shared preferences:
    public static final String PLAY_BACKGROUND_MUSIC = "play_background_music";                 //bool
    public static final String PLAY_SOUND = "play_sound";                                       //bool
    public static final String DISPLAY_INNER_BORDERS = "display_inner_borders";                 //bool
    public static final String VIBRATE = "vibrate";                                             //bool
    public static final String LOCALIZE_APP_LANGUAGE = "localize_app_language";                 //int
    public static final String LOCALIZE_STUDY_LANGUAGE = "localize_study_language";             //int
    public static final String GAME_ACHIEVEMENT = "game_achievement";                           //int
    public static final String CURRENT_GAME_FILL = "current_game_fill";                         //int
    public static final String CURRENT_GAME_REVEAL = "current_game_reveal";                      //int
    public static enum MAX_GAME_FILL_IN_LANG {
        max_game_fill_en,
        max_game_fill_uk,
        max_game_fill_ru,
        max_game_fill_hu,
        max_game_fill_de,
        max_game_fill_fr,
        max_game_fill_es,
        max_game_fill_zh,
        max_game_fill_ar,
        max_game_fill_hi;

        public static String getMaxGameField(AppHelper.Languages s) {
            String language = s.toString();
            if(language.equals("en"))
                return max_game_fill_en.toString();
            else if(language.equals("uk"))
                return  max_game_fill_uk.toString();
            else if(language.equals("ru"))
                return max_game_fill_ru.toString();
            else if(language.equals("hu"))
                return max_game_fill_hu.toString();
            else if(language.equals("de"))
                return max_game_fill_de.toString();
            else if(language.equals("fr"))
                return max_game_fill_fr.toString();
            else if(language.equals("es"))
                return max_game_fill_es.toString();
            else if(language.equals("zh"))
                return max_game_fill_zh.toString();
            else if(language.equals("ar"))
                return max_game_fill_ar.toString();
            else if(language.equals("hi"))
                return max_game_fill_hi.toString();

            return max_game_fill_en.toString();
        }
    }
    public static enum MAX_GAME_REVEAL_IN_LANG {
        max_game_reveal_en,
        max_game_reveal_uk,
        max_game_reveal_ru,
        max_game_reveal_hu,
        max_game_reveal_de,
        max_game_reveal_fr,
        max_game_reveal_es,
        max_game_reveal_zh,
        max_game_reveal_ar,
        max_game_reveal_hi;
        public static String getMaxGameField(AppHelper.Languages s) {
            String language = s.toString();
            if(language.equals("en"))
                return max_game_reveal_en.toString();
            else if(language.equals("uk"))
                return  max_game_reveal_uk.toString();
            else if(language.equals("ru"))
                return max_game_reveal_ru.toString();
            else if(language.equals("hu"))
                return max_game_reveal_hu.toString();
            else if(language.equals("de"))
                return max_game_reveal_de.toString();
            else if(language.equals("fr"))
                return max_game_reveal_fr.toString();
            else if(language.equals("es"))
                return max_game_reveal_es.toString();
            else if(language.equals("zh"))
                return max_game_reveal_zh.toString();
            else if(language.equals("ar"))
                return max_game_reveal_ar.toString();
            else if(language.equals("hi"))
                return max_game_reveal_hi.toString();

            return max_game_reveal_en.toString();
        }
    }
    public static enum CURRENT_GAME_FILL_IN_LANG {
        current_game_fill_en,
        current_game_fill_uk,
        current_game_fill_ru,
        current_game_fill_hu,
        current_game_fill_de,
        current_game_fill_fr,
        current_game_fill_es,
        current_game_fill_zh,
        current_game_fill_ar,
        current_game_fill_hi;

        public static String getCurrentGameField(AppHelper.Languages s) {
            String language = s.toString();
            if(language.equals("en"))
                return current_game_fill_en.toString();
            else if(language.equals("uk"))
                return  current_game_fill_uk.toString();
            else if(language.equals("ru"))
                return current_game_fill_ru.toString();
            else if(language.equals("hu"))
                return current_game_fill_hu.toString();
            else if(language.equals("de"))
                return current_game_fill_de.toString();
            else if(language.equals("fr"))
                return current_game_fill_fr.toString();
            else if(language.equals("es"))
                return current_game_fill_es.toString();
            else if(language.equals("zh"))
                return current_game_fill_zh.toString();
            else if(language.equals("ar"))
                return current_game_fill_ar.toString();
            else if(language.equals("hi"))
                return current_game_fill_hi.toString();

            return current_game_fill_en.toString();
        }
    }
    public static enum CURRENT_GAME_REVEAL_IN_LANG {
        current_game_reveal_en,
        current_game_reveal_uk,
        current_game_reveal_ru,
        current_game_reveal_hu,
        current_game_reveal_de,
        current_game_reveal_fr,
        current_game_reveal_es,
        current_game_reveal_zh,
        current_game_reveal_ar,
        current_game_reveal_hi;
        public static String getCurrentGameField(AppHelper.Languages s) {
            String language = s.toString();
            if(language.equals("en"))
                return current_game_reveal_en.toString();
            else if(language.equals("uk"))
                return  current_game_reveal_uk.toString();
            else if(language.equals("ru"))
                return current_game_reveal_ru.toString();
            else if(language.equals("hu"))
                return current_game_reveal_hu.toString();
            else if(language.equals("de"))
                return current_game_reveal_de.toString();
            else if(language.equals("fr"))
                return current_game_reveal_fr.toString();
            else if(language.equals("es"))
                return current_game_reveal_es.toString();
            else if(language.equals("zh"))
                return current_game_reveal_zh.toString();
            else if(language.equals("ar"))
                return current_game_reveal_ar.toString();
            else if(language.equals("hi"))
                return current_game_reveal_hi.toString();

            return current_game_reveal_en.toString();
        }
    }
    public static final String MAX_GAME_FILL = "max_game_fill";                                 //int
    public static final String MAX_GAME_REVEAL = "max_game_reveal";                             //int
    public static final String PASSED_GAME = "passed_game";                                     //int
    public static final String PUZZLES_INITIALIZED = "puzzles_initialized";                     //bool
    public static final String BONUS_FLOWER = "bonus_flower";                                   //bool
    public static final String BONUS_SHAKE = "bonus_shake";                                     //bool
    public static final String BONUS_TREE = "bonus_tree";                                       //bool
    public static final String MENU_LEFT_HAND_TUTORIAL = "menu_left_hand_tutorial";             //bool
    public static final String MENU_RIGHT_HAND_TUTORIAL = "menu_right_hand_tutorial";           //bool

    public static final String GAME_BACKGROUND_MUSIC = "game_background_music";
    public static final String MENU_BACKGROUND_MUSIC = "menu_background_music";

    public static final String TAG_DISABLED_ADS = "disabledADS";

    public static final String START_COUNT = "start_count";

    /**
     * Bonus Level
     */
    public static final int CANDY_ON_TOP = 1;
    public static final int CANDY_FALLEN = 2;
    public static final int CANDY_PICKED = 3;

    public static final int APP_LANGUAGE = 1;
    public static final int GAME_LANGUAGE = 2;

    /**
     * Share result
     */

    public static final int SHARE_LOGIN_ERROR = 0;
    public static final int SHARE_POST_ERROR = 1;
    public static final int SHARE_POST_DONE = 2;
    public static final int SHARE_INTERNET_ERROR = 3;
    public static final int SHARE_POST_DUPLICATE = 4;
}
