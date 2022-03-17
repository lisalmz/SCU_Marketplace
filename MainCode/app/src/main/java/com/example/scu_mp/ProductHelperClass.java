package com.example.scu_mp;

import com.example.scu_mp.models.AddressData;

import java.util.HashMap;

public class ProductHelperClass {

    String category;
    String condition;
    String itemName;
    String description;
    String price;
    String address;
    String status;
    String username;
    String itemId;
    String imageURL1;
    String imageURL2;
    String imageURL3;
    HashMap<String, Boolean> likeMap;
    //AddressData addressData;
    public ProductHelperClass() {
    }
    //neha


    public ProductHelperClass(String category, String condition, String itemName, String description, String price, String address, String status, String username, String itemId, String imageURL1, String imageURL2, String imageURL3) {
        this.category = category;
        this.condition = condition;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.address = address;
        this.status = status;
        this.username = username;
        this.itemId = itemId;
        this.imageURL1 = imageURL1;
        this.imageURL2 = imageURL2;
        this.imageURL3 = imageURL3;
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

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getItemId() {
        return itemId;
    }

    public String getImageURL1() {
        return imageURL1;
    }

    public String getImageURL2() {
        return imageURL2;
    }

    public String getImageURL3() {
        return imageURL3;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setImageURL1(String imageURL1) {
        this.imageURL1 = imageURL1;
    }

    public void setImageURL2(String imageURL2) {
        this.imageURL2 = imageURL2;
    }

    public void setImageURL3(String imageURL3) {
        this.imageURL3 = imageURL3;
    }

    //neha
    public HashMap<String, Boolean> getLikeMap() {
        return likeMap;
    }

    public void setLikeMap(HashMap<String, Boolean> likeMap) {
        this.likeMap = likeMap;
    }
    //neha
}
