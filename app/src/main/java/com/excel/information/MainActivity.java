package com.excel.information;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ExcelDataBaseHandler controller;
    private ArrayList<HashMap<String, String>> listMap;

    @BindView(R.id.csvFileButton)
    Button csv;
    @BindView(R.id.xlsFileButton)
    Button xls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, CSVActivity.class);
                startActivity(in);
            }
        });

        xls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, XLSActivity.class);
                startActivity(in);
            }
        });


    }
}
