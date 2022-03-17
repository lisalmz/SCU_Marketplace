package com.example.scu_mp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scu_mp.ui.home.HomeFragment;
import com.example.scu_mp.ui.like.LikeFragment;

public class OrderSuccesfullpageActivity extends AppCompatActivity {

    AppCompatButton okbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_succesfullpage);
        okbutton=findViewById(R.id.okbutton);
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(OrderSuccesfullpageActivity.this, NavigationDrawer.class);
                startActivity(intent1);
            }
        });

    }
}