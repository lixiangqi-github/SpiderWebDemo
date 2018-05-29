package com.mozre.spider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpiderWeb spiderWeb = findViewById(R.id.main_spider_web);
        List<SpiderData> data = new ArrayList<>();
        data.add(new SpiderData("KDA", 0.75f));
        data.add(new SpiderData("生存", 0.65f));
        data.add(new SpiderData("输出", 0.84f));
        data.add(new SpiderData("团战", 0.93f));
        data.add(new SpiderData("发育", 0.77f));
        data.add(new SpiderData("熟练度", 0.80f));
        spiderWeb.updateData(data);
    }
}
