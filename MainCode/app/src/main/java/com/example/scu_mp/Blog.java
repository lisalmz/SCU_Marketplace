package com.example.scu_mp;

import java.util.List;

public class Blog {

    private String itemName, condition, price, imageURL1, imageURL2, imageURL3, username, category, description, id,address,status;
    private List<String> likesList;
    public Blog(){

    }
        /*public Blog(String itemName, String condition, String price, String imageURL1, String username, String category, String description) {
        this.itemName = itemName;
        this.condition = condition;
        this.price = price;
        this.imageURL1 = imageURL1;
        this.username = username;
        this.category = category;
        this.description = description;
    }

    */


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }



    public void setPrice(String price) {
        this.price = price;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImageURL0(String imageURL0) {
        this.imageURL1 = imageURL0;
    }

    public String getImageURL2() {
        return imageURL2;
    }

    public void setImageURL2(String imageURL2) {
        this.imageURL2 = imageURL2;
    }

    public String getImageURL3() {
        return imageURL3;
    }

    public void setImageURL3(String imageURL3) {
        this.imageURL3 = imageURL3;
    }

    public void setImageURL1(String imageURL1) {
        this.imageURL1 = imageURL1;
    }




    public String getItemName() {
        return itemName;
    }

    public String getCondition() {
        return condition;
    }


    public String getPrice() {
        return price;
    }

    public String getImageURL1() {
        return imageURL1;
    }

    public String getUsername()
    {
        return username;
    }

    public String getCategory()
    {
        return category;
    }

    public String getDescription()
    {
        return description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<String> likesList) {
        this.likesList = likesList;
    }
}
