package com.example.mymagicapp.models;

import java.lang.invoke.MutableCallSite;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ItemAlbum extends MyItem{
    private MyImage currentImage = new MyImage("");
    private int amount = 0;

    public ItemAlbum(){

    }

    public MyImage getCurrentImage() {
        return currentImage;
    }

    @Override
    public String getTitle() {
        return getName();
    }

    public void addImage(MyImage image){
        LocalDate dateOfCurrentImage = LocalDate.parse(currentImage.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        if(dateOfCurrentImage.compareTo(LocalDate.MIN) == 0){
            currentImage = image;
        }
        else{
            LocalDate date = LocalDate.parse(image.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            if(date.compareTo(dateOfCurrentImage) > 0)
                currentImage = image;
        }
        amount ++;
    }

    public int getAmount() {
        return amount;
    }
}
