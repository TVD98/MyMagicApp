package com.example.mymagicapp.models;

import java.time.LocalDate;

public abstract class MyItem {
    private String name = "";
    private String date = LocalDate.MIN.toString();
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

    public abstract String getTitle();
}
