package com.example.scu_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class sell2 extends AppCompatActivity {


    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sell2);


        listView=(ListView) findViewById(R.id.listview);
        ArrayList<String> arrayList=new ArrayList<>();

        arrayList.add("Allow all the time");
        arrayList.add("Allow only while using the app");
        arrayList.add("Deny & don't ask again");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter)
        ;
    }
}