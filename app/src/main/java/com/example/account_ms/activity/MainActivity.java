package com.example.account_ms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.account_ms.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context mContext;
        mContext = getApplicationContext();
        Toast.makeText(this, "第一次提交", Toast.LENGTH_SHORT);
    }
}