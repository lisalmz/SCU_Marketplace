package com.example.scu_mp.models;

public class CartData {


    String category, condition, itemName, price, username, imageURL1;
    String imageheartdata;

    public void CartData(){}

    public CartData(String category, String condition, String itemName, String price, String username, String imageURL1, String imageheartdata) {
        this.category = category;
        this.condition = condition;
        this.itemName = itemName;
        this.price = price;
        this.username = username;
        this.imageURL1 = imageURL1;
        this.imageheartdata = imageheartdata;
    }

    public String getCategory() {
        return category;
    }

    public String getCondition() {
        return condition;
    }

    public String getItemName() {
        return itemName;
    }

    public String getPrice() {
        return price;
    }

    public String getUsername() {
        return username;
    }

    public String getImageURL1() {
        return imageURL1;
    }

    public String getImageheartdata() {
        return imageheartdata;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImageURL1(String imageURL1) {
        this.imageURL1 = imageURL1;
    }

    public void setImageheartdata(String imageheartdata) {
        this.imageheartdata = imageheartdata;
    }
}

