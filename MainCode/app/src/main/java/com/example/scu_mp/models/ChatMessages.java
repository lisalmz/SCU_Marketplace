package com.example.scu_mp.models;

import com.google.firebase.database.IgnoreExtraProperties;

//This contains the actual messages for each chat in the seller tab
@IgnoreExtraProperties
public class ChatMessages {
    private String user;
    private String message;
    private String timestamp; //MM/DD/YYYY - HH:MM

    public ChatMessages() {}

    public ChatMessages(String user, String message, String timestamp)
    {
        this.user = user;
        this.message = message;
        this.timestamp = timestamp;
    }

    //Accessors
    public String getUser() { return user; }
    public String getMessage() { return message; }
    public String getTimestamp() { return timestamp; }

    //Setters
    public void setUser(String user) { this.user = user; }
    public void setMessage(String message) { this.message = message; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}

