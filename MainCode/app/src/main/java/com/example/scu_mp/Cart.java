package com.example.scu_mp;

public class Cart {
    int image;
    String prpducttype;
    String username;
    String condition;
    String price;
    String imageheart;

    public Cart(int image, String prpducttype, String username, String condition, String price, String imageheart) {
        this.image = image;
        this.prpducttype = prpducttype;
        this.username = username;
        this.condition = condition;
        this.price = price;
        this.imageheart = imageheart;
    }


    public int getImage() {
        return image;
    }

    public String getPrpducttype() {
        return prpducttype;
    }

    public String getUsername() {
        return username;
    }

    public String getCondition() {
        return condition;
    }

    public String getPrice() {
        return price;
    }

    public String getImageheart() {
        return imageheart;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setPrpducttype(String prpducttype) {
        this.prpducttype = prpducttype;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageheart(String imageheart) {
        this.imageheart = imageheart;
    }
}
