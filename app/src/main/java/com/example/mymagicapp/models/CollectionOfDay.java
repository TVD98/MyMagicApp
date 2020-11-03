package com.example.mymagicapp.models;

import android.net.Uri;

import com.example.mymagicapp.helper.Utility;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionOfDay implements Comparable<CollectionOfDay> {
    private String date;
    private List<MyImage> imageList = new ArrayList<>();
    private String description;

    public CollectionOfDay(LocalDate date, String description) {
        this.date = Utility.dateToString(date);
        this.description = description;
    }

    public void addImage(MyImage image) {
        image.setDate(Utility.stringToDate(this.date));
        imageList.add(image);
    }

    public void addUploadImage(){
        imageList.add(0, new MyImageBuilder().buildMyImage());
    }

    public void setImage(int pos, String uri) {
        imageList.get(pos).setUrl(uri);
    }

    public void removeImages(List<Integer> imageIdList) {
        imageIdList.sort(Comparator.naturalOrder());
        for (int i = 0; i < imageIdList.size(); i++) {
            imageList.remove(imageIdList.get(i) - i);
        }
    }

    public void removeImage(int index){
        imageList.remove(index);
    }

    public String getTitle() {
        LocalDate d = Utility.stringToDate(date);
        if (d.compareTo(LocalDate.MIN) == 0)
            return "Chọn ngày";
        else {
            Period period = Period.between(d, LocalDate.now());
            if (period.getDays() == 0)
                return "Hôm nay";
            else if (period.getDays() == 1)
                return "Hôm qua";
            return String.format("%d Th %d", d.getDayOfMonth(), d.getMonthValue());
        }
    }

    public LocalDate getDate() {
        return Utility.stringToDate(date);
    }

    public void setDate(LocalDate date) {
        this.date = Utility.dateToString(date);
        for (MyImage image: this.imageList
             ) {
            image.setDate(date);
        }
    }

    public List<MyImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<MyImage> imageList) {
        this.imageList = imageList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(CollectionOfDay other) {
        return -(getDate().compareTo(other.getDate()));
    }
}
