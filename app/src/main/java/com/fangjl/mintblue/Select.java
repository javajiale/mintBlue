package com.fangjl.mintblue;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;


public class Select extends RightTouch {
    private Button mReciteButton;
    private Button mExiconButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        mReciteButton = (Button)findViewById(R.id.cet4_button);
        mReciteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Select.this, Recite.class);
                startActivity(i);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        mExiconButton = (Button)findViewById(R.id.lexicon_button);
        mExiconButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Select.this, Lexicon.class);
                startActivity(i);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        RelativeLayout select = (RelativeLayout)findViewById(R.id.select);
        select.setOnTouchListener(this);
    }

}
