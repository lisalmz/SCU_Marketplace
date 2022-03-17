package com.example.scu_mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button signIn;
    private EditText email;
    private EditText password;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.startup_page);

        signIn = findViewById(R.id.sign_in);

        // sign in button is clicked
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(LoginActivity.this);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_login);
                bottomSheetDialog.setCanceledOnTouchOutside(false);

                email = bottomSheetDialog.findViewById(R.id.et_email);
                password = bottomSheetDialog.findViewById(R.id.et_password);

                mAuth = FirebaseAuth.getInstance();

                Button submit = bottomSheetDialog.findViewById(R.id.bt_submit);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //if login success - temporary way to login
                        /*if (email.getText().toString().equals("pb@scu.edu") && password.getText().toString().equals("group3")){
                            Intent in = new Intent(LoginActivity.this, NavigationDrawer.class);
                            startActivity(in);
                        } else{
                            //if login failed
                            Toast.makeText(view.getContext(), "Login Failed. Please try again", Toast.LENGTH_SHORT).show();
                        }*/

                        loginUser();
                    }
                });

                bottomSheetDialog.show();
            }
        });

        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this,R.color.theme_darker));
    }

    public void createAccount(View view){
        Intent in = new Intent(this, CreateActivity.class);
        startActivity(in);
    }

    public void loginUser(){
        String email_address = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        // validation
        if (email_address.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email_address).matches()){
            email.setError("Please enter a valid email address!");
            email.requestFocus();
            return;
        }

        if (pass.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        if (pass.length() < 6){
            password.setError("Please enter a password that is longer than 6 characters");
            password.requestFocus();
            return;
        }

        // database authentication
        mAuth.signInWithEmailAndPassword(email_address, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (task.isSuccessful()){
                    if (user.isEmailVerified()){
                        Intent in = new Intent(LoginActivity.this, NavigationDrawer.class);
                        startActivity(in);
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Please check your email to verify your account and login again!", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(LoginActivity.this, "Email or password incorrect. Please try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()){
            bottomSheetDialog.cancel();
        }
    }
}