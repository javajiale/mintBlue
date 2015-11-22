package com.fangjl.mintblue;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.RelativeLayout;

public class Recite extends RightTouch {
    @Override
       protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);

        RelativeLayout recite = (RelativeLayout)findViewById(R.id.recite);
        recite.setOnTouchListener(this);
    }
}
