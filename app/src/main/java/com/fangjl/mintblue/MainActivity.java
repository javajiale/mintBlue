package com.fangjl.mintblue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private ImageButton mQuerylocalButton;
    private ImageButton mTranslateButton;
    private ImageButton mNewsButton;
    private ImageButton mReciteButton;
    private ImageButton mMintBlueButton;
    private ImageButton mCollectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        DBHelper helper=new DBHelper(MainActivity.this,"dic.db",null,1);
//        try {
//            helper.deleteDatabase(MainActivity.this);
//            helper.createDatabase(MainActivity.this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuerylocalButton = (ImageButton)findViewById(R.id.query_button);
        mQuerylocalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Query.class);
                startActivity(i);
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });

        mTranslateButton = (ImageButton)findViewById(R.id.translate_button);
        mTranslateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Translate.class);
                startActivity(i);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        mNewsButton = (ImageButton)findViewById(R.id.news_button);
        mNewsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,News.class));
                //动画
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });

        mReciteButton = (ImageButton)findViewById(R.id.itSearch_button);
        mReciteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Select.class);
                startActivity(i);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        mMintBlueButton = (ImageButton)findViewById(R.id.mintBlue_button);
        mMintBlueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MintBlue.class);
                startActivity(i);
            }
        });

        mCollectButton = (ImageButton)findViewById(R.id.collect_button);
        mCollectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Manager.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
