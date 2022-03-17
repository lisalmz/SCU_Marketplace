package com.example.scu_mp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapterLike  extends RecyclerView.Adapter<RecyclerViewAdapterLike.ViewHolder2> {
    ArrayList<Blog> likes;
    ImageView heart;
    //private Blog blog;
    //private String itemId;



    public RecyclerViewAdapterLike(ArrayList<Blog> arrayList) {
        this.likes=arrayList;

    }

    @NonNull
    @Override
    public RecyclerViewAdapterLike.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.like_product_list,parent,false);
        ViewHolder2 viewholder=new ViewHolder2(view);
        heart=(ImageView) view.findViewById(R.id.heart);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterLike.ViewHolder2 holder, int position) {
        holder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return likes.size();
    }


    public class ViewHolder2 extends RecyclerView.ViewHolder {
        public ViewHolder2(@NonNull View itemView) {

            super(itemView);

        }

        public void bindData(int position) {

            //ImageView imageView=itemView.findViewById(R.id.image);
            //imageView.setImageResource(likes.get(position).image1);
            ImageView imageView=itemView.findViewById(R.id.image);
            Picasso.get().load(likes.get(position).getImageURL1()).into(imageView);
            TextView itemname=itemView.findViewById(R.id.head_text1);
            itemname.setText(likes.get(position).getItemName());
            TextView user=itemView.findViewById(R.id.user1);
            user.setText(likes.get(position).getUsername());
            TextView productcondition=itemView.findViewById(R.id.condition);
            productcondition.setText(likes.get(position).getCondition());
            TextView price=itemView.findViewById(R.id.price1);
            price.setText("$"+likes.get(position).getPrice());
            ImageView heart1=itemView.findViewById(R.id.heart);
            //imageView1.setImageResource(Integer.parseInt(likes.get(position).));


            itemView.setOnClickListener(new View.OnClickListener() {
                //DatabaseReference cartref = FirebaseDatabase.getInstance().getReference().child("Cart");
                //String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), activity_description.class);
                    intent.putExtra("itemId", likes.get(position).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    itemView.getContext().startActivity(intent);

                    //Toast.makeText(view.getContext(), getItemId(), Toast.LENGTH_SHORT).show();

                    //Intent intent= new Intent(itemView.getContext(),activity_description.class);

                }
            });

            heart1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    unlikeProduct(likes.get(position));
                    likes.remove(position);
                    notifyItemRemoved(position);
                }
            });


        }

        public void unlikeProduct(Blog blog){
            DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Product");
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if(blog.getLikesList().contains(uid)){
                blog.getLikesList().remove(uid);
            }else {
                blog.getLikesList().add(uid);
            }

            database.child(blog.getId()).setValue(blog);
            boolean isseleceted=heart.isSelected();
            heart.setSelected(!isseleceted);
        }
    }
}
