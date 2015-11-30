package com.fangjl.mintblue;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fangjl.mintblue.slide.RightTouch;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Translate extends RightTouch {

    private Button mFanyiButton;
    private EditText mYuanwenText;
    private TextView mYiwenText;

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
        setContentView(R.layout.activity_translate);
        init();

        RelativeLayout translate = (RelativeLayout)findViewById(R.id.translate);
        translate.setOnTouchListener(this);
    }
    private void init(){
        mFanyiButton = (Button)findViewById(R.id.fanyi_button);
        mFanyiButton.setOnClickListener(new fanyiListener()) ;
        mYuanwenText = (EditText)findViewById(R.id.yuanwen_text);
        mYiwenText = (TextView)findViewById(R.id.yiwen_text);
    }

    private class fanyiListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String YouDaoSearchContent = null;
            try {
                 YouDaoSearchContent = java.net.URLEncoder.encode(mYuanwenText.getText().toString().trim(),   "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String YouDaoUrl = YouDaoBaseUrl + "?keyfrom=" + YouDaoKeyFrom
                    + "&key=" + YouDaoKey + "&type=" + YouDaoType + "&doctype="
                    + YouDaoDoctype + "&version="
                    + YouDaoVersion + "&q=" + YouDaoSearchContent;

            myTask = new MyTask();
            myTask.execute(YouDaoUrl);
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
            mYiwenText.setText(result);
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
