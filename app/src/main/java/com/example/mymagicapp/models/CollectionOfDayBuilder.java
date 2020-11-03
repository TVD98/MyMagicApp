package com.example.mymagicapp.models;


import com.example.mymagicapp.helper.Utility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CollectionOfDayBuilder {
    private LocalDate date = LocalDate.MIN;
    private String description = "";

    public CollectionOfDayBuilder(){};

    public CollectionOfDay buildCollectionOfDay(){
        return new CollectionOfDay(date, description);
    }

    public CollectionOfDayBuilder date(LocalDate date){
        this.date = date;
        return this;
    }

    public CollectionOfDayBuilder description(String description){
        this.description = description;
        return this;
    }
}
