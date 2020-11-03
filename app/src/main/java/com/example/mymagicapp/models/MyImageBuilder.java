package com.example.mymagicapp.models;

import android.net.Uri;

import com.example.mymagicapp.helper.Constraint;

import java.time.LocalDate;

public class MyImageBuilder {
    private String url = "";
    private int resId = Constraint.DEFAULT_RES_ID;
    private String description = "";
    private LocalDate date = LocalDate.MIN;

    public MyImageBuilder(){}

    public MyImage buildMyImage(){
        return new MyImage(url, resId, description, date);
    }

    public MyImageBuilder url(String url){
        this.url = url;
        return this;
    }

    public MyImageBuilder resId(int resId){
        this.resId = resId;
        return this;
    }

    public MyImageBuilder description(String description){
        this.description = description;
        return this;
    }

    public MyImageBuilder date(LocalDate date){
        this.date = date;
        return this;
    }


}
