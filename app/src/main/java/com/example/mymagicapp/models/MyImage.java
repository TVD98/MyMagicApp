package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.Utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MyImage extends MyItem {
    private String uri = "";
    private int imageId = Constraints.DEFAULT_IMAGE_ID;

    public MyImage() {
    }

    public boolean imageIdIsNull() {
        if (imageId == Constraints.DEFAULT_IMAGE_ID)
            return true;
        return false;
    }

    @Override
    public String title() {
        LocalDateTime dateTime = Utility.secondsToLocalDateTime(getDate());
        return String.format("%d Tháng %d, %d", dateTime.getDayOfMonth(), dateTime.getMonthValue(), dateTime.getYear());
    }

    @Override
    public String subTitle() {
        LocalDateTime dateTime = Utility.secondsToLocalDateTime(getDate());
        LocalTime time = dateTime.toLocalTime();
        return Utility.localTimeToString(time);
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

    public int compareTo(MyImage other) {
        return this.getUri().compareTo(other.getUri());
    }


}
