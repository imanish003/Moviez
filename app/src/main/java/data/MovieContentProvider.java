package data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import data.MovieContract.*;

/**
 * Created by Manish Menaria on 15-Jun-16.
 */
public class MovieContentProvider extends ContentProvider {

    static final int vFAVORITE = 100;
    static final int vTRAILER = 103;
    static final int vREVIEW = 104;
    static final int vFAVORITE_WITH_ID = 101;
    static final int vFAVORITE_ALL = 102;

    private MovieDbHelper movieDbHelper;

    private static final UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY,FavoriteTableContents.TABLE_NAME,vFAVORITE);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY,FavoriteTableContents.TABLE_NAME+"/check",vFAVORITE_WITH_ID);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY,FavoriteTableContents.TABLE_NAME+"/all",vFAVORITE_ALL);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY,TrailerTableContent.TABLE_NAME,vTRAILER);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY,ReviewTableContent.TABLE_NAME,vREVIEW);
    }

    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        Cursor cursor = null;

        switch (uriMatcher.match(uri)){
            case vFAVORITE:
                queryBuilder.setTables(FavoriteTableContents.TABLE_NAME);
                cursor=queryBuilder.query(db,null,
                        FavoriteTableContents._ID+" = ? ",selectionArgs,null,null,null);
                break;
            case vFAVORITE_WITH_ID:
                queryBuilder.setTables(FavoriteTableContents.TABLE_NAME);
                cursor=queryBuilder.query(db,null,
                        FavoriteTableContents.TABLE_NAME+"."+FavoriteTableContents._ID+" = ? ",selectionArgs,null,null,null);
                break;
            case vFAVORITE_ALL:
                queryBuilder.setTables(FavoriteTableContents.TABLE_NAME);
                cursor=queryBuilder.query(db,null,null,null,null,null,null,null);
                break;
            case vREVIEW:
                queryBuilder.setTables(ReviewTableContent.TABLE_NAME);
                cursor=queryBuilder.query(db,null,
                        ReviewTableContent.COLUMN_movie_id+" = ? ",selectionArgs,null,null,null);
                break;
            case vTRAILER:
                queryBuilder.setTables(TrailerTableContent.TABLE_NAME);
                cursor=queryBuilder.query(db,null,
                        TrailerTableContent.COLUMN_movie_id+" = ? ",selectionArgs,null,null,null);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri :"+uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case vFAVORITE:
                db.insert(FavoriteTableContents.TABLE_NAME,null,values);
                break;
            case vREVIEW:
                db.insert(ReviewTableContent.TABLE_NAME,null,values);
                break;
            case vTRAILER:
                db.insert(TrailerTableContent.TABLE_NAME,null,values);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri :"+uri);

        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case vFAVORITE:
                db.delete(FavoriteTableContents.TABLE_NAME,selection,selectionArgs);
                break;
            case vTRAILER:
                db.delete(TrailerTableContent.TABLE_NAME,selection,selectionArgs);
                break;
            case vREVIEW:
                db.delete(ReviewTableContent.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri :"+uri);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
