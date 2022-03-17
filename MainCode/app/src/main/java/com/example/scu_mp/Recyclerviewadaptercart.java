package com.example.scu_mp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Recyclerviewadaptercart extends RecyclerView.Adapter<Recyclerviewadaptercart.ViewHolder> {

    ArrayList<Blog> cart;


    public Recyclerviewadaptercart(ArrayList<Blog> cart){
        this.cart=cart;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.list_cart,parent,false);
        ViewHolder viewholder=new ViewHolder(view);
        return viewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Glide.with(context).load(cart.get(position).getImageURL1()).into(holder.imageView);
        holder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindData(int position) {

            //ImageView imageView;
            ImageView imageView=itemView.findViewById(R.id.image);
            Picasso.get().load(cart.get(position).getImageURL1()).into(imageView);

           // imageView.setImageResource(cart.get(position).imageURL1);
            //TextView textView4=itemView.findViewById(R.id.image);
            //Glide.with(textView4).load(cart.get(position).imageURL1);
            //textView4.setText(cart.get(position).imageURL1);
            TextView itemname=itemView.findViewById(R.id.head_text1);
            itemname.setText(cart.get(position).getItemName());
            TextView username=itemView.findViewById(R.id.user1);
            username.setText(cart.get(position).getUsername());
            TextView productcondition=itemView.findViewById(R.id.condition);
            productcondition.setText(cart.get(position).getCondition());
            TextView price=itemView.findViewById(R.id.price1);
            price.setText("$"+cart.get(position).getPrice());


            //ImageView imageView1=itemView.findViewById(R.id.heart);
            //imageView1.setImageResource(Integer.parseInt(cart.get(position).imageheart));
            Button button=itemView.findViewById(R.id.btn_removecart);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference cartref = FirebaseDatabase.getInstance().getReference().child("Cart");
                    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    //String id = System.currentTimeMillis()+ "_" + UUID.randomUUID().toString();
                    //Replace with product id
                    //cartref.child(uid).child(id).removeValue();
                    cartref.child(uid).child(cart.get(position).getId()).removeValue();
                    cart.remove(position);
                    notifyItemRemoved(position);
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                DatabaseReference cartref = FirebaseDatabase.getInstance().getReference().child("Cart");
                //String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), activity_description.class);
                    intent.putExtra("itemId", cart.get(position).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    itemView.getContext().startActivity(intent);

                    //Toast.makeText(view.getContext(), getItemId(), Toast.LENGTH_SHORT).show();

                    //Intent intent= new Intent(itemView.getContext(),activity_description.class);

                }
            });




        }
    }
}
