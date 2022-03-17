package com.example.scu_mp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LikeActivity extends AppCompatActivity {

    ImageButton cart;
    AppCompatImageView likeheart,likeheart1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.like);
        /*cart=findViewById(R.id.cart);
        likeheart=findViewById(R.id.likeheart);
        likeheart1=findViewById(R.id.likeheart1);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LikeActivity.this,ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
        likeheart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean isseleceted=likeheart.isSelected();
                likeheart.setSelected(!isseleceted);
            }
        });

        likeheart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isseleceted=likeheart1.isSelected();
                likeheart1.setSelected(!isseleceted);
            }
        });

         */
    }
}