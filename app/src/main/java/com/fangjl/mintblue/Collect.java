package com.fangjl.mintblue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Collect extends AppCompatActivity {

    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));
        setContentView(listView);

    }

    private List<String> getData(){

        List<String> data = new ArrayList<String>();
        data.add("收藏1");
        data.add("收藏2");
        data.add("收藏3");
        data.add("收藏4");

        return data;
    }
}
