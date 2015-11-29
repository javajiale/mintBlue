package com.fangjl.mintblue;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Recite extends RightTouch {

    private TextView danci_textview;
    private TextView tf_text;
    private CheckBox ch1;
    private CheckBox ch2;
    private CheckBox ch3;
    private CheckBox ch4;
    private Button next;

    private String answer;
    private String[] select = new String[4];

    @Override
       protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);
        RelativeLayout recite = (RelativeLayout)findViewById(R.id.recite);
        recite.setOnTouchListener(this);


        init();
//        ch1 = (CheckBox)findViewById(R.id.ch1);
//        ch1.setText("ch1");

    }

    private void init(){

        tf_text = (TextView)findViewById(R.id.tf_text);
        danci_textview = (TextView)findViewById(R.id.danci_textview);
        ch1 = (CheckBox)findViewById(R.id.ch1);
        ch2 = (CheckBox)findViewById(R.id.ch2);
        ch3 = (CheckBox)findViewById(R.id.ch3);
        ch4 = (CheckBox)findViewById(R.id.ch4);
        next = (Button)findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        List<Danci> listDanci = new ArrayList<Danci>();
        listDanci = getDanci();
        danci_textview.setText(listDanci.get(0).danci); // set单词进testview
        answer = listDanci.get(0).yisi; // 意思进answer

        int x = new Random().nextInt(4);
        ch1.setText(listDanci.get(x).yisi);
        ch1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ch1.getText().equals(answer)) {
                    tf_text.setText("true");
                } else {
                    tf_text.setText("false");
                }
            }
        });
        listDanci.remove(x);
        x = new Random().nextInt(3);
        ch2.setText(listDanci.get(x).yisi);
        ch2.setOnClickListener(new CheckBox.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ch2.getText().equals(answer)) {
                    tf_text.setText("true");
                } else {
                    tf_text.setText("false");
                }
            }
        });
        listDanci.remove(x);
        x = new Random().nextInt(2);
        ch3.setText(listDanci.get(x).yisi);
        ch3.setOnClickListener(new CheckBox.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ch3.getText().equals(answer)) {
                    tf_text.setText("true");
                } else {
                    tf_text.setText("false");
                }
            }
        });
        listDanci.remove(x);
        ch4.setText(listDanci.get(0).yisi);
        ch4.setOnClickListener(new CheckBox.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ch4.getText().equals(answer)) {
                    tf_text.setText("true");
                } else {
                    tf_text.setText("false");
                }
            }
        });

    }

    private List<Danci> getDanci(){
        List<Danci> result = new ArrayList<Danci>();
        DBHelper dbHelper = new DBHelper(this,"dic.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        for(int i = 0; i<4; i++) {
            int sid = new Random().nextInt(45096);
            Cursor cursor = db.query("dicInfo", new String[]{"word", "explain"}, "sid=" + sid, null, null, null, null);
            cursor.moveToNext();
            Danci danci = new Danci();
            danci.danci = cursor.getString(0);
            danci.yisi = cursor.getString(1);
            result.add(danci);
        }
        db.close();
        return result;
    }


    class Danci{
        String danci;
        String yisi;
    }

    private void refresh() {
        finish();
        Intent intent = new Intent(Recite.this, Recite.class);
        startActivity(intent);
    }


}
