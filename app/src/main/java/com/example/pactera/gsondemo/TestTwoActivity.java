package com.example.pactera.gsondemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TestTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_two);

        /*
        *
        * 1.去掉进场
          Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
           2.去掉出场动画
            在需要结束的activity中重写finish方法
           @Override
           public void finish() {
               super.finish();
               overridePendingTransition(0, 0);
             }
        * */
        findViewById(R.id.btn_result_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("data","successful");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
