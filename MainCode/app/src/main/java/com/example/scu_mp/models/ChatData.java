package com.example.scu_mp.models;

//Seller chat data table for firebase -- holds metadata of seller chats
//Generally: this will hold data for the users you message about their product listings
//This is becomes associated with other message data in firebase through unique id
public class ChatData {
    private String product_item;
    private String timestamp; //Month Day, Year
    private String lastMessage;
    private String user1;
    private String user2;

    ChatData(){} //empty constructor

    //Constructor
    public ChatData(String product_item, String timestamp, String lastMessage, String user1, String user2)
    {
        this.product_item = product_item;
        this.timestamp = timestamp;
        this.lastMessage = lastMessage;
        this.user1 = user1;
        this.user2 = user2;
    }

    //Accessors
    public String getProduct_item() { return product_item; }
    public String getTimestamp() { return timestamp; }
    public String getLastMessage() { return lastMessage; }
    public String getUser1() { return user1; }
    public String getUser2() { return user2; }

    //Setters
    public void setProduct_item(String product_item) { this.product_item = product_item; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }
    public void setUser1(String user1) { this.user1 = user1; }
    public void setUser2(String user2) { this.user1 = user2; }
}

