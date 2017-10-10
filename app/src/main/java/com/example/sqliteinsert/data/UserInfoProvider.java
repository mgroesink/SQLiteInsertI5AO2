package com.example.sqliteinsert.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Marcel on 9-10-2017.
 */

public class UserInfoProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private myDbHelper dbHelper;

    static {
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY , UserContract.UserEntity.TABLE_NAME , 1);
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY , UserContract.UserEntity.TABLE_NAME + "/#" , 2);
    }
    @Override
    public boolean onCreate() {
        dbHelper = new myDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        switch(sUriMatcher.match(uri)){
            case 1:
                if(TextUtils.isEmpty(sortOrder))
                    sortOrder = UserContract.UserEntity.UID;
                break;
            case 2:
                selection = selection + UserContract.UserEntity.UID + " = " + uri.getLastPathSegment();
                break;
            default:
                return null;

        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(UserContract.UserEntity.TABLE_NAME , projection ,
                selection , selectionArgs , null , null , sortOrder );

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch(sUriMatcher.match(uri)){
            case 1:
                return InsertUser(uri , values);
            default:
                return null;

        }

    }

    private Uri InsertUser(Uri uri , ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newId =  db.insert(UserContract.UserEntity.TABLE_NAME , null, values);
        return ContentUris.withAppendedId(uri , newId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
