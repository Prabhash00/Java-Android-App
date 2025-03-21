package com.prd.moviesaffinity.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.prd.moviesaffinity.R;
import com.prd.moviesaffinity.ui.main.MainActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int SPLASH_TIME_OUT = 2500;
        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);

            finish();
        }, SPLASH_TIME_OUT);
    }
}

