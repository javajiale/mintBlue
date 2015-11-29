package com.fangjl.mintblue;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class WelComeActivity extends Activity{

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.welcome);
        Start();
    }
    public void Start(){
        new Thread(){
            public void run() {
                try{
                    Thread.sleep(2500);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.setClass(WelComeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }

}
