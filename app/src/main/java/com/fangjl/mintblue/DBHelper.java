package com.fangjl.mintblue;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION    = 1;
    private static String DB_PATH        = "/data/data/com.fangjl.mintblue/databases/";
//    private static String DB_PATH;


    private static String DB_NAME         = "dic.db";


    private final Context myContext;


    public DBHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,null,version);

//        if(android.os.Build.VERSION.SDK_INT >= 4.2){
//            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
//        } else {
//            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
//        }

        this.myContext = context;
    }

    public void onCreate(SQLiteDatabase db) {
        //建表
    }

    public  void  createDatabase(Context context) throws IOException {
        try {
            String databaseFilename = DB_PATH + DB_NAME;
            File dir = new File(DB_PATH);
            // 如果目录不存在，创建这个目录
            if (!dir.exists())
                dir.mkdir();

            //目录中不存在 .db文件，则从res\raw目录中复制这个文件到该目录
            if (!(new File(databaseFilename)).exists()) {
                // 获得封装.db文件的InputStream对象
                InputStream is = context.getResources().openRawResource(R.raw.dic);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[7168];
                int count = 0;
                // 开始复制.db文件
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (Exception e) {
        }
    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {

    }

    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase("dic.db");
    }
}
