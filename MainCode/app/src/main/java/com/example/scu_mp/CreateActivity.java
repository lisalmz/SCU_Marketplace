package com.example.scu_mp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.scu_mp.models.User;
import com.example.scu_mp.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class CreateActivity extends AppCompatActivity {

    public final static String TAG = "CreateActivity";

    FirebaseAuth mAuth;

    EditText firstName;
    EditText lastName;
    EditText userName;
    EditText password;
    EditText retypePassword;
    EditText email;
    EditText phone;

    AppCompatButton bt_create;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        mAuth = FirebaseAuth.getInstance();

        firstName = findViewById(R.id.first_name_text);
        lastName = findViewById(R.id.last_name_text);
        userName = findViewById(R.id.username_text);
        password = findViewById(R.id.password_text);
        retypePassword = findViewById(R.id.retype_password_text);
        email = findViewById(R.id.email_text);
        phone = findViewById(R.id.phone_number);

        bt_create = findViewById(R.id.bt_create);

        bt_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
        // Add info to Database
    }


    public void create(View view){
        Intent in = new Intent(CreateActivity.this, NavigationDrawer.class);
        startActivity(in);
        // Logging for testing purposes
        Log.i(TAG, firstName.toString());
        Log.i(TAG, lastName.toString());
        Log.i(TAG, userName.toString());
        Log.i(TAG, email.toString());
        Log.i(TAG, phone.toString());
    }

    public void createUser(){
        String first_name = firstName.getText().toString().trim();
        String last_name = lastName.getText().toString().trim();
        String username = userName.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String retype_pass = retypePassword.getText().toString().trim();
        String email_address = email.getText().toString().trim();
        String phone_num = phone.getText().toString().trim();

        if (first_name.isEmpty()){
            firstName.setError("First Name is required");
            firstName.requestFocus();
            return;
        }

        if (last_name.isEmpty()){
            lastName.setError("Last Name is required");
            lastName.requestFocus();
            return;
        }

        if (username.isEmpty()){
            userName.setError("Username is required");
            userName.requestFocus();
            return;
        }

        if (pass.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if (retype_pass.isEmpty()){
            retypePassword.setError("Please retype password");
            retypePassword.requestFocus();
            return;
        }

        if (!retype_pass.equals(pass)){
            retypePassword.setError("Passwords do not match. Please retype password");
            retypePassword.requestFocus();
            return;
        }

        if (email_address.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email_address).matches()) {
            email.setError("Please enter valid email");
            email.requestFocus();
            return;
        }

        if (phone_num.isEmpty()){
            phone.setError("Phone number is required");
            phone.requestFocus();
            return;
        }

        if (pass.length() < 6){
            password.setError("Please enter a password that is longer than 6 characters");
            password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email_address, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(first_name, last_name, username, email_address, phone_num);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(CreateActivity.this, first_name + " " + last_name + " has been registered successfully!\n\tPlease sign in again!", Toast.LENGTH_LONG).show();
                                        Intent in = new Intent(CreateActivity.this, LoginActivity.class);
                                        startActivity(in);
                                    }else{
                                        Toast.makeText(CreateActivity.this, "Failed to register user. Please try again!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(CreateActivity.this, "Failed to register user. Please try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
