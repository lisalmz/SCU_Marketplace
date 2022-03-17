package com.example.scu_mp.models;

import java.util.ArrayList;

public class User {
    public String first_name, last_name, user_name, email, phone;

    public User(){

    }

    public User(String first_name, String last_name, String user_name, String email, String phone){
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_name = user_name;
        this.email = email;
        this.phone = phone;
    }
}
