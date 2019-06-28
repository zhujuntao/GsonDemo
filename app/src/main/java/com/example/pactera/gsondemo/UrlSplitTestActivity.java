package com.example.pactera.gsondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class UrlSplitTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_split_test);

        String url="http://bjetc.murongtech.com/mall/st-offLineSell/jsp/payResult.jsp?issNameKey=方庄营业厅&issNoKey=110100010001&opNameKey=柳凯&opNoKey=18602693887&bankNoKey=ICBC";

        Log.d("MainActivity","=====url==rag===:"+url.split("&")[0]);
        Log.d("MainActivity","=====url==rag=issNameKey==:"+url.split("&")[0].split("=")[1]);
        Log.d("MainActivity","=====url==rag=issNoKey==:"+url.split("&")[1].split("=")[1]);
        Log.d("MainActivity","=====url==rag=opNameKey==:"+url.split("&")[2].split("=")[1]);
        Log.d("MainActivity","=====url==rag=opNoKey==:"+url.split("&")[3].split("=")[1]);
        Log.d("MainActivity","=====url==rag=bankNoKey==:"+url.split("&")[4].split("=")[1]);








    }
}
