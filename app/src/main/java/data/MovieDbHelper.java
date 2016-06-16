package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import data.MovieContract.*;

/**
 * Created by Manish Menaria on 13-Jun-16.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;

    static final String DATABASE_NAME = "movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteTableContents.TABLE_NAME + " ("+
                FavoriteTableContents._ID + " INTEGER PRIMARY KEY," +
                FavoriteTableContents.COLUMN_original_title + " TEXT UNIQUE NOT NULL, " +
                FavoriteTableContents.COLUMN_overview + " TEXT NOT NULL, " +
                FavoriteTableContents.COLUMN_poster_path + " TEXT NOT NULL, " +
                FavoriteTableContents.COLUMN_vote_average + " TEXT NOT NULL, " +
                FavoriteTableContents.COLUMN_release_date + " TEXT NOT NULL " +
                " );";

        final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE " + ReviewTableContent.TABLE_NAME + " ("+
                ReviewTableContent._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ReviewTableContent.COLUMN_author + " TEXT NOT NULL, " +
                ReviewTableContent.COLUMN_review + " TEXT NOT NULL ," +
                ReviewTableContent.COLUMN_movie_id + " INTEGER NOT NULL ," +
                " FOREIGN KEY (" + ReviewTableContent.COLUMN_movie_id + ") REFERENCES " +
                FavoriteTableContents.TABLE_NAME + " (" + FavoriteTableContents._ID + ") " +
                " );";

        final String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE " + TrailerTableContent.TABLE_NAME + " ("+
                TrailerTableContent._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TrailerTableContent.COLUMN_source + " TEXT NOT NULL, " +
                TrailerTableContent.COLUMN_Trailer_name + " TEXT NOT NULL, " +
                TrailerTableContent.COLUMN_Internet_Source + " TEXT NOT NULL, " +
                TrailerTableContent.COLUMN_movie_id + " INTEGER NOT NULL ," +
                " FOREIGN KEY (" + TrailerTableContent.COLUMN_movie_id + ") REFERENCES " +
                FavoriteTableContents.TABLE_NAME + " (" + FavoriteTableContents._ID + ") " +
                " );";

        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
        db.execSQL(SQL_CREATE_REVIEW_TABLE);
        db.execSQL(SQL_CREATE_TRAILER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteTableContents.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ReviewTableContent.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrailerTableContent.TABLE_NAME);
        onCreate(db);
    }


}