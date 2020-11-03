package com.example.mymagicapp.models;

import android.net.Uri;

import com.example.mymagicapp.helper.Constraint;

public class Album {
    private String title = "";
    private MyImage myImage;
    private int amount = 0;

    public Album() {
        this.myImage = new MyImageBuilder().buildMyImage();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MyImage getMyImage() {
        return myImage;
    }

    public void setMyImage(String uri) {
        this.myImage.setUrl(uri);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
