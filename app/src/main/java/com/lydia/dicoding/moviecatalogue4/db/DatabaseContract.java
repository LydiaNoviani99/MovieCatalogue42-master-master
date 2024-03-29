package com.lydia.dicoding.moviecatalogue4.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.lydia.dicoding.moviecatalogue4";
    public static final String SCHEME = "content";

    private DatabaseContract() {}

    public static final class MovieColumns implements BaseColumns {

        public static String TABLE_NAME = "MOVIE";
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String POSTER_PATH = "poster_path";
        public static String RELEASE_DATE = "release_date";
        public static String POPULARITY = "popularity";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static final class TvshowColumns implements BaseColumns {

        public static String TABLE_NAME_TV = "TVSHOW";
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String POSTER_PATH = "poster_path";

        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME_TV)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
