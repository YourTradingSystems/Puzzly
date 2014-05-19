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
            cv.put(PuzzlesDBHelper.GAME_ID, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Pig");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Cвинья");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "1 70 230");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "48 1 18");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "150 30 35");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "140 300 20");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "420");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "140");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_ID, "1");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Chicken");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Курица");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "400 536 621");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "116 192 85");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "170 15 40");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "170 20 260");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "140");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "320");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_ID, "2");
            cv.put(PuzzlesDBHelper.GAME_WORD_ENG, "Dove");
            cv.put(PuzzlesDBHelper.GAME_WORD_RUS, "Голубь");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "450 522 544");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "218 164 95");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "20 170 20");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "50 230 300");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_X, "320");
            cv.put(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y, "140");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            findAllBD.close();

            AppHelper.setPuzzlesInit((Activity) context, true);
        }
    }

    public static int getPuzzleGameCount(Context context){
        initDBHelper(context);

        SQLiteDatabase findAllBD = dbHelper.getWritableDatabase();
        Cursor cursor = findAllBD.query(PuzzlesDBHelper.TABLE_NAME_FILL_GAME,
                PuzzlesDBHelper.FIND_ALL_COLUMN,
                null,
                null,
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

    public static PuzzleFillGame getPuzzle(int gameNumber, Activity context) {

        String gameWordEng = "";
        String gameWordRus = "";
        String gameImage;
        String gameResultImage;
        List<PuzzlesPart> gameParts = new ArrayList<PuzzlesPart>();
        Point figurePoint = new Point();

        initDBHelper(context);

        SQLiteDatabase findAllBD = dbHelper.getWritableDatabase();
        Cursor cursor = findAllBD.query(PuzzlesDBHelper.TABLE_NAME_FILL_GAME,
                PuzzlesDBHelper.FIND_ALL_COLUMN,
                PuzzlesDBHelper.GAME_ID + " =?",
                new String[] {String.valueOf(gameNumber)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            gameWordEng = cursor.getString(cursor.getColumnIndex(PuzzlesDBHelper.GAME_WORD_ENG));
            gameWordRus = cursor.getString(cursor.getColumnIndex(PuzzlesDBHelper.GAME_WORD_RUS));

            if(AppHelper.getShowImageBorder(context))
                gameImage = gameWordEng.toLowerCase() + "_bordered";
            else
                gameImage = gameWordEng.toLowerCase() + "_gray";

            gameResultImage = gameWordEng.toLowerCase() + "_result";

            List<Point> finalPosList = parsePositionList(cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X)),
                    cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y)), context);

            List<Point> startPosList = parsePositionList(cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_START_POSITION_X)),
                    cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y)), context);

            for(int i = 0; i < finalPosList.size(); i++) {
                gameParts.add(new PuzzlesPart(i, gameWordEng.toLowerCase() + i, startPosList.get(i), finalPosList.get(i)));
            }


            figurePoint.x = cursor.getInt(cursor.getColumnIndex(PuzzlesDBHelper.GAME_FIGURE_POSITION_X));
            figurePoint.y = cursor.getInt(cursor.getColumnIndex(PuzzlesDBHelper.GAME_FIGURE_POSITION_Y));
        }
        else
            return null;

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        findAllBD.close();

        return new PuzzleFillGame(gameWordEng, gameWordRus, gameParts, gameImage, gameResultImage, figurePoint);
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
