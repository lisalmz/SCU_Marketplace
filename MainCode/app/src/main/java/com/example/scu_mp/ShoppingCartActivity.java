package com.example.scu_mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scu_mp.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ShoppingCartActivity extends AppCompatActivity {

    Button placeorder;
    RecyclerView recyclerView1;
    ImageButton backarrow;
    DatabaseReference cartref;
    private  Recyclerviewadaptercart adapter;
    //Button addCart;
    private  ArrayList<Blog> cart;
    TextView totalprice;
    private double totalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);
        placeorder=findViewById(R.id.placeorder);
        backarrow=findViewById(R.id.backarrow);
        totalprice=findViewById(R.id.totalprice);
        //addCart=findViewById(R.id.addCart);




        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(ShoppingCartActivity.this,NavigationDrawer.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();

                //Intent intent=new Intent(ShoppingCartActivity.this, NavigationDrawer.class);
                //startActivity(intent);
            }
        });
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ShoppingCartActivity.this,CheckoutAddressPageActivity.class);
                startActivity(intent);
            }
        });
        ArrayList<Cart> arrayList=new ArrayList<>();
        recyclerView1=findViewById(R.id.recyclerview1);
        getCartProducts();

    }

    public void getCartProducts(){
        cartref= FirebaseDatabase.getInstance().getReference().child("Cart");
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        cartref.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<Blog> productList =  new ArrayList<>();
                totalPrice =0.0;
                if(task.isSuccessful()){
                    task.getResult().getChildren().forEach(new Consumer<DataSnapshot>() {
                        @Override
                        public void accept(DataSnapshot dataSnapshot) {
                            Blog product = dataSnapshot.getValue(Blog.class);
                            productList.add(product);

                            try {
                               totalPrice+=Double.parseDouble(product.getPrice());
                            }catch (Exception ex){

                            }
                            totalprice.setText("Total: $"+totalPrice);


                        }
                    });
                    Recyclerviewadaptercart recyclerviewAdapter= new Recyclerviewadaptercart(productList);

                    recyclerView1.setAdapter(recyclerviewAdapter);
                }
            }
        });

    }
}