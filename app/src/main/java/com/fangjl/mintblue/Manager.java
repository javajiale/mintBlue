package com.fangjl.mintblue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fangjl.mintblue.slide.RightTouch;

import java.util.ArrayList;
import java.util.List;

public class Manager extends RightTouch {

    private Button add = null;
    private Button del = null;
    private ListView table = null;

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        add = (Button) findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       tankuang();
                                   }
                               }
        );

        del = (Button)findViewById(R.id.del_button);
        del.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                tankuang2();
            }
        });

        update();


        RelativeLayout Manager = (RelativeLayout)findViewById(R.id.manager);
        Manager.setOnTouchListener(this);

    }
    private void update(){
        table = (ListView)findViewById(R.id.listView);
        table.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getData()));
    }

    private List<String> getData(){
        List<String> data = new ArrayList<String>();

        DBHelper dbHelper = new DBHelper(this,"dic.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("sqlite_master",new String[] {"name"},"type='table'",null,null,null,null);

        while(cursor.moveToNext()) {
            String table = cursor.getString(0);
            if(table.equals("android_metadata")||table.equals("sqlite_sequence")||table.equals("dicInfo"))
                continue;
            data.add(table);
        }
        db.close();
        return data;
    }

    private void createTable(String name){
        DBHelper dbHelper = new DBHelper(this,"dic.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql  = "create table "+name+"(word varchar(128),explain varchar(500),time datetime)";
        db.execSQL(sql);
    }

    private void tankuang(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("请输入");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        final EditText e = new EditText(this);
        builder.setView(e);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = e.getText().toString();
                createTable(name);
                update();
            }

        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void tankuang2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(getTableName(), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lw = ((AlertDialog) dialog).getListView();
                // which表示点击的条目
                Object checkedItem = lw.getAdapter().getItem(which);
                // 既然你没有cancel或者ok按钮，所以需要在点击item后使dialog消失
                dialog.dismiss();
                // 更新你的view
                delMintBlue((String) checkedItem);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void delMintBlue(String table_name){
        DBHelper dbHelper = new DBHelper(this,"dic.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "drop table "+table_name;
        db.execSQL(sql);
        update();
    }
    private String[] getTableName(){

        List<String> data = new ArrayList<String>();

        DBHelper dbHelper = new DBHelper(this,"dic.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("sqlite_master",new String[] {"name"},"type='table'",null,null,null,null);

        while(cursor.moveToNext()) {
            String table = cursor.getString(0);
            if(table.equals("android_metadata")||table.equals("sqlite_sequence")||table.equals("dicInfo"))
                continue;
            data.add(table);
        }
        db.close();

        String[] result = new String[data.size()];
        for(int i = 0;i<data.size();i++){
            result[i] = data.get(i);
        }
        return result;

    }
}
