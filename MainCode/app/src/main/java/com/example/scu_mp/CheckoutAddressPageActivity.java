package com.example.scu_mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.scu_mp.models.AddressData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CheckoutAddressPageActivity extends AppCompatActivity {

    Button continueaddress;
    ImageButton backarrowaddress;
    DatabaseReference databaseReference;
    AddressData addressData;
    CheckBox radioaddress;
    EditText fullname,phonenumber,homeaddress,city,pincode,state,country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_address_page);
        continueaddress=findViewById(R.id.continueaddress);
        backarrowaddress=findViewById(R.id.backarrowaddress);
        radioaddress=findViewById(R.id.radioaddress);
        fullname=findViewById(R.id.fullname);
        phonenumber=findViewById(R.id.phonenumber);
        homeaddress=findViewById(R.id.homeaddress);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        pincode=findViewById(R.id.pincode);
        country=findViewById(R.id.country);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Address");



        continueaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioaddress.isChecked()) {
                    Intent intent = new Intent(CheckoutAddressPageActivity.this, PaymentActivity.class);
                    startActivity(intent);
                }else{
                    String firstname =fullname.getText().toString();
                    String phonenumber1=phonenumber.getText().toString();
                    String address=homeaddress.getText().toString();
                    String cityname=city.getText().toString();
                    String pincodeno=pincode.getText().toString();
                    String statename=state.getText().toString();
                    String country1 =country.getText().toString();
                    if (firstname.isEmpty()) {
                        fullname.setError("Name is required!");
                        fullname.requestFocus();
                        return;
                    }
                    if(phonenumber1.isEmpty()){
                        phonenumber.setError("");

                    }
                    if(statename.isEmpty()){
                        state.setError("State field should not empty");
                        return;
                    }

                    if(address.isEmpty()){
                        homeaddress.setError("Address field should not empty");
                        homeaddress.requestFocus();
                        return;

                    }
                    if(cityname.isEmpty()){
                        city.setError("City field should not empty");
                        return;
                    }
                    if(pincodeno.isEmpty()){
                        pincode.setError("Pincode field should not empty");
                        return;
                    }
                    if(country1.isEmpty()){
                        country.setError("country field should not empty");
                        return;
                    }else{
                        Intent intent = new Intent(CheckoutAddressPageActivity.this, PaymentActivity.class);
                        startActivity(intent);

                    }
                }

            }
        });
        backarrowaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();


            }
        });
        getAddress();

        radioaddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fullname.setEnabled(false);
                    phonenumber.setEnabled(false);
                    homeaddress.setEnabled(false);
                    city.setEnabled(false);
                    pincode.setEnabled(false);
                    state.setEnabled(false);
                    country.setEnabled(false);

                }else {
                    fullname.setEnabled(true);
                    phonenumber.setEnabled(true);
                    homeaddress.setEnabled(true);
                    city.setEnabled(true);
                    pincode.setEnabled(true);
                    state.setEnabled(true);
                    country.setEnabled(true);
                }
            }
        });
    }
    public void getAddress(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    addressData = task.getResult().getValue(AddressData.class);
                    if(addressData!=null) {
                        radioaddress.setText(addressData.toString());
                    }

                }
            }
        });

    }
}