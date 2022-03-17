package com.example.scu_mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class activity_description extends AppCompatActivity {

    private ImageSlider imageslider;
    private TextView header;
    private TextView price;
    private TextView user;
    private TextView brandNew;
    private TextView descriptionText;
    private ImageButton backButton;
    ImageButton shoppingcart;
    Button addcart;
    private final static String TAG = "activity_desc";
    ImageView heart;
    DatabaseReference cartref;
    //FirebaseStorage storage;
    //StorageReference storageReference;
    RelativeLayout relativeLayout;

    private DatabaseReference mDatabase;
    private Blog blog;
    private String itemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        //imageslider = (ImageSlider) findViewById(R.id.image_slider);
        //ArrayList<SlideModel> image_list = new ArrayList<>();
        //image_list.add(new SlideModel(R.drawable.furniture, null));
        //image_list.add(new SlideModel(R.drawable.textbook, null));
        //imageslider.setImageList(image_list, ScaleTypes.CENTER_CROP);
        backButton = findViewById(R.id.imageButton1);
        imageslider = (ImageSlider) findViewById(R.id.image_slider);
        heart=findViewById(R.id.heart);
        addcart=findViewById(R.id.addCart);
        shoppingcart=findViewById(R.id.shoppingcart);
        ArrayList<SlideModel> image_list = new ArrayList<>();
        itemId = getIntent().getStringExtra("itemId");
        //
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Product");
        mDatabase.keepSynced(true);


        Query query = mDatabase.orderByKey().equalTo(itemId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    blog = snapShot.getValue(Blog.class);
                    String imageURL1 = blog.getImageURL1();
                    String imageURL2 = blog.getImageURL2();
                    String imageURL3 = blog.getImageURL3();
                    // Lisa tried to remove the requirement of at least one photo image_list.add(new SlideModel(imageURL1, null));
                    header = (TextView) findViewById(R.id.product_name);
                    brandNew = (TextView) findViewById(R.id.brandNew);
                    price = (TextView) findViewById(R.id.price);
                    user = (TextView) findViewById(R.id.user);
                    descriptionText = (TextView) findViewById(R.id.description_text);
                    header.setText(blog.getItemName());
                    price.setText(blog.getPrice());
                    user.setText(blog.getUsername());
                    brandNew.setText(blog.getCondition());
                    descriptionText.setText(blog.getDescription());
                    // Lisa added the first line to remove requirement for at least one photo
                    if (imageURL1 != null) image_list.add(new SlideModel(imageURL1, null));
                    if (imageURL2 != null) image_list.add(new SlideModel(imageURL2, null));
                    if (imageURL3 != null) image_list.add(new SlideModel(imageURL3, null));
                    imageslider.setImageList(image_list, ScaleTypes.CENTER_CROP);
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    if(blog.getLikesList() ==null){
                        blog.setLikesList(new ArrayList());
                    }
                    heart.setSelected(blog.getLikesList().contains(uid));
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //

        //ArrayList<SlideModel> image_list = new ArrayList<>();
        //Intent intent = getIntent();
        //  String directory = intent.getStringExtra(SellActivity1.KEY5);

        //  ArrayList<String> urlList = new ArrayList<>();

        // get the Firebase storage reference
     /*   storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Log.i("mjfan", directory);
        StorageReference downloadImagesRef = storageReference.child(directory);
        downloadImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                urlList.add(uri.toString());
                Log.i("mjfan", uri.toString());
            }
        });
      */
        shoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShoppingCartActivity.class);
                startActivity(intent);
            }
        });




        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeProduct();

            }
        });

        cartref= FirebaseDatabase.getInstance().getReference().child("Cart");
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity_description.this, "Product is added to cart", Toast.LENGTH_LONG).show();
                addTocart();
                //cartref.push().setValue();
                //product = ids.get(0);
                //cartref= FirebaseDatabase.getInstance().getReference().child("Cart");
                //cartRef.child("user1").push().setValue(prod);


            }

        });

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

    public void addTocart(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Cart");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        blog.setId(itemId);
        database.child(uid).child(itemId).setValue(blog);
    }

    public void likeProduct(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Product");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(blog.getLikesList().contains(uid)){
            blog.getLikesList().remove(uid);
        }else {
            blog.getLikesList().add(uid);
        }

        database.child(itemId).setValue(blog);
        boolean isseleceted=heart.isSelected();
        heart.setSelected(!isseleceted);
    }
}