package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreens extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreens.this)
                .withFullScreen()
                .withTargetActivity(categories.class)
                .withSplashTimeOut(5000)
                .withBackgroundColor(Color.parseColor("#3CB371"))
                .withLogo(R.drawable.logo);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }


}