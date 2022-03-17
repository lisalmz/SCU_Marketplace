package com.example.scu_mp;

import static com.example.scu_mp.R.id.cameraButton2;
import static com.example.scu_mp.R.id.cameraButton3;
import static com.example.scu_mp.R.id.imageButton1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.scu_mp.models.User;
import com.example.scu_mp.ui.sell.SellFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.DatabaseReference;


public class SellActivity1 extends AppCompatActivity {

    //variables
    TextInputLayout categoryLayout, conditionLayout, itemNameLayout, descriptionLayout, priceLayout, locationLayout;
    TextInputLayout uniqueid;
    ProgressBar progressBar;

    FirebaseDatabase rootNode;
    DatabaseReference productref;
    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    public static final int[] CAMERA_PERM_CODE = {101, 102, 103};
    // camera request code for image grid 0, 1, 2
    public static final int[] CAMERA_REQUEST_CODE = {201, 202, 203};
    ImageView displayImage1, displayImage2, displayImage3;
    ImageButton cameraButton1, cameraButton2, cameraButton3;
    Button mButton;
    public static final String KEY1 = "msg1";
    public static final String KEY2 = "msg2";
    public static final String KEY3 = "msg3";
    public static final String KEY4 = "msg4";
    public static final String KEY5 = "photo1";



    String[] categoryItems = {"Furniture", "Textbooks", "Computers", "School Supplies", "Transportation","Miscellaneous"};
    String[] conditionItems = {"New", "Like New", "Good", "Fair", "Poor"};

