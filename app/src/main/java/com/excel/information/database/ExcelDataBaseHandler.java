package com.excel.information.database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SappiKaran on 13/04/17.
 */

public class ExcelDataBaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "excelManager.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase database;
    private static final String CREATE_TABLE = "CREATE TABLE " + ExcelDbSchema.ListOfTable.NAME + "("
            + ExcelDbSchema.ListOfTable.Columns.ID + " INTEGER PRIMARY KEY, "
            + ExcelDbSchema.ListOfTable.Columns.PRODUCT + " TEXT,"
            + ExcelDbSchema.ListOfTable.Columns.QUANTITY + " INTEGER,"
            + ExcelDbSchema.ListOfTable.Columns.TYPE + " TEXT)";
    private String TAG="ExcelDataBaseHandler";

    public ExcelDataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    public ExcelDataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ExcelDbSchema.ListOfTable.NAME);
        // Create tables again
        onCreate(db);
    }

    public void dropTableIfExists() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(ExcelDbSchema.ListOfTable.NAME, null, null);
    }

    public ArrayList<HashMap<String, String>> getAllProducts() {

        ArrayList<HashMap<String, String>> proList;

        proList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT  * FROM " + ExcelDbSchema.ListOfTable.NAME;

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {


                HashMap<String, String> map = new HashMap<String, String>();

                map.put(ExcelDbSchema.ListOfTable.Columns.ID, cursor.getString(0));

                map.put(ExcelDbSchema.ListOfTable.Columns.PRODUCT, cursor.getString(1));

                map.put(ExcelDbSchema.ListOfTable.Columns.QUANTITY, cursor.getString(2));

                map.put(ExcelDbSchema.ListOfTable.Columns.TYPE, cursor.getString(3));

                proList.add(map);

            } while (cursor.moveToNext());

        }



        return proList;

    }

}
