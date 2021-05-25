package com.alex.rickandmorty.utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.rickandmorty.MainActivity;
import com.alex.rickandmorty.R;
import com.wang.avi.AVLoadingIndicatorView;


public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    AVLoadingIndicatorView avi;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        avi = findViewById(R.id.avi);
        avi.show();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                avi.hide();
                finish();


            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}