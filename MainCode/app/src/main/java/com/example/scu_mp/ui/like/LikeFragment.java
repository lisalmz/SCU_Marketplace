package com.example.scu_mp.ui.like;

import static java.lang.Math.round;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scu_mp.Blog;
import com.example.scu_mp.Cart;
import com.example.scu_mp.Likes;
import com.example.scu_mp.NavigationDrawer;
import com.example.scu_mp.R;
import com.example.scu_mp.RecyclerViewAdapterLike;
import com.example.scu_mp.Recyclerviewadaptercart;
import com.example.scu_mp.ShoppingCartActivity;
import com.example.scu_mp.activity_description;
import com.example.scu_mp.databinding.FragmentLikeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.function.Consumer;

public class LikeFragment extends Fragment {

    //private FragmentLikeBinding binding;
    //ImageButton cart;
    //LinearLayout layoutList;
    //ArrayList<Integer> ids;
    RecyclerView recyclerView2;
    DatabaseReference likebref;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_like, container, false);

        //FirebaseAuth.getInstance().getCurrentUser().getUid()

        /*likeheart=(AppCompatImageView) view.findViewById(R.id.likeheart);

        likeheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isseleceted=likeheart.isSelected();
                likeheart.setSelected(!isseleceted);
            }
        });

        likeheart1=(AppCompatImageView) view.findViewById(R.id.likeheart1);
*/


        recyclerView2=(RecyclerView) view.findViewById(R.id.recyclerview2);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLikeProducts();
    }

    public void getLikeProducts(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Product");
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<Blog> productList =  new ArrayList<>();
                if(task.isSuccessful()){
                    task.getResult().getChildren().forEach(new Consumer<DataSnapshot>() {
                        @Override
                        public void accept(DataSnapshot dataSnapshot) {
                            Blog product = dataSnapshot.getValue(Blog.class);
                            if(product.getLikesList() !=null && product.getLikesList().contains(uid)) {
                                product.setId(dataSnapshot.getKey());
                                productList.add(product);
                            }
                        }
                    });

                    RecyclerViewAdapterLike recyclerviewAdapter= new RecyclerViewAdapterLike(productList);

                    recyclerView2.setAdapter(recyclerviewAdapter);
                }
            }
        });
    }

}

