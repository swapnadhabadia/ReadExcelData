package com.excel.information.listadapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.excel.information.MainActivity;
import com.excel.information.R;
import com.excel.information.database.ExcelDataBaseHandler;
import com.excel.information.database.ExcelDbSchema;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SappiKaran on 13/04/17.
 */

public class ListDataAdapter extends BaseAdapter {

    private ExcelDataBaseHandler controller;
    private ArrayList<HashMap<String, String>> infoList;
    MainActivity context;
     HashMap<String, String> listOfmap;

    public ListDataAdapter(MainActivity context) {
        this.context=context;
        controller = new ExcelDataBaseHandler(context);
        this.infoList =controller.getAllProducts();;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_items, null);
            holder = new ViewHolder();

            holder.productname=(TextView)convertView.findViewById(R.id.name);
            holder.quantity=(TextView)convertView.findViewById(R.id.quantity);
            holder.type=(TextView)convertView.findViewById(R.id.type);
            holder.id=(TextView)convertView.findViewById(R.id.ids);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        listOfmap=infoList.get(position);

        if(position==0)
        {
            holder.productname.setTypeface(null,Typeface.BOLD);
            holder.quantity.setTypeface(null,Typeface.BOLD);
            holder.type.setTypeface(null,Typeface.BOLD);
            holder.id.setText("");
        }
        else
        {
            holder.id.setText(String.valueOf(position));
            holder.productname.setTypeface(null,Typeface.NORMAL);
            holder.quantity.setTypeface(null,Typeface.NORMAL);
            holder.type.setTypeface(null,Typeface.NORMAL);
        }

        holder.productname.setText(listOfmap.get(ExcelDbSchema.ListOfTable.Columns.PRODUCT));
        holder.quantity.setText(listOfmap.get(ExcelDbSchema.ListOfTable.Columns.QUANTITY));
        holder.type.setText(listOfmap.get(ExcelDbSchema.ListOfTable.Columns.TYPE));

        return convertView;
    }

    private class ViewHolder {
        TextView type;
        TextView quantity;
        TextView productname;
        TextView id;
    }
}
