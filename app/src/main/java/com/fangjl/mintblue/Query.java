package com.fangjl.mintblue;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fangjl.mintblue.slide.LeftTouch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Query extends LeftTouch {

    private EditText edit = null;
    private Button search = null;
    private TextView text = null;
    private Button join = null;

    private String YouDaoBaseUrl = "http://fanyi.youdao.com/openapi.do";
    private String YouDaoKeyFrom = "zuccSignIn";
    private String YouDaoKey = "1660829821";
    private String YouDaoType = "data";
    private String YouDaoDoctype = "json";
    private String YouDaoVersion = "1.1";

    private MyTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        init();

        RelativeLayout query = (RelativeLayout) findViewById(R.id.query);
        query.setOnTouchListener(this);
    }

    private void init(){
        edit = (EditText)findViewById(R.id.edit);
        search = (Button)findViewById(R.id.search);
        search.setOnClickListener(new searchListener());
        text = (TextView) findViewById(R.id.text);
        join = (Button)findViewById(R.id.join_button);
        join.setOnClickListener(new joinListener());
    }

    private class joinListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
                select();

        }
    }

    private void select(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(getTableName(), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lw = ((AlertDialog) dialog).getListView();
                // which表示点击的条目
                Object checkedItem = lw.getAdapter().getItem(which);
                // 既然你没有cancel或者ok按钮，所以需要在点击item后使dialog消失
                dialog.dismiss();
                // 更新你的view
                join((String)checkedItem);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String[] getTableName(){

        List<String> data = new ArrayList<String>();

        DBHelper dbHelper = new DBHelper(this,"dic.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("sqlite_master",new String[] {"name"},"type='table'",null,null,null,null);

        while(cursor.moveToNext()) {
            String table = cursor.getString(0);
            if(table.equals("android_metadata")||table.equals("sqlite_sequence")||table.equals("dicInfo"))
                continue;
            data.add(table);
        }
        db.close();

        String[] result = new String[data.size()];
        for(int i = 0;i<data.size();i++){
            result[i] = data.get(i);
        }
        return result;

    }

    private void join(String name){
        DBHelper dbHelper = new DBHelper(Query.this,"dic.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteDatabase dd = dbHelper.getReadableDatabase();

        ContentValues cValue = new ContentValues();  //先只加入一个名字
        String line = text.getText().toString();
        int j = 0;
        for(int i = 0;i<line.length();i++)
            if(line.charAt(i)=='[') {
                j = i - 1;
                break;
            }
        cValue.put("word", line.substring(7, j));
        Cursor cursor = dd.query("dicInfo",new String[] {"explain"},"word = "+"'"+line.substring(7,j)+"'",null,null,null,null);
        cursor.moveToNext();
        cValue.put("explain", cursor.getString(0));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cValue.put("time", df.format(new Date()));

        db.insert(name, null, cValue);
        db.close();
        dd.close();
        Toast toast = Toast.makeText(Query.this, "加入成功!", Toast.LENGTH_SHORT);
        toast.show();
    }

    private class searchListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String YouDaoSearchContent = edit.getText().toString().trim();
            String YouDaoUrl = YouDaoBaseUrl + "?keyfrom=" + YouDaoKeyFrom
                    + "&key=" + YouDaoKey + "&type=" + YouDaoType + "&doctype="
                    + YouDaoDoctype + "&version="
                    + YouDaoVersion + "&q=" + YouDaoSearchContent;

            myTask = new MyTask();
            myTask.execute(YouDaoUrl);
//            try {
//                AnalyzingOfJson(YouDaoUrl);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    private class MyTask extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... params) {
            String message = null;
            try {
                message =  AnalyzingOfJson(params[0]);
            } catch (Exception e) {
              e.printStackTrace();
            }
            return message;
        }
        protected void onPostExecute(String result) {
            text.setText("\n\n\n\n\n\n\n"+result);
        }

        }

    private String AnalyzingOfJson(String url) throws Exception {
        // 第一步，创建HttpGet对象
        HttpGet httpGet = new HttpGet(url);
        // 第二步，使用execute方法发送HTTP GET请求，并返回HttpResponse对象
        HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);

        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            // 第三步，使用getEntity方法活得返回结果
            String result = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("result:" + result);
            JSONArray jsonArray = new JSONArray("[" + result + "]");
            String message = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null) {
                    String errorCode = jsonObject.getString("errorCode");
                    if (errorCode.equals("20")) {
                        Toast.makeText(getApplicationContext(), "要翻译的文本过长",
                                Toast.LENGTH_SHORT);
                    } else if (errorCode.equals("30 ")) {
                        Toast.makeText(getApplicationContext(), "无法进行有效的翻译",
                                Toast.LENGTH_SHORT);
                    } else if (errorCode.equals("40")) {
                        Toast.makeText(getApplicationContext(), "不支持的语言类型",
                                Toast.LENGTH_SHORT);
                    } else if (errorCode.equals("50")) {
                        Toast.makeText(getApplicationContext(), "无效的key",
                                Toast.LENGTH_SHORT);
                    } else {
                        // 要翻译的内容
                        String query = jsonObject.getString("query");
                        message = query;
                        // 翻译内容
                        String translation = jsonObject
                                .getString("translation");
                        message += "\t" + translation;
                        // 有道词典-基本词典
                        if (jsonObject.has("basic")) {
                            JSONObject basic = jsonObject
                                    .getJSONObject("basic");
                            if (basic.has("phonetic")) {
                                String phonetic = basic.getString("phonetic");
                                message += "\n\t" + phonetic;
                            }
                            if (basic.has("explains")) {
                                String explains = basic.getString("explains");
                                message += "\n\t" + explains;
                            }
                        }
                        // 有道词典-网络释义
                        if (jsonObject.has("web")) {
                            String web = jsonObject.getString("web");
                            JSONArray webString = new JSONArray("[" + web + "]");
                            message += "\n网络释义：";
                            JSONArray webArray = webString.getJSONArray(0);
                            int count = 0;
                            while (!webArray.isNull(count)) {

                                if (webArray.getJSONObject(count).has("key")) {
                                    String key = webArray.getJSONObject(count)
                                            .getString("key");
                                    message += "\n\t<" + (count + 1) + ">"
                                            + key;
                                }
                                if (webArray.getJSONObject(count).has("value")) {
                                    String value = webArray
                                            .getJSONObject(count).getString(
                                                    "value");
                                    message += "\n\t   " + value;
                                }
                                count++;
                            }
                        }
                    }
                }
            }
            return message;
        } else {
            Toast.makeText(getApplicationContext(), "提取异常", Toast.LENGTH_SHORT);
        }
        return  null;

    }
}
