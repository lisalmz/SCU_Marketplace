package com.example.scu_mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.function.Consumer;

public class PaymentActivity extends AppCompatActivity {

    Button finishpayment;
    ImageButton backarrowpayment;
    //RecyclerView recyclerView;
    DatabaseReference cartref;
    DatabaseReference productref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        finishpayment=findViewById(R.id.finishpayment);
        backarrowpayment=findViewById(R.id.backarrowpayment);
        finishpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getCartProductss();

                Intent intent=new Intent(PaymentActivity.this,OrderSuccesfullpageActivity.class);
                Toast.makeText(PaymentActivity.this, "Payment Successful", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        backarrowpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //ArrayList<Card> arrayList=new ArrayList<>();
        //recyclerView=findViewById(R.id.recyclerview);
        //RecyclerViewAdapterCard recyclerviewAdapter= new RecyclerViewAdapterCard(arrayList);

        //recyclerView.setAdapter(recyclerviewAdapter);
    }

    /*private void getCartProductss() {
        cartref= FirebaseDatabase.getInstance().getReference().child("Cart");
        productref=FirebaseDatabase.getInstance().getReference().child("Product");
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        cartref.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<Blog> productList =  new ArrayList<>();

                if(task.isSuccessful()){
                    task.getResult().getChildren().forEach(new Consumer<DataSnapshot>() {
                        @Override
                        public void accept(DataSnapshot dataSnapshot) {
                            Blog product = dataSnapshot.getValue(Blog.class);
                            productList.add(product);
                            //FirebaseDatabase.getInstance().getReference().child(productref.getKey()).child("status").setValue("SOLD");

                        }

                    });

                }
            }


        });


    }
    */
}