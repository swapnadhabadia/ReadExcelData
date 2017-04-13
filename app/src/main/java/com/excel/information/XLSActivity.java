package com.excel.information;

import android.content.ContentValues;
import android.content.res.AssetManager;
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
import com.excel.information.database.XlsDataBaseHandler;
import com.excel.information.listadapter.ListDataAdapter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SappiKaran on 13/04/17.
 */

public class XLSActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    @BindView(R.id.infoList)
    ListView infoListView;
    @BindView(R.id.backButton)
    Button backButton;
    private ExcelDataBaseHandler controller;
    private HSSFWorkbook wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        ButterKnife.bind(this);

            AssetManager am = this.getAssets();

            InputStream inStream;

           

            try {

                inStream = am.open("Ecommerce.xls");

                wb = new HSSFWorkbook(inStream);

                inStream.close();

            } catch (IOException e) {

                e.printStackTrace();
            }

            XlsxCon dbAdapter = new XlsxCon(this);

        Sheet sheet1 = wb.getSheetAt(0);

        if (sheet1 == null) {

            return;

        }


        dbAdapter.open();

        dbAdapter.delete();

        dbAdapter.close();

        dbAdapter.open();

        XlsDataBaseHandler.insertExcelToSqlite(dbAdapter, sheet1);

        dbAdapter.close();


        ListDataAdapter listImagesBaseAdapter = new ListDataAdapter(XLSActivity.this);
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
