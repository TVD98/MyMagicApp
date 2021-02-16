package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Constraints;

import java.lang.invoke.MutableCallSite;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ItemAlbum extends ImageContainer {
    public ItemAlbum() {
    }

    @Override
    public void addImage(MyImage image) {
        String name = Integer.toString(Integer.parseInt(getLastImageName()) + 1);
        image.setName(name);
        if (getName().compareTo(image.getDescription()) != 0)
            image.setDescription(getName());
        super.addImage(image);
    }

    public MyImage getFirstImage() {
        if (size() == 0)
            return null;
        return imageList.get(0);
    }

    public String getLastImageName() {
        int size = size();
        if (size == 0)
            return "0";
        else return this.imageList.get(size - 1).getName();
    }

}