    AutoCompleteTextView autoCompleteTxt1;
    ArrayAdapter<String> adapterItems1;
    AutoCompleteTextView autoCompleteTxt2;
    ArrayAdapter<String> adapterItems2;
    ImageButton imagebutton;
    TextInputEditText descriptionText, itemnameText, priceText, locationText;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_sell1);

        displayImage1 = findViewById(R.id.imageView1);
        displayImage2 = findViewById(R.id.imageView2);
        displayImage3 = findViewById(R.id.imageView3);
        cameraButton1 = findViewById(R.id.cameraButton1);
        cameraButton2 = findViewById(R.id.cameraButton2);
        cameraButton3 = findViewById(R.id.cameraButton3);
        mButton = findViewById(R.id.btnPost);
        autoCompleteTxt1 = findViewById(R.id.category);
        autoCompleteTxt2 = findViewById(R.id.condition);
        itemnameText = findViewById(R.id.item);
        descriptionText = findViewById(R.id.description);
        priceText = findViewById(R.id.price);
        locationText = findViewById(R.id.address);


        categoryLayout = findViewById(R.id.CategoryLayout);
        conditionLayout = findViewById(R.id.ConditionLayout);
        itemNameLayout = findViewById(R.id.ItemLayout);
        descriptionLayout = findViewById(R.id.DescriptionLayout);
        priceLayout = findViewById(R.id.PriceLayout);
        locationLayout = findViewById(R.id.LocationLayout);
        uniqueid = findViewById(R.id.CategoryLayout);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);
        rootNode = FirebaseDatabase.getInstance();
        productref= FirebaseDatabase.getInstance().getReference().child("Product");

        // get the Firebase storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("Product");


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = categoryLayout.getEditText().getText().toString();
                String condition = conditionLayout.getEditText().getText().toString();
                String itemName = itemNameLayout.getEditText().getText().toString();
                String description = descriptionLayout.getEditText().getText().toString();
                String price = priceLayout.getEditText().getText().toString();
                String address = locationLayout.getEditText().getText().toString();
                /*BitmapDrawable drawable;
                drawable = (BitmapDrawable) displayImage1.getDrawable();*/



                if(/*drawable==null || */category.isEmpty() || condition.isEmpty() || itemName.isEmpty()
                        || description.isEmpty()||price.isEmpty()||address.isEmpty()){
                    Toast.makeText(SellActivity1.this, "Check if you have entered all the fields.", Toast.LENGTH_LONG).show();}
                else {
                    uploadToFirebase();
                    Intent intent = new Intent(SellActivity1.this, Splash.class);
                    startActivity(intent);
                    finish();
                }
            }


        });


        cameraButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions(0);
            }
        });

        cameraButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions(1);
            }
        });

        cameraButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions(2);
            }
        });


        autoCompleteTxt1 = findViewById(R.id.category);

        adapterItems1 = new ArrayAdapter<String>(this,R.layout.list_item,categoryItems);

        autoCompleteTxt1.setAdapter(adapterItems1);

        autoCompleteTxt2 = findViewById(R.id.condition);

        adapterItems2 = new ArrayAdapter<String>(this,R.layout.list_item,conditionItems);

        autoCompleteTxt2.setAdapter(adapterItems2);

        getWindow().setStatusBarColor(ContextCompat.getColor(SellActivity1.this,R.color.theme_darker));

        imagebutton = findViewById(imageButton1);

        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kill current activity
                // reference: https://stackoverflow.com/questions/14848590/return-back-to-mainactivity-from-another-activity
                finish();
            }
        });



    }


    private void askCameraPermissions(int grid_idx) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE[grid_idx]);
        } else {
            openCamera(grid_idx);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE[0]) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera(0);
            } else {
                Toast.makeText(this, "Camera Permission is Required to Use camera", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CAMERA_PERM_CODE[1]) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera(1);
            } else {
                Toast.makeText(this, "Camera Permission is Required to Use camera", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CAMERA_PERM_CODE[2]) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera(2);
            } else {
                Toast.makeText(this, "Camera Permission is Required to Use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera(int grid_idx) {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE[grid_idx]);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE[0]) {
            Bitmap image1 = (Bitmap) data.getExtras().get("data");
            displayImage1.setImageBitmap(image1);
        }
        if (requestCode == CAMERA_REQUEST_CODE[1]) {
            Bitmap image2 = (Bitmap) data.getExtras().get("data");
            displayImage2.setImageBitmap(image2);
        }
        if (requestCode == CAMERA_REQUEST_CODE[2]) {
            Bitmap image3 = (Bitmap) data.getExtras().get("data");
            displayImage3.setImageBitmap(image3);
        }
    }

    /*private void saveToGallery() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) displayImage1.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        FileOutputStream outputStream = null;
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath() + "/MyPics");
        dir.mkdirs();

        String filename = String.format("%d.png",System.currentTimeMillis());
        File outFile = new File(dir,filename);
        try{
            outputStream = new FileOutputStream(outFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        try{
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    private void uploadToFirebase() {
        String category = categoryLayout.getEditText().getText().toString();
        String condition = conditionLayout.getEditText().getText().toString();
        String itemName = itemNameLayout.getEditText().getText().toString();
        String description = descriptionLayout.getEditText().getText().toString();
        String price = priceLayout.getEditText().getText().toString();
        String address = locationLayout.getEditText().getText().toString();
        String id = System.currentTimeMillis()+ "_" + UUID.randomUUID().toString();
        String status = "not sold";

        ProductHelperClass helperClass = new ProductHelperClass(category, condition, itemName,
                description, price, address, status, id, null, null, null, null);

        productref.child(id).setValue(helperClass);
        progressBar.setVisibility(View.INVISIBLE);
        // Upload images, get url and update the url fields of the same entry
        StorageReference ImageFolder = storageReference.child("ImageFolder");
        BitmapDrawable drawable = null;
        for (int upload_count = 0; upload_count < 3; upload_count++) {
            if (upload_count == 0) {
                drawable = (BitmapDrawable) displayImage1.getDrawable();
            } else if (upload_count == 1) {
                drawable = (BitmapDrawable) displayImage2.getDrawable();
            } else if (upload_count == 2) {
                drawable = (BitmapDrawable) displayImage3.getDrawable();
            }
            if (drawable == null) continue;
            Bitmap image = drawable.getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] each_image = baos.toByteArray();
            StorageReference ImageName = ImageFolder.child(id + '_' +
                    Integer.toString(upload_count) + ".jpg");
            int finalUpload_count = upload_count;
            ImageName.putBytes(each_image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Map<String, Object> updates = new HashMap<String, Object>();
                            updates.put("imageURL" + Integer.toString(finalUpload_count+1),
                                    uri.toString());
                            productref.child(id).updateChildren(updates);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            });
        }

        // Upload user name
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();
        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User prof = snapshot.getValue(User.class);
                Map<String, Object> updates = new HashMap<String, Object>();
                updates.put("username", prof.user_name);
                productref.child(id).updateChildren(updates);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

}

