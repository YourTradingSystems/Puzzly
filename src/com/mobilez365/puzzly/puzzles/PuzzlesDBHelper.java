package com.mobilez365.puzzly.puzzles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class PuzzlesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "puzzles_database.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME_FILL_GAME = "find_all_table";

    public static final String GAME_ID = "game_id";
    public static final String GAME_WORD = "game_word";
    public static final String GAME_PARTS_FINAL_POSITION_X = "game_parts_final_pos_x";
    public static final String GAME_PARTS_FINAL_POSITION_Y = "game_parts_final_pos_y";
    public static final String GAME_PARTS_START_POSITION_X = "game_parts_start_pos_x";
    public static final String GAME_PARTS_START_POSITION_Y = "game_parts_start_pos_y";
    public static final String GAME_FIGURE_POSITION_X = "game_figure_pos_x";
    public static final String GAME_FIGURE_POSITION_Y = "game_figure_pos_y";

    public static final String[] FIND_ALL_COLUMN = {GAME_ID, GAME_WORD, GAME_PARTS_FINAL_POSITION_X, GAME_PARTS_FINAL_POSITION_Y,
            GAME_PARTS_START_POSITION_X, GAME_PARTS_START_POSITION_Y, GAME_FIGURE_POSITION_X, GAME_FIGURE_POSITION_Y};

    private static final String SQL_CREATE_FILL_GAME_ENTRIES = "CREATE TABLE "
            + TABLE_NAME_FILL_GAME + " ("
            + GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + GAME_WORD + " TEXT,"
            + GAME_PARTS_FINAL_POSITION_X + " TEXT,"
            + GAME_PARTS_FINAL_POSITION_Y + " TEXT,"
            + GAME_PARTS_START_POSITION_X + " TEXT,"
            + GAME_PARTS_START_POSITION_Y + " TEXT,"
            + GAME_FIGURE_POSITION_X + " TEXT,"
            + GAME_FIGURE_POSITION_Y + " TEXT"
            +");";

    private static final String SQL_DELETE_FIND_ALL_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME_FILL_GAME;


    public PuzzlesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FILL_GAME_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_FIND_ALL_ENTRIES);
        onCreate(db);
    }
}
