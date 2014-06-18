package com.mobilez365.puzzly.puzzles;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.util.DisplayMetrics;

import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class PuzzlesDB {

    private static PuzzlesDBHelper dbHelper;


    private static void initDBHelper(Context context) {
        if (dbHelper == null)
            dbHelper = new PuzzlesDBHelper(context);
    }

    public static void addBasePuzzlesToDB(Context context) {
        if (!AppHelper.getPuzzlesInit(context)) {
            initDBHelper(context);

            SQLiteDatabase findAllBD = dbHelper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "ice_cream");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "18 18");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "5 167");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "20 170");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "220 30");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "500");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "40");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
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
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "frogling");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "2 152");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "176 17");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "20 30");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "50 230");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "400");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "60");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "hen");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 110 207");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "35 96 8");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "270 125 40");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "270 90 270");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "460");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "100");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "pigeon");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "22 94 116");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "132 78 9");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "20 170 20");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "50 230 300");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "440");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "100");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "giraffe");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 104 169");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 184 207");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "130 285 55");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "265 110 100");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "510");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "40");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "octopus");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "2 89 199");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "133 18 138");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "250 120 15");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "60 228 60");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "420");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "90");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "penguin");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "72 8 25");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 137 240");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "112 60 70");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "169 40 350");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "500");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "65");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "piggy");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "2 71 231");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "49 2 19");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "150 30 35");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "140 300 20");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "420");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "140");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "snake");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "80 8 151");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 147 148");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "70 70 210");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "110 290 70");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "425");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "85");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "rabbit");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 99 115 216");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "91 106 163 8");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "185 105 110 35");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "220 380 105 165");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "410");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "125");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
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
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "flamingo");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "152 16 136 128");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "11 142 126 222");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "85 40 228 245");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "280 95 85 230");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "510");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "35");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "zebra");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 98 183 19 181");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 88 88 135 159");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "170 40 35 160 35");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "325 340 195 100 100");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "410");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "110");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "backpack");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "74 8 59 87 104");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 122 61 44 112");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "240 20 290 45 125");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "360 115 100 350 145");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "500");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "115");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "bread");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "84 90 11 8 70");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 92 151 100 101");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "55 16 226 135 217");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "148 272 300 82 40");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "480");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "90");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "hamburger");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "28 151 8 19 157");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 8 61 89 76");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "220 50 50 210 50");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "130 240 340 240 130");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "460");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "160");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "lemon");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 92 173 85 105");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 131 140 198 262");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "150 35 105 82 224");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "265 185 32 313 109");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "485");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "40");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "lime");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 86 58 161 130");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 113 156 190 230");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "26 10 330 175 286");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "337 185 146 100 307");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "475");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "60");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "shuttlecocks");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "93 156 59 100 8");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 44 101 119 157");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "35 205 205 135 35");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "325 160 350 70 175");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "485");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "105");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "stapler");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "40 96 63 49 8");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 26 63 126 65");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "75 40 200 260 15");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "355 275 185 275 85");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "470");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "145");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "teddy_bear");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "21 8 45 116 119 200");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 54 125 57 189 118");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "25 195 265 25 185 265");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "255 55 255 55 345 55");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "485");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "100");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "corn");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "48 48 19 96 8 102");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 121 43 95 114 138");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "145 140 260 45 270 30");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "335 170 230 285 70 125");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "550");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "75");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "crocodile");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "82 9 81 103 145 221");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "9 37 32 33 38 45");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "45 40 240 140 230 115");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "240 360 240 260 360 140");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "400");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "200");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "kettle");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "132 8 85 142 268 124");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 73 67 93 70 218");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "200 55 155 210 30 35");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "60 100 300 150 330 255");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "410");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "120");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "tea_cup");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 53 100 146 37 99");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 46 8 24 123 123");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "240 45 60 150 200 60");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "215 100 345 100 350 200");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "495");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "135");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "beet");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "217 233 252 101 91 19");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 53 97 81 123 221");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "280 286 40 63 63 228");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "311 90 244 323 75 188");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "435");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "70");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "baseball_cap");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "92 132 209 77 130 242 8");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "17 8 16 68 88 88 136");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "145 90 295 30 250 225 80");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "205 313 195 200 360 252 85");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "455");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "135");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "stork");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "42 46 64 136 8 50 177");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 73 53 83 163 133 108");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "80 275 245 275 60 50 44");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "75 325 55 200 385 280 200");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "420");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "135");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "crab");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "106 8 75 180 185 81 180");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "66 8 109 105 10 195 195");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "40 215 210 155 20 40 245");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "100 300 85 235 300 175 185");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "410");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "120");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "gorilla");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "15 8 38 65 112 138 186");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 97 81 52 67 168 99");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "125 120 230 35 200 40 240");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "130 230 255 100 340 360 80");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "510");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "105");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "potato");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "35 124 8 78 143 45 111");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "10 8 35 71 27 113 94");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "22 260 150 180 305 80 255");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "223 140 60 270 215 360 350");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "525");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "160");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "wagon");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 23 42 158 27 158 42 221");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 30 48 30 92 92 124 128");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "40 216 70 45 122 122 275 45");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "420 265 345 265 95 180 175 175");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "450");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "160");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "computer_chair");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "70 63 9 8 70 149 23 99");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 68 100 146 147 108 207 208");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "35 200 135 70 200 285 180 40");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "360 360 110 280 280 155 170 180");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "515");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "100");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "sea_bear");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "239 211 80 111 189 8 83 234");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 56 62 73 70 93 148 125");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "100 50 50 25 228 240 155 240");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "200 295 360 90 360 240 100 165");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "425");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "155");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "yacht");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "73 170 182 213 253 8 73 134 320");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 18 8 18 10 59 62 97 75");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "30 275 210 140 270 30 30 30 30");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "100 215 100 215 325 325 265 420 190");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "390");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "175");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "butterfly");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "9 78 114 150 200 37 208 60 108 193");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "29 40 25 26 8 83 70 132 127 120");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "200 175 110 20 45 145 30 225 260 100");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "80 175 240 145 385 330 290 315 170 80");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "470");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "135");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "car");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "77 100 205 275 11 121 202 202 8 263");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 20 18 24 72 72 70 72 125 125");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "85 140 260 20 15 240 160 25 15 240");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "50 125 130 80 228 270 200 355 140 395");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "370");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "140");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "koala");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 27 78 164 183 64 120 156 25 95 46 152");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "31 50 16 8 28 78 91 122 151 158 182 182");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "40 205 125 275 30 180 275 40 130 115 20 225");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "75 400 150 355 395 60 170 190 255 345 275 240");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "490");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "115");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv.put(PuzzlesDBHelper.GAME_TYPE, "0");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "train");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "180 24 55 168 200 244 8 102 236 37 76 160 249");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 36 62 86 64 75 101 101 108 163 196 175 184");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "200 22 197 125 305 195 165 30 305 51 50 260 41");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "275 185 190 95 222 95 360 260 311 382 150 85 83");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "460");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "130");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "carrot");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "120 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "4 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "60 260 30 320");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "45 58 240 240");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "515");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "80");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "fingerling");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "178 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "29 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "220 60 220 30");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "250 25 30 280");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "415");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "125");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "mushrooms");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "63 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "60 300 70 290");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "80 80 300 275");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "560");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "150");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "key");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "14 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "14 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "80 335 40 300");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "45 45 360 300");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "590");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "95");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "strawberries");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "26 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "320 75 290 75");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "315 105 95 320");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "560");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "140");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "rhino");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "60 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "55 215 55 215");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "140 140 330 330");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "360");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "185");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "pumpkin");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "76 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "60 92 280 300");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "300 92 92 300");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "500");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "125");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "scrambled_eggs");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "85 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "75 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "70 278 60 280");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "100 108 272 290");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "480");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "125");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
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
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "turtle");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "34 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "147 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "90 255 70 250");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "135 115 295 345");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "480");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "135");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "banana");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "166 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "70 64 280 281");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "333 107 125 306");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "475");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "125");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "dog");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "210 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "57 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "290 90 90 310");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "150 115 295 290");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "480");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "150");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "eggplant");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "211 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "77 280 75 280");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "96 120 308 300");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "450");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "120");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "fox");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "230 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "45 75 275 235");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "340 125 115 380");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "410");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "155");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "peas");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "67 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "106 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "205 55 215 45");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "355 145 145 360");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "435");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "165");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "cancer");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "150 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "50 70 255 235");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "280 115 105 320");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "510");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "75");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "pepper");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "154 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "70 80 280 230");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "300 90 100 300");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "520");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "120");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "monkey");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "33 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "6 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "255 30 35 260");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "40 45 308 324");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "520");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "70");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "pot_with_honey");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "52 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "60 77 260 275");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "300 96 115 290");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "505");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "80");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "lamb");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "9 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "49 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "235 40 170 25");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "310 80 80 320");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "385");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "75");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "wizard_hat");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "74 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "265 45 260 70");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "250 90 100 335");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "500");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "135");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "squirrel");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "167 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "19 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "60 35 254 234");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "215 30 30 250");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "465");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "90");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "crew_cut");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "189 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "48 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "55 210 60 220");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "150 130 320 340");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "425");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "135");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "jug_with_milk");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "166 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "14 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "90 85 255 265");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "285 85 90 285");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "490");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "165");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
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
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "lion");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "99 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "55 85 195 240");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "330 110 140 315");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "390");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "125");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "dolphin");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "63 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "17 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "30 40 240 265");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "325 50 70 260");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "430");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "120");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "soother");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "35 245 35 260");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "105 85 290 280");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "510");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "160");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "hippo");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "32 55 268 318");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "235 75 90 216");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "530");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "135");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "toucan");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "196 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "47 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "20 20 225 270");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "360 125 120 340");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "380");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "175");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "light_bulb");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "79 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "72 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "55 280 65 270");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "90 100 305 305");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "535");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "90");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "bee");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "11 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "10 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "55 300 55 306");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "28 45 255 280");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "500");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "115");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "radish");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "114 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "70 70 270 270");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "290 160 100 350");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "490");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "130");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "snail");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "38 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "35 40 260 260");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "305 90 90 305");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "430");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "160");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "umbrella");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "137 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "61 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "50 50 280 285");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "245 80 80 280");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "460");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "135");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "elephant");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "5 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "5 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "20 55 302 302");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "290 45 50 240");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "495");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "110");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "cock");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "128 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "25 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "250 70 70 290");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "95 115 300 300");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "500");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "115");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "cherry");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "176 100000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "275 56 286 50");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "306 120 105 290");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "508");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "85");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_TYPE, "1");
            cv.put(PuzzlesDBHelper.GAME_ITEM_NAME, "pear");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "8 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "92 10000 10000 10000");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "52 321 40 316");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "77 86 320 321");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "533");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "63");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);


            findAllBD.close();

            AppHelper.setPuzzlesInit(context, true);
        }
    }

    public static int getPuzzleGameCount(Context context, int gameType) {
        initDBHelper(context);

        SQLiteDatabase findAllBD = dbHelper.getWritableDatabase();

        Cursor cursor = findAllBD.query(PuzzlesDBHelper.TABLE_NAME_FILL_GAME,
                PuzzlesDBHelper.FIND_ALL_COLUMN,
                PuzzlesDBHelper.GAME_TYPE + " =?",
                new String[]{String.valueOf(gameType)},
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

    public static PuzzleFillGame getPuzzle(int gameNumber, int type, Context context) {
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
            gameItemName = cursor.getString(cursor.getColumnIndex(PuzzlesDBHelper.GAME_ITEM_NAME));

                if (AppHelper.getShowImageBorder(context) && type == 0)
                    gameImage = gameItemName + "_bordered";
                else
                    gameImage = gameItemName + "_gray";

                gameResultImage = gameItemName + "_result";

                List<Point> finalPosList = parsePositionList(cursor.getString(cursor.getColumnIndex(
                                PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X)),
                        cursor.getString(cursor.getColumnIndex(
                                PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y)), context
                );

                List<Point> startPosList = parsePositionList(cursor.getString(cursor.getColumnIndex(
                                PuzzlesDBHelper.GAME_PARTS_START_POSITION_X)),
                        cursor.getString(cursor.getColumnIndex(
                                PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y)), context
                );

                for (int i = 0; i < finalPosList.size(); i++) {
                    gameParts.add(new PuzzlesPart(i, gameItemName + i, startPosList.get(i), finalPosList.get(i)));
                }


                figurePoint.x = cursor.getInt(cursor.getColumnIndex(PuzzlesDBHelper.GAME_FIGURE_POSITION_X));
                figurePoint.y = cursor.getInt(cursor.getColumnIndex(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y));
                break;
            } else number++;
        } while (cursor.moveToNext());

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        findAllBD.close();

        return new PuzzleFillGame(type, gameItemName, gameParts, gameImage, gameResultImage, figurePoint);
    }

    /*public static void updateTableGameWord(Context _context)
    {
        initDBHelper(_context);
        SQLiteDatabase findAllBD = dbHelper.getWritableDatabase();

        Cursor cursor = findAllBD.query(PuzzlesDBHelper.TABLE_NAME_FILL_GAME,
                PuzzlesDBHelper.FIND_ALL_COLUMN,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        do {
            String gameItem = cursor.getString(cursor.getColumnIndex(PuzzlesDBHelper.GAME_ITEM_NAME));
            String updateWord = _context.getResources().getString(_context.getResources().getIdentifier(gameItem, "string", _context.getPackageName()));

            ContentValues cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_WORD, updateWord);

            findAllBD.update(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, cv, PuzzlesDBHelper.GAME_ITEM_NAME + "='" + gameItem + "'", null);
        } while (cursor.moveToNext());
        findAllBD.close();
    }*/

    private static List<Point> parsePositionList(String posX, String posY, Context context) {
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
