package com.example.bethechange.nanomovieproject.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.UnsupportedSchemeException;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by BeTheChange on 3/12/2017.
 */

public class MoviesContentProvider extends ContentProvider {
    private MovieDBHelper mMoviesDbHelper;
    private static  UriMatcher sUriMatcher;
    public static final int MOVIES=100;
    public static final int MOVIE_WITH_ID=101;
    public static final int REVIEWS=200;
    public static final int REVIEWS_WITH_MOVIE_ID=201;
    public static final int TRAILERS=300;
    public static final int TRAILERS_WITH_MOVIE_ID=301;
    private UriMatcher buildUriMatcher(){
            UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
            uriMatcher.addURI(MovieContract.Authority,MovieContract.MOVIES_PATH,MOVIES);
            uriMatcher.addURI(MovieContract.Authority,MovieContract.MOVIES_PATH+"/#",MOVIE_WITH_ID);
            uriMatcher.addURI(MovieContract.Authority,MovieContract.REVIEWS_PATH,REVIEWS);
            uriMatcher.addURI(MovieContract.Authority,MovieContract.REVIEWS_PATH+"/#",REVIEWS_WITH_MOVIE_ID);
            uriMatcher.addURI(MovieContract.Authority,MovieContract.TRAILERS_PATH,TRAILERS);
            uriMatcher.addURI(MovieContract.Authority,MovieContract.TRAILERS_PATH+"/#",TRAILERS_WITH_MOVIE_ID);

           return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        mMoviesDbHelper=new MovieDBHelper(getContext());
        sUriMatcher=buildUriMatcher();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db=mMoviesDbHelper.getReadableDatabase();
        Cursor retCursor=null;
        switch (sUriMatcher.match(uri)){
            case MOVIES:
                retCursor=db.query(MovieContract.MovieEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case REVIEWS_WITH_MOVIE_ID:

                //retCursor=db.rawQuery("SELECT * FROM "+MovieContract.TrailerEntry.TABLE_NAME+" WHERE "+MovieContract.TrailerEntry.COLUMN_MOVIE_KEY
                  //      + " = ? ;", new String[]{String.valueOf(movieId)});

                retCursor=db.query(MovieContract.ReviewEntry.TABLE_NAME,projection,MovieContract.ReviewEntry.COLUMN_MOVIE_KEY
                        + " = ?",new String[]{String.valueOf(uri.getPathSegments().get(1))},null,null,sortOrder);
                break;
            case TRAILERS_WITH_MOVIE_ID:
                retCursor=db.query(MovieContract.TrailerEntry.TABLE_NAME,projection,MovieContract.TrailerEntry.COLUMN_MOVIE_KEY
                        +"= ?",new String[]{String.valueOf(uri.getPathSegments().get(1))},null,null,sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknwon URI "+ uri);
        }

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case MOVIES:
                return "vnd.android.cursor.dir" + "/" +MovieContract.Authority+"/"+MovieContract.MOVIES_PATH;
            case MOVIE_WITH_ID:
                return "vnd.android.cursor.dir" + "/" +MovieContract.Authority+"/"+MovieContract.MOVIES_PATH;
            case REVIEWS:
                return "vnd.android.cursor.dir" + "/" +MovieContract.Authority+"/"+MovieContract.REVIEWS_PATH;
            case REVIEWS_WITH_MOVIE_ID:
                return "vnd.android.cursor.dir" + "/" +MovieContract.Authority+"/"+MovieContract.REVIEWS_PATH;
            case TRAILERS:
                return "vnd.android.cursor.dir" + "/" +MovieContract.Authority+"/"+MovieContract.TRAILERS_PATH;
            case TRAILERS_WITH_MOVIE_ID:
                return "vnd.android.cursor.dir" + "/" +MovieContract.Authority+"/"+MovieContract.TRAILERS_PATH;
            default:
                throw new UnsupportedOperationException("Unknown uri : "+uri);

        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db=mMoviesDbHelper.getWritableDatabase();
        Uri retUri=null;
        switch (sUriMatcher.match(uri)){
            case MOVIES:
                  long mId=db.insert(MovieContract.MovieEntry.TABLE_NAME,null,values);
                    if(mId>0)
                       retUri= ContentUris.withAppendedId(uri,mId);
                    else
                       throw new SQLException("Failed To Insert row into "+uri);
                break;
            case REVIEWS:
                long RId=db.insert(MovieContract.ReviewEntry.TABLE_NAME,null,values);
                if(RId>0)
                    retUri= ContentUris.withAppendedId(uri,RId);
                else
                    throw new SQLException("Failed To Insert row into "+uri);
                break;
            case TRAILERS:
                long TId=db.insert(MovieContract.TrailerEntry.TABLE_NAME,null,values);
                if(TId>0)
                    retUri= ContentUris.withAppendedId(uri,TId);
                else
                    throw new SQLException("Failed To Insert row into "+uri);
                break;

        }
        if(retUri!=null)
            getContext().getContentResolver().notifyChange(uri,null);
        return retUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db= mMoviesDbHelper.getWritableDatabase();
        int num=0;
        switch (sUriMatcher.match(uri)){
            case REVIEWS_WITH_MOVIE_ID:
                num=db.delete(MovieContract.ReviewEntry.TABLE_NAME, MovieContract.ReviewEntry.COLUMN_MOVIE_KEY+" = ? ",
                        new String[]{uri.getPathSegments().get(1)});
                break;
            case TRAILERS_WITH_MOVIE_ID:
                num=db.delete(MovieContract.TrailerEntry.TABLE_NAME, MovieContract.TrailerEntry.COLUMN_MOVIE_KEY+" = ? ",
                        new String[]{uri.getPathSegments().get(1)});
                break;
            case MOVIE_WITH_ID:
                num=db.delete(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry._ID+" = ? ",
                        new String[]{String.valueOf(uri.getPathSegments().get(1))});
                break;
            case MOVIES:
                num=db.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new UnsupportedOperationException("the operation of this uri is unsupported :: " +uri);
        }


       //
        return num;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
