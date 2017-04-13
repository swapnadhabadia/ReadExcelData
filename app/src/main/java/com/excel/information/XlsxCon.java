package com.excel.information;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.excel.information.database.XLsDbSchema;
import com.excel.information.database.XlsDataBaseHandler;

/**
 * Created by SappiKaran on 13/04/17.
 */

public class XlsxCon {

    private SQLiteDatabase db;
    private XlsDataBaseHandler dbHelper;


    public XlsxCon(Context context) {
        dbHelper = new XlsDataBaseHandler(context);
    }

    public void open() {

        if (null == db || !db.isOpen()) {

            try {

                db = dbHelper.getWritableDatabase();

            } catch (SQLiteException sqLiteException) {

            }

        }

    }



    public void close() {

        if (db != null) {

            db.close();

        }

    }



    public int insert(String table, ContentValues values) {

        return (int) db.insert(table, null, values);

    }



    public void delete() {

        db.execSQL("delete from " + XLsDbSchema.ListOfTable.NAME );

    }



    public Cursor getAllRow(String table) {

        return db.query(table, null, null, null, null, null, XLsDbSchema.ListOfTable.Columns.ID);

    }
}


