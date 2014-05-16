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
        if(AppHelper.getPuzzlesInit((Activity) context)) {
            initDBHelper(context);

            SQLiteDatabase findAllBD = dbHelper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_ID, "0");
            cv.put(PuzzlesDBHelper.GAME_WORD, "pig");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "420 489 650");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "187 140 157");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "20 100 150");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "30 100 20");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_ID, "1");
            cv.put(PuzzlesDBHelper.GAME_WORD, "chicken");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "400 536 621");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "116 192 85");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "20 100 150");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "30 100 20");

            findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            cv = new ContentValues();
            cv.put(PuzzlesDBHelper.GAME_ID, "2");
            cv.put(PuzzlesDBHelper.GAME_WORD, "dove");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "450 522 544");
            cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "218 164 95");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "20 100 150");
            cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "30 100 20");

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

    public static PuzzleFillGame getPuzzle(int gameNumber, Context context) {

        String gameWord = "";
        String gameImage;
        String gameBorderedImage;
        String gameResultImage;
        List<PuzzlesPart> gameParts = new ArrayList<PuzzlesPart>();

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
            gameWord = cursor.getString(cursor.getColumnIndex(PuzzlesDBHelper.GAME_WORD));
            gameImage = gameWord + "_gray";
            gameBorderedImage = gameWord + "_bordered";
            gameResultImage = gameWord + "_result";

            List<Point> finalPosList = parsePositionList(cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X)),
                    cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y)), context);

            List<Point> startPosList = parsePositionList(cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_START_POSITION_X)),
                    cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y)), context);

            for(int i = 0; i < finalPosList.size(); i++) {
                gameParts.add(new PuzzlesPart(i, gameWord + i, startPosList.get(i), finalPosList.get(i)));
            }
        }
        else
            return null;

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        findAllBD.close();

        return new PuzzleFillGame(gameNumber, gameWord, gameParts, gameImage, gameBorderedImage, gameResultImage);
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
