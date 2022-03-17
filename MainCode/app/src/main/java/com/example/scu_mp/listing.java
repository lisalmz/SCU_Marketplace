package com.example.scu_mp;

import static java.lang.Math.round;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.scu_mp.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class listing extends AppCompatActivity {
    private final static String TAG = "listing_tag";
    ImageButton backButton;
    ImageButton shoppingCart;
    LinearLayout layoutList;
    ArrayList <Integer> ids;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //product = ids.get(0);
        //cartRef.child("user1").push().setValue(prod);

        Log.i(TAG, "On Create");
        String category = getIntent().getStringExtra("Category");
        Log.i(TAG, "Category: " + category);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Product");
        mDatabase.keepSynced(true);
        Query query = mDatabase.orderByChild("category").equalTo(category);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                layoutList = (LinearLayout) findViewById(R.id.layoutList);
                layoutList.setOrientation(LinearLayout.VERTICAL);
                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    Blog blog = snapShot.getValue(Blog.class);
                    String keyId = snapShot.getKey();
                    //Log.d(TAG, "keyID: " + keyId);
                    String heading = blog.getItemName();
                    String user = blog.getUsername();
                    String brand = blog.getCondition();
                    String price = "$" + blog.getPrice();
                    String imageURL = blog.getImageURL1();
                    List<String> likes = blog.getLikesList();
                    if(likes ==null){
                        likes =new ArrayList<>();
                        blog.setLikesList(likes);
                    }
                    CardView cardView = (CardView) createCardView(
                            heading, user, brand, price, keyId, imageURL, likes, blog);
                    layoutList.addView(cardView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                layoutList = (LinearLayout) findViewById(R.id.layoutList);
                layoutList.setOrientation(LinearLayout.VERTICAL);
                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    Blog blog = snapShot.getValue(Blog.class);

                    String keyId = snapShot.getKey();
                    Log.d(TAG, "keyID: " + keyId);
                    String heading = blog.getItemName();
                    String user = blog.getUsername();
                    String brand = blog.getCondition();
                    String price = "$" + blog.getPrice();
                    CardView cardView = (CardView) createCardView(heading, user, brand, price, keyId);
                    layoutList.addView(cardView);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
         */
        ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        setContentView(R.layout.listing);
        /*layoutList = (LinearLayout) findViewById(R.id.layoutList);
        layoutList.setOrientation(LinearLayout.VERTICAL);
        CardView cardView2 = (CardView) createCardView("Car", "Justin", "Old", "$999.99", "1");
        CardView cardView3 = (CardView) createCardView("Bicycle", "tony", "Brand New", "$15.99", "2");
        layoutList.addView(cardView2);
        layoutList.addView(cardView3);
        CardView cardView4 = (CardView) createCardView("Skateboard", "kent", "Old", "$9.99", "3");
        layoutList.addView(cardView4);
        CardView cardView5 = (CardView) createCardView("Scooter", "bus driver", "Used", "$20.00", "4");
        layoutList.addView(cardView5);
        CardView cardView6 = (CardView) createCardView("Bus", "bus driver", "Poor", "$1120.00", "4");
        layoutList.addView(cardView6);
         */

        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               finish();
            }
        }
        );
        shoppingCart = (ImageButton) findViewById(R.id.shoppingCartButton);
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), ShoppingCartActivity.class);
                startActivity(intent);
            }
        }
        );

    }

    /*public void onClick(View view)
    {
        Intent intent = new Intent(view.getContext(), activity_description.class);
        //intent.putExtra("choice", "transportation");
        startActivity(intent);
    }*/

    public View createCardView(String headingString, String userString, String brandNewString,
                               String priceString, String list_id, String imageURL, List<String> likes, Blog blog)
    {
        CardView cardView = new CardView(this);
        float scalePix = this.getResources().getDisplayMetrics().density;
        float tmp = 120 * scalePix;
        LinearLayout.LayoutParams lparams_cv = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, round(tmp));
        lparams_cv.setMargins(round(15*scalePix), round(15*scalePix), round(15*scalePix), round(15*scalePix));
        cardView.setLayoutParams(lparams_cv);
        int id = View.generateViewId();
        cardView.setId(id);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), activity_description.class);
                intent.putExtra("itemId", list_id);
                //Toast.makeText(view.getContext(), list_id, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        }

        );

        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView heading = new TextView(this);
        RelativeLayout.LayoutParams rl_head = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        rl_head.setMargins(5,0,5,0);
        heading.setLayoutParams(rl_head);
        heading.setText(headingString);
        heading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        heading.setPadding(0,0,0,15);
        heading.setId(View.generateViewId());

        TextView usernameText = new TextView(this);
        RelativeLayout.LayoutParams rl_user = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        rl_user.setMargins(5,0,5,0);
        rl_user.addRule(RelativeLayout.BELOW, heading.getId());
        usernameText.setLayoutParams(rl_user);
        usernameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        usernameText.setText(userString);
        usernameText.setTextColor(Color.BLUE);
        usernameText.setId(View.generateViewId());

        TextView brandNew = new TextView(this);
        RelativeLayout.LayoutParams rl_new = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        rl_new.setMargins(5,0,5,0);
        rl_new.addRule(RelativeLayout.BELOW, usernameText.getId());
        brandNew.setLayoutParams(rl_new);
        brandNew.setText(brandNewString);
        brandNew.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        brandNew.setId(View.generateViewId());

        TextView price = new TextView(this);
        RelativeLayout.LayoutParams rl_price = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        rl_price.setMargins(5,0,5,0);
        price.setLayoutParams(rl_price);
        price.setPadding(0,0,0,20);
        price.setText(priceString);
        price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        price.setGravity(Gravity.END);
        price.setId(View.generateViewId());

        ImageView heart = new ImageView(this);
        RelativeLayout.LayoutParams rl_heart = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        rl_heart.setMargins(5,0,5,0);
        rl_heart.addRule(RelativeLayout.BELOW, price.getId());
        rl_heart.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        heart.setLayoutParams(rl_heart);
        /*Drawable unselected = getDrawable(R.drawable.heart);
        int newColor = getColor(R.color.black);
        unselected.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
         */
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {-android.R.attr.state_selected},
                getResources().getDrawable(R.drawable.ic_action_heart2));
        states.addState(new int[] {android.R.attr.state_selected},
                getResources().getDrawable(R.drawable.ic_red_heart_2));
        heart.setImageDrawable(states);
        heart.setClickable(true);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        heart.setSelected(likes.contains(uid));
        heart.setId(View.generateViewId());
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isseleceted=heart.isSelected();
                heart.setSelected(!isseleceted);
                DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Product");
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if(blog.getLikesList().contains(uid)){
                    blog.getLikesList().remove(uid);
                }else {
                    blog.getLikesList().add(uid);
                }

                database.child(list_id).setValue(blog);
            }
        });

        RelativeLayout rl = new RelativeLayout(this);
        RelativeLayout.LayoutParams rl_params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, round(tmp)
        );

        LinearLayout ll = new LinearLayout(this);
        LinearLayout.LayoutParams lparams_cv_ll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(lparams_cv_ll);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imageItem = new ImageView(this);
        LinearLayout.LayoutParams lparams_cv_image = new LinearLayout.LayoutParams(
                round(130*scalePix), round(120*scalePix));
        imageItem.setLayoutParams(lparams_cv_image);
        imageItem.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.get().load(imageURL).into(imageItem);

        rl.setLayoutParams(rl_params);
        rl.addView(heading);
        rl.addView(usernameText);
        rl.addView(brandNew);
        rl.addView(price);
        rl.addView(heart);
        ll.addView(imageItem);
        ll.addView(rl);
        cardView.addView(ll);
        return cardView;
    }


    protected void onStart()
    {
        super.onStart();
        Log.i(TAG, "On Start");
    }

    protected void onResume()
    {
        super.onResume();
        Log.i(TAG, "On Resume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.i(TAG, "On Pause");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.i(TAG, "On Stop");
    }

    protected void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "On Destroy");
    }
}
