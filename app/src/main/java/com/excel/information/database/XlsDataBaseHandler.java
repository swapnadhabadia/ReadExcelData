package com.excel.information.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.excel.information.XlsxCon;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by SappiKaran on 13/04/17.
 */

public class XlsDataBaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "XlsManager.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase database;
    private static final String CREATE_TABLE = "CREATE TABLE " + XLsDbSchema.ListOfTable.NAME + "("
            + XLsDbSchema.ListOfTable.Columns.ID + " INTEGER PRIMARY KEY, "
            + XLsDbSchema.ListOfTable.Columns.PRODUCT + " TEXT,"
            + XLsDbSchema.ListOfTable.Columns.QUANTITY + " INTEGER,"
            + XLsDbSchema.ListOfTable.Columns.TYPE + " TEXT,"
            + XLsDbSchema.ListOfTable.Columns.PRICE + " INTEGER)";
    private String TAG = "XlsDataBaseHandler";

    public XlsDataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public XlsDataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + XLsDbSchema.ListOfTable.NAME);
        // Create tables again
        onCreate(db);
    }

    public void dropTableIfExists() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(XLsDbSchema.ListOfTable.NAME, null, null);
    }

    public ArrayList<HashMap<String, String>> getAllProducts() {

        ArrayList<HashMap<String, String>> proList;

        proList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT  * FROM " + XLsDbSchema.ListOfTable.NAME;

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {


                HashMap<String, String> map = new HashMap<String, String>();

                map.put(XLsDbSchema.ListOfTable.Columns.ID, cursor.getString(0));

                map.put(XLsDbSchema.ListOfTable.Columns.PRODUCT, cursor.getString(1));

                map.put(XLsDbSchema.ListOfTable.Columns.QUANTITY, cursor.getString(2));

                map.put(XLsDbSchema.ListOfTable.Columns.TYPE, cursor.getString(3));
                map.put(XLsDbSchema.ListOfTable.Columns.PRICE, cursor.getString(4));
                proList.add(map);

            } while (cursor.moveToNext());

        }


        return proList;

    }

    public static void insertExcelToSqlite(XlsxCon dbAdapter, Sheet sheet) {

        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); ) {

            Row row = rit.next();

            ContentValues contentValues = new ContentValues();

            row.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);

            row.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);

            row.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);

            row.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);


            contentValues.put(XLsDbSchema.ListOfTable.Columns.PRODUCT, row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            contentValues.put(XLsDbSchema.ListOfTable.Columns.QUANTITY, row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            contentValues.put(XLsDbSchema.ListOfTable.Columns.TYPE, row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            contentValues.put(XLsDbSchema.ListOfTable.Columns.PRICE, row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            try {

                if (dbAdapter.insert(XLsDbSchema.ListOfTable.NAME, contentValues) < 0) {

                    return;

                }

            } catch (Exception ex) {

                Log.d("Exception in importing", ex.getMessage().toString());

            }

        }


    }
}
