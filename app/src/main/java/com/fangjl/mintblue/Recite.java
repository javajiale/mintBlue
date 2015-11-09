package com.fangjl.mintblue;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class Recite extends AppCompatActivity {
    @Override
       protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);
    }
}
