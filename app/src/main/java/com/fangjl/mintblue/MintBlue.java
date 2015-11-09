package com.fangjl.mintblue;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MintBlue extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));
        setContentView(listView);


       // setContentView(R.layout.activity_mintblue);
    }

    private List<String> getData(){

        List<String> data = new ArrayList<String>();

        DBHelper dbHelper = new DBHelper(MintBlue.this,"dic.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("newword",new String[] {"word"},null,null,null,null,null);

                while(cursor.moveToNext()) {
                    String name = cursor.getString(0);
                    data.add(name);
                }


//        data.add("单词1");
//        data.add("单词2");
//        data.add("单词3");
//        data.add("单词4");
        db.close();
        return data;
    }
}
