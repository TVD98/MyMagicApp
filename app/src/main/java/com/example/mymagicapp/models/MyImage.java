package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.Utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyImage extends MyItem implements Comparable<MyImage> {
    private String uri = "";
    private int imageId = Constraints.DEFAULT_IMAGE_ID;

    public MyImage(){ }

    public boolean imageIdIsNull(){
        if(imageId == Constraints.DEFAULT_IMAGE_ID)
            return true;
        return false;
    }

    @Override
    public String getTitle() {
        LocalDate date = LocalDate.parse(getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        return String.format("%d Th%d %d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public int compareTo(MyImage o) {
        return this.uri.compareTo(o.uri);
    }
}
