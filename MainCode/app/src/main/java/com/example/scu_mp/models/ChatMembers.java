package com.example.scu_mp.models;

//Contains the two users (a buyer and a seller) who are in the seller chat about an item
public class ChatMembers {
    private String buyer_user; //In seller chats, you are the buyer
    private String seller_user;

    ChatMembers() {}

    //Constructor
    public ChatMembers(String buyer_user, String seller_user)
    {
        this.buyer_user = buyer_user;
        this.seller_user = seller_user;
    }

    //Accessors
    public String getBuyer_user() { return buyer_user; }
    public String getSeller_user() { return seller_user; }

    //Setters
    public void setBuyer_user(String buyer_user) { this.buyer_user = buyer_user; }
    public void setSeller_user(String seller_user) { this.seller_user = seller_user; }
}
