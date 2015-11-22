package com.fangjl.mintblue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MintBlue extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ScrollListviewDelete listviewDelete;
    private DeleteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_delete);

        select();

    }
    private void select(){
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
                findMintBlue((String)checkedItem);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private String[] getTableName(){

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

        String[] result = new String[data.size()];
        for(int i = 0;i<data.size();i++){
            result[i] = data.get(i);
        }
        return result;

    }

    private void findMintBlue(String table_name){

            listviewDelete = (ScrollListviewDelete) findViewById(android.R.id.list);
            adapter = new DeleteAdapter(this, getData(table_name));
            listviewDelete.setAdapter(adapter);
            listviewDelete.setOnItemClickListener(this);

    }

    private List<String> getData(String table_name){

        List<String> data = new ArrayList<String>();

        DBHelper dbHelper = new DBHelper(MintBlue.this,"dic.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(table_name,new String[] {"word","explain"},null,null,null,null,null);

                while(cursor.moveToNext()) {
                    String name = cursor.getString(0);
                    String explian = cursor.getString(1);
                    data.add(name+"\n"+explian);
                }

        db.close();
        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
