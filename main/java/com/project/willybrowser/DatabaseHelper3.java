package com.project.willybrowser;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper3 extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper3";
    private static final String TABLE_NAME = "downloads";
    private static final String COL1 = "ID";
    private static final String COL2 = "filename";

    public DatabaseHelper3(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addData(String item) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + ") VALUES ('" + item + "')");
        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);
    }

    public void deleteData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME);
        Log.d(TAG, "deleteData: Removing all item from " + TABLE_NAME);
    }

    public Cursor getData() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY ID DESC";
        Cursor data = sqLiteDatabase.rawQuery(query, null);
        return data;
    }
}
