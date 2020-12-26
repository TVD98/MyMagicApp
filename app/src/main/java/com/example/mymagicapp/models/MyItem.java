package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Constraints;

import java.time.LocalDate;

public abstract class MyItem implements Comparable<MyItem>{
    private String name = "";
    private String date = Constraints.LOCAL_DATE_TIME_MIN;
    private String description = "";

    public MyItem(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String title(){
        return getName();
    }

    public String subTitle(){
        return "";
    }

    @Override
    public int compareTo(MyItem other) {
        return other.getDate().compareTo(this.getDate());
    }
}
