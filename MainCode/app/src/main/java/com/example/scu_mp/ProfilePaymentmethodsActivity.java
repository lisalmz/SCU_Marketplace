package com.example.scu_mp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.scu_mp.ui.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class ProfilePaymentmethodsActivity extends AppCompatActivity {

    //Button remove;
    //RecyclerView recyclerView2;
    AppCompatButton addcard;
    ImageButton backarrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_paymentmethods);
        //remove=findViewById(R.id.remove);
        addcard = findViewById(R.id.addcard);
        backarrow = findViewById(R.id.backarrow);


        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //Intent intent=new Intent(ProfilePaymentmethodsActivity.this, NavigationDrawer.class);
                //startActivity(intent);

            }
        });


        addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //Intent intent=new Intent(ProfilePaymentmethodsActivity.this, NavigationDrawer.class);
                //startActivity(intent);
            }
        });

       /* remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ProfilePaymentmethodsActivity.this)
                        .setTitle("Remove Card")
                        .setMessage("")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();

            }
        });
        ArrayList<Card> arrayList=new ArrayList<>();
        recyclerView2=findViewById(R.id.recyclerview);
        RecyclerViewAdapterCard recyclerviewAdapter= new RecyclerViewAdapterCard(arrayList);

        recyclerView2.setAdapter(recyclerviewAdapter);

    }*/
    }
}