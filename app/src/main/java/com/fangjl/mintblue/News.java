package com.fangjl.mintblue;

import android.os.Bundle;
import android.view.Window;
import android.widget.RelativeLayout;

public class News extends LeftTouch {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        RelativeLayout news = (RelativeLayout) findViewById(R.id.news);
        news.setOnTouchListener(this);

    }




}
