package com.fangjl.mintblue;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangjl.mintblue.slide.RightTouch;

public class Lexicon  extends RightTouch {

    private Button mLastButton;
    private Button mNextButton;
    private TextView text;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lexicon);

        text = (TextView) findViewById(R.id.lexicon_text);

        DBHelper dbHelper = new DBHelper(Lexicon.this,"dic.db",null,1);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        final Cursor cursor = db.query("dicInfo",new String[] {"word,explain"},null,null,null,null,null);

        if(cursor.moveToFirst()){
                cursor.move(i);
                String word = cursor.getString(0);
                String explain = cursor.getString(1);
                String result = word+"\n"+explain;
                text.setText(result);
        } else {

        }

        mLastButton = (Button)findViewById(R.id.last_button);
        mLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query("last",cursor);
            }
        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query("next",cursor);
            }
        });


        RelativeLayout lexion = (RelativeLayout)findViewById(R.id.lexicon);
        lexion.setOnTouchListener(this);


    }
    private void query(String type,Cursor cursor){
        if(type == "next"){
            i++;
        } else {
            i--;
        }
        if(cursor.moveToFirst()){
            cursor.move(i);
            String word = cursor.getString(0);
            String explain = cursor.getString(1);
            String result = word+"\n  "+explain;
            text.setText(result);
        } else {

        }


    }
}
