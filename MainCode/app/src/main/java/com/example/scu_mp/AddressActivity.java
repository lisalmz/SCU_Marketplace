package com.example.scu_mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.scu_mp.models.AddressData;
import com.example.scu_mp.ui.profile.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.UUID;

public class AddressActivity extends AppCompatActivity {

    AppCompatButton addaddress;
    ImageButton backarrow;
    EditText fullname,phonenumber,homeaddress,city,pincode,state,country;
    DatabaseReference addressdbref;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address);
        addaddress=findViewById(R.id.addaddress);
        backarrow=findViewById(R.id.backarrow);
        fullname=findViewById(R.id.fullname);
        phonenumber=findViewById(R.id.phonenumber);
        homeaddress=findViewById(R.id.homeaddress);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        pincode=findViewById(R.id.pincode);
        country=findViewById(R.id.country);
        listView=findViewById(R.id.listview);

        FirebaseAuth.getInstance().getCurrentUser().getUid();
        addressdbref= FirebaseDatabase.getInstance().getReference().child("Address");

        addaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAddressData();
                //fullname.setText("");
                //phonenumber.setText("");
                //country.setText("");
                //homeaddress.setText("");
                //pincode.setText("");
                //country.setText("");
                //state.setText("");
                //city.setText("");


                /*AddressData addressData = new AddressData(homeaddress.getText().toString(), fullname.getText().toString());
                addressdbref.setValue("addressData").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AddressActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(AddressActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddressActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }); */
            }

        });




        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //Intent intent=new Intent(AddressActivity.this, NavigationDrawer.class);
                //startActivity(intent);
            }
        });

        //Retrival of address data

        /*ArrayList<String> list=new ArrayList<>();
        ArrayAdapter adapter= new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Address");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    AddressData addressData1=snapshot.getValue(AddressData.class);
                    String txt=addressData1.getFirstName()+","+addressData1.getPhonenumber()+","+addressData1.getAddress();
                    //list.add(snapshot.getValue().toString());
                    list.add(txt);
                }
                adapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        //end retrival

    }

    private void insertAddressData() {
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
        }
        else{
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            //String id = System.currentTimeMillis()+ "_" + UUID.randomUUID().toString();
            AddressData addressData=new AddressData(firstname,phonenumber1,address,cityname,pincodeno,statename,country1,uid);
            addressdbref.child(uid).setValue(addressData);
            Toast.makeText(AddressActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
            finish();

        }


    }


}