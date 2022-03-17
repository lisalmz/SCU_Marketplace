package com.example.scu_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.scu_mp.ui.sell.SellFragment;

public class Splash extends AppCompatActivity {

    ImageView logo;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        lottieAnimationView = findViewById(R.id.splash);

        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, SellActivity1.class);
                startActivity(intent);
                finish();
            }
        },6500);

    }
}