package com.excel.information;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.excel.information.database.ExcelDataBaseHandler;
import com.excel.information.database.ExcelDbSchema;
import com.excel.information.listadapter.ListDataAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SappiKaran on 13/04/17.
 */

public class CSVActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    
     @BindView(R.id.infoList)
     ListView infoListView;
    @BindView(R.id.backButton)
    Button backButton;
    private ExcelDataBaseHandler controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        ButterKnife.bind(this);


        try {

            InputStreamReader is = new InputStreamReader(getAssets()
                    .open("Ecommerce.csv"));
            controller = new ExcelDataBaseHandler(getApplicationContext());

            SQLiteDatabase db = controller.getWritableDatabase();

            db.execSQL("DELETE FROM " + ExcelDbSchema.ListOfTable.NAME+" ; ");

            BufferedReader buffer = new BufferedReader(is);

            ContentValues contentValues = new ContentValues();

            String line = "";

            db.beginTransaction();

            while ((line = buffer.readLine()) != null) {

                String[] str = line.split(",", 3);

                String product = str[0].toString();

                String quantity = str[1].toString();

                String type = str[2].toString();

                contentValues.put(ExcelDbSchema.ListOfTable.Columns.PRODUCT, product);

                contentValues.put(ExcelDbSchema.ListOfTable.Columns.QUANTITY, quantity);

                contentValues.put(ExcelDbSchema.ListOfTable.Columns.TYPE, type);

                db.insert(ExcelDbSchema.ListOfTable.NAME, null, contentValues);

            }

            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (IOException e) {
            e.printStackTrace();
        }


        ListDataAdapter listImagesBaseAdapter = new ListDataAdapter(CSVActivity.this);
        infoListView.setAdapter(listImagesBaseAdapter);
        infoListView.setOnItemClickListener(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        
    }
}
