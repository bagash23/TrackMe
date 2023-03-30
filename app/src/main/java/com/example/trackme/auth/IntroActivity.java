package com.example.trackme.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trackme.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Button button = findViewById(R.id.register);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(IntroActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                });

        Button button2 = findViewById(R.id.masuk);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
    }

}