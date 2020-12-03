package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Constraints;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemGallery extends ImageContainer implements IMyCollection, Comparable<ItemGallery> {

    @Override
    public String getTitle() {
        LocalDate date = LocalDate.parse(getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        if (date.getYear() == LocalDate.now().getYear()) {  // compare with current time
            Period period = Period.between(date, LocalDate.now());
            if (period.getMonths() > 0 || period.getDays() > 1)
                return String.format("%d Th%d", date.getDayOfMonth(), date.getMonthValue());
            else if (period.getDays() == 1 && period.getMonths() == 0)
                return "Hôm qua";
            return "Hôm nay";
        } else {
            return String.format("%d Th%d %d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        }
    }

    @Override
    public int compareTo(ItemGallery other) {
        LocalDate date = LocalDate.parse(getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate otherDate = LocalDate.parse(other.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        return otherDate.compareTo(date);
    }

    public boolean isContainingSpecialImage() {
        for (MyImage image : imageList
        ) {
            if (!image.imageIdIsNull())
                return true;
        }
        return false;
    }

    public void removeSpecialImage(){
        int len = imageList.size();
        for(int i=0;i<len;i++){
            if(!imageList.get(i).imageIdIsNull()){
                imageList.remove(i);
                return;
            }
        }
    }
}
