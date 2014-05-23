package com.mobilez365.puzzly.puzzles;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.util.DisplayMetrics;
import com.mobilez365.puzzly.global.AppHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class PuzzlesDB {

    private static PuzzlesDBHelper dbHelper;


    private static void initDBHelper (Context context){
        if(dbHelper == null)
            dbHelper = new PuzzlesDBHelper(context);
    }

    public static void addBasePuzzlesToDB(Context context){
        if(!AppHelper.getPuzzlesInit((Activity) context)) {
            initDBHelper(context);

            SQLiteDatabase findAllBD = dbHelper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Ice cream");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Мороженое");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "icecream");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "18 18");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "5 167");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "20 170");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "220 30");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "500");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "40");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Pencil");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Карандаш");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "pencil");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "7 63");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "114 8");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "50 70");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "20 230");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "500");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "70");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Frogling");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Лягушонок");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "toad");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "2 152");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "176 17");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "20 30");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "50 230");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "400");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "60");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Piggy");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Поросенок");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "pig");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "2 71 231");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "49 2 19");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "150 30 35");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "140 300 20");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "420");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "140");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Pigeon");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Голубь");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "dove");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "22 94 116");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "132 78 9");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "20 170 20");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "50 230 300");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "440");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "100");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Rabbit");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Кролик");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "rabbit");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "4 46 197");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "167 94 4");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "200 0 20");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "0 50 230");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "370");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "80");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Hen");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Курочка");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "chicken");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "6 142 227");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "46 122 15");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "190 15 40");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "170 20 260");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "380");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "70");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Octopus");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Осьминог");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "octopus");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "2 89 199");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "133 18 138");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "10 15 160");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "10 225 70");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "420");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "70");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Snake");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Змея");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "snake");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "87 15 158");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "16 154 156");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "70 70 210");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "110 290 70");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "425");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "85");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Penguin");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Пингвинчик");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "penguin");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "80 16 33");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "13 142 245");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "112 60 70");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "169 40 350");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "500");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "65");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Goat");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Козлик");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "goat");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "9 103 250 145");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "81 15 39 165");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "210 30 258 30");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "115 248 278 50");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "390");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "100");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Giraffe");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Жираф");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "giraffe");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "28 106 110");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "279 227 21");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "188 270 30");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "55 191 224");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "475");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "25");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Flamingo");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Фламинго");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "flamingo");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "152 16 136 128");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "11 142 126 222");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "85 40 228 245");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "280 95 85 230");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "515");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "35");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Teddy-bear");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Медвежонок");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "teddy-bear");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "14 17 15");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "9 115 232");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "55 169 75");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "316 180 39");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "525");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "60");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Zebra");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Зебра");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "zebra");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "34 12 141");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "172 76 11");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "245 128 55");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "310 26 215");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "490");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "80");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Apple");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Яблочко");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "apple");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "94 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "11 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "65 75 235 266");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "260 55 60 300");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "480");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "60");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Carrot");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Морковка");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "carrot");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "120 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "4 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "60 260 30 320");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "45 58 240 240");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "535");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "80");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Fingerling");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Рыбка");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "fish");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "178 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "29 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "220 60 220 30");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "250 25 30 280");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "425");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "125");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Snail");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Улитка");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "snail");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "43 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "36 30 275 267");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "275 30 30 252");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "391");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "137");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Owl");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Сова");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "owl");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "110 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "141 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "310 58 35 290");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "28 50 240 290");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "550");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "50");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Squirrel");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Белочка");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "squirrel");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "167 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "19 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "60 35 254 234");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "215 30 30 250");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "480");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "90");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Lamb");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Овечка");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "lamb");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "6 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "49 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "280 60 210 35");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "320 25 30 285");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "414");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "75");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Elephant");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Слоник");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "elephant");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "5 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "5 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "20 55 302 302");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "290 45 50 240");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "495");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "110");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            findAllBD.close();

            AppHelper.setPuzzlesInit((Activity) context, true);
        }
    }

    public static int getPuzzleGameCount(Context context, int gameType){
        initDBHelper(context);

        SQLiteDatabase findAllBD = dbHelper.getWritableDatabase();
        Cursor cursor = findAllBD.query(PuzzlesDBHelper.TABLE_NAME_FILL_GAME,
                PuzzlesDBHelper.FIND_ALL_COLUMN,
                PuzzlesDBHelper.GAME_TYPE + " =?",
                new String[] {String.valueOf(gameType)},
                null,
                null,
                null
        );

        int gameCount = cursor.getCount();

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        findAllBD.close();

        return gameCount;
    }

    public static PuzzleFillGame getPuzzle(int gameNumber, int type, Activity context) {
        String gameWordEng = "";
        String gameWordRus = "";
        String gameItemName = "";
        String gameImage = "";
        String gameResultImage = "";
        List<PuzzlesPart> gameParts = new ArrayList<PuzzlesPart>();
        Point figurePoint = new Point();

        initDBHelper(context);

        SQLiteDatabase findAllBD = dbHelper.getWritableDatabase();
        Cursor cursor = findAllBD.query(PuzzlesDBHelper.TABLE_NAME_FILL_GAME,
                PuzzlesDBHelper.FIND_ALL_COLUMN,
                PuzzlesDBHelper.GAME_TYPE + " =?",
                new String[] {String.valueOf(type)},
                null,
                null,
                null
        );

        cursor.moveToFirst();
        int number = 0;
        do {
            if(number == gameNumber)
            {
        	gameWordEng = cursor.getString(cursor.getColumnIndex(PuzzlesDBHelper.GAME_WORD_ENG));
            gameWordRus = cursor.getString(cursor.getColumnIndex(PuzzlesDBHelper.GAME_WORD_RUS));
            gameItemName = cursor.getString(cursor.getColumnIndex(PuzzlesDBHelper.GAME_ITEM_NAME));

            if(AppHelper.getShowImageBorder(context) && type == 0)
                gameImage = gameItemName + "_bordered";
            else
                gameImage = gameItemName + "_gray";

            gameResultImage = gameItemName + "_result";

            List<Point> finalPosList = parsePositionList(cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X)),
                    cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y)), context);

            List<Point> startPosList = parsePositionList(cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_START_POSITION_X)),
                    cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y)), context);

            for(int i = 0; i < finalPosList.size(); i++) {
                gameParts.add(new PuzzlesPart(i, gameItemName + i, startPosList.get(i), finalPosList.get(i)));
            }


            figurePoint.x = cursor.getInt(cursor.getColumnIndex(PuzzlesDBHelper.GAME_FIGURE_POSITION_X));
            figurePoint.y = cursor.getInt(cursor.getColumnIndex(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y));
                break;
            }
            else number++;
        }    while (cursor.moveToNext());

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        findAllBD.close();

        return new PuzzleFillGame(type, gameWordEng, gameWordRus, gameItemName, gameParts, gameImage, gameResultImage, figurePoint);
    }

    private static List<Point> parsePositionList(String posX, String posY, Context context){
        List<Point> retList = new ArrayList<Point>();

        String delims = "[ ]+";

        String[] posXArray = posX.split(delims);
        String[] posYArray = posY.split(delims);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        for (int i = 0; i < posXArray.length; i++) {

            int dpPosX = Integer.valueOf(posXArray[i]);
            int dpPosY = Integer.valueOf(posYArray[i]);

            retList.add(new Point(dpPosX, dpPosY));
        }

        return retList;
    }

}
