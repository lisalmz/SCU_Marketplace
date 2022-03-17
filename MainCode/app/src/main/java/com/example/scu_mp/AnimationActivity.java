package com.example.scu_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.scu_mp.ui.sell.SellFragment;

public class AnimationActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        b=(Button) findViewById(R.id.btnsuccessful);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(AnimationActivity.this,R.anim.rotate);

                b.startAnimation(animation);
            }
        });





        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AnimationActivity.this, SellFragment.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);*/


    }
}