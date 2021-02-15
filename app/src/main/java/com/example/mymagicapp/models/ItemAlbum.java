package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Constraints;

import java.lang.invoke.MutableCallSite;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ItemAlbum extends ImageContainer{
    public ItemAlbum(){ }

    public MyImage getFirstImage(){
        if(size() == 0)
            return null;
        return imageList.get(0);
    }

}
