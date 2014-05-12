package com.mobilez365.puzzly.puzzles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;

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

        ContentValues cv = new ContentValues();
        cv.put(PuzzlesDBHelper.GAME_ID, "0");
        cv.put(PuzzlesDBHelper.GAME_WORD, "Camel");
        cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "0 421");
        cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "0 421");
        cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "123 421");
        cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "12323 421");

        SQLiteDatabase findAllBD = dbHelper.getWritableDatabase();
        findAllBD.insert(PuzzlesDBHelper.FIND_ALL_TABLE_NAME, null, cv);
        findAllBD.close();
    }

    public static void addPuzzlesToDB(Context context){
        initDBHelper(context);

        ContentValues cv = new ContentValues();
        cv.put(PuzzlesDBHelper.GAME_ID, "0");
        cv.put(PuzzlesDBHelper.GAME_WORD, "Camel");
        cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X, "123 421");
        cv.put(PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y, "12323 421");
        cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_X, "123 421");
        cv.put(PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y, "12323 421");

        SQLiteDatabase findAllBD = dbHelper.getWritableDatabase();
        findAllBD.insert(PuzzlesDBHelper.FIND_ALL_TABLE_NAME, null, cv);
        findAllBD.close();
    }

    public static PuzzleFindAllGame getPuzzle(int gameNumber, Context context) {

        String gameWord = "";
        String gameSound;
        String gameImage;
        String gameBorderedImage;
        String gameResultImage;
        List<PuzzlesPart> gameParts = new ArrayList<PuzzlesPart>();

        initDBHelper(context);

        SQLiteDatabase findAllBD = dbHelper.getWritableDatabase();
        Cursor cursor = findAllBD.query(PuzzlesDBHelper.FIND_ALL_TABLE_NAME,
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
            gameImage = gameSound + ".jpg";
            gameBorderedImage = gameSound + "_bordered.jpg";
            gameResultImage = gameSound + "_result.jpg";
            List<Point> finalPosList = parsePositionList(cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_X)),
                    cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_FINAL_POSITION_Y)));
            List<Point> startPosList = parsePositionList(cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_START_POSITION_X)),
                    cursor.getString(cursor.getColumnIndex(
                    PuzzlesDBHelper.GAME_PARTS_START_POSITION_Y)));

            for(int i = 0; i<finalPosList.size();i++) {
                gameParts.add(new PuzzlesPart(i, gameWord + i +".jpg", startPosList.get(i), finalPosList.get(i)));
            }
        }
        else
            return null;

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        findAllBD.close();

        return new PuzzleFindAllGame(gameNumber, gameWord, gameSound, gameParts, gameImage, gameBorderedImage, gameResultImage);
    }

    private static List<Point> parsePositionList(String posX, String posY){
        List<Point> retList = new ArrayList<Point>();

        String delims = "[ ]+";

        String[] posXArray = posX.split(delims);
        String[] posYArray = posY.split(delims);

        for (int i = 0; i < posXArray.length; i++) {
            retList.add(new Point(Integer.valueOf(posXArray[i]), Integer.valueOf(posYArray[i])));
        }

        return retList;
    }

}
