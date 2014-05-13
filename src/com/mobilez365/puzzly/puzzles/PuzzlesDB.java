package com.mobilez365.puzzly.puzzles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.util.DisplayMetrics;

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
        initDBHelper(context);

        SQLiteDatabase findAllBD = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(PuzzlesDBHelper.GAME_ID, "0");
        cv.put(PuzzlesDBHelper.GAME_WORD, "camel");
        cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "0 421");
        cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "0 421");
        cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "123 421");
        cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "12323 421");

        findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

        cv = new ContentValues();
        cv.put(PuzzlesDBHelper.GAME_ID, "1");
        cv.put(PuzzlesDBHelper.GAME_WORD, "tiger");
        cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "0 421");
        cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "0 421");
        cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "123 421");
        cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "12323 421");

        findAllBD.insertWithOnConflict(PuzzlesDBHelper.TABLE_NAME_FILL_GAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

        findAllBD.close();
    }

    public static PuzzleFillGame getPuzzle(int gameNumber, Context context) {

        String gameWord = "";
        String gameSound;
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
            gameSound = gameWord + ".mp3";
            gameImage = gameSound + ".png";
            gameBorderedImage = gameSound + "_bordered.png";
            gameResultImage = gameSound + "_result.png";

            List<Point> finalPosList = parsePositionList(cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X)),
                    cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y)), context);

            List<Point> startPosList = parsePositionList(cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_START_POSITION_X)),
                    cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y)), context);

            for(int i = 0; i < finalPosList.size(); i++) {
                gameParts.add(new PuzzlesPart(i, gameWord + i +".png", startPosList.get(i), finalPosList.get(i)));
            }
        }
        else
            return null;

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        findAllBD.close();

        return new PuzzleFillGame(gameNumber, gameWord, gameSound, gameParts, gameImage, gameBorderedImage, gameResultImage);
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
            dpPosX = (int) (dpPosX * (metrics.densityDpi / 160f));
            dpPosY = (int) (dpPosY * (metrics.densityDpi / 160f));

            retList.add(new Point(dpPosX, dpPosY));
        }

        return retList;
    }

}
