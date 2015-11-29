package com.fangjl.mintblue.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangjl.mintblue.LeftTouch;
import com.fangjl.mintblue.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class News extends LeftTouch {
    private TextView content;
    private TextView dateline;
    private TextView note;
 //   private TextView translation;
    private ImageView picture;
    private Bitmap bmImg;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        RelativeLayout news = (RelativeLayout) findViewById(R.id.news);
        news.setOnTouchListener(this);

        content = (TextView) findViewById(R.id.content);
        dateline = (TextView) findViewById(R.id.dateline);
        note = (TextView) findViewById(R.id.note);
    //    translation = (TextView) findViewById(R.id.translation);
        picture = (ImageView) findViewById(R.id.picture);

        MyTask mTask = new MyTask();
        mTask.execute();
    }

    private class MyTask extends AsyncTask<String, Integer, EverydayInfo> {
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected EverydayInfo doInBackground(String... params) {
            EverydayInfo e = EverydaySen.request();
            try {
                URL myFileUrl = new URL( e.getPicture());
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);
                is.close();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return e;
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... progresses) {
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(EverydayInfo result) {
            if(result!=null) {
                dateline.setText(result.getDateline());
                content.setText(result.getContent());
                note.setText(result.getNote());
        //        translation.setText(result.getTranslation());

                picture.setImageBitmap(bmImg);
            }
        }
        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {

        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news);
//
//        RelativeLayout news = (RelativeLayout) findViewById(R.id.news);
//        news.setOnTouchListener(this);
//
//    }




}
