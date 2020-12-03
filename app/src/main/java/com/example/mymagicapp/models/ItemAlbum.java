package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Constraints;

import java.lang.invoke.MutableCallSite;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ItemAlbum extends ImageContainer implements Comparable<ItemAlbum>{
    public ItemAlbum(){ }

    public String getUriOfFirstImage(){
        if(size() == 0) // list is null
            return null;
        else return imageList.get(0).getUri(); // get uri of first image
    }

    @Override
    public int compareTo(ItemAlbum other) {
        return getName().compareTo(other.getName());
    }
}
