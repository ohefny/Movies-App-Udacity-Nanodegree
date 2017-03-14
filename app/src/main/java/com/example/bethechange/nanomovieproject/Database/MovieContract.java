package com.example.bethechange.nanomovieproject.Database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by BeTheChange on 3/9/2017.
 */

public class MovieContract {
    public static  final String Authority="com.example.bethechange.nanomovieproject";
    public static final Uri Base_Content_URI= Uri.parse("content://"+Authority);
    public static final String MOVIES_PATH="movies";
    public static final String REVIEWS_PATH="reviews";
    public static final String TRAILERS_PATH="trailers";

    private MovieContract(){}
    public static final class MovieEntry implements BaseColumns {
        public  static final Uri CONTENT_URI=Base_Content_URI.buildUpon().appendPath(MOVIES_PATH).build();
       // public  static final Uri CONTENT_URI=Uri.parse(Base_Content_URI.toString()+"/"+MOVIES_PATH);
        public static final String TABLE_NAME = "Movie_Table";
        public static final String COLUMN_VOTE_AVERAGE="vote_average";
        public static final String COLUMN_POSTER = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_DATE = "release_date";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_RATE = "popularity";
    }

    public static final class ReviewEntry implements BaseColumns {
        public  static final Uri CONTENT_URI=Base_Content_URI.buildUpon().appendPath(REVIEWS_PATH).build();
        public static final String TABLE_NAME = "Review_Table";
        public static final String COLUMN_AUTHOR= "author";
        public static final String COLUMN_CONTENT = "review_content";
        public static final String COLUMN_MOVIE_KEY = "movie_id";
    }
    public static final class TrailerEntry implements BaseColumns{
        public  static final Uri CONTENT_URI=Base_Content_URI.buildUpon().appendPath(TRAILERS_PATH).build();
        public static final String TABLE_NAME = "Trailer_Table";
        public static final String COLUMN_TRAILER_KEY = "trailer_key";
        public static final String COLUMN_TRAILER_NAME = "title";
        public static final String COLUMN_LINK= "link";
        public static final String COLUMN_MOVIE_KEY = "movie_id";
    }
}
