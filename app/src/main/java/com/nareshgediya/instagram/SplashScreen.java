package com.nareshgediya.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    ImageView black;
    LottieAnimationView insta, loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        black = findViewById(R.id.imageBlack);
        insta = findViewById(R.id.insta);
        loading = findViewById(R.id.loading);

        black.setTranslationY(-1000);
        insta.setTranslationY(600);

        loading.setAlpha(0f);

        insta.animate().translationY(0).setDuration(3500);
        black.animate().translationY(200).setDuration(2000);

        loading.animate().alpha(1f).setDuration(1000).setStartDelay(2000);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }
        },4500);


    }
}