package com.example.trackme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.trackme.auth.IntroActivity;

public class MainActivity extends AppCompatActivity {

    private int waktu_load = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(splash);
                finish();
            }
        }, waktu_load);
    }
}