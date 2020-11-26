package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Utility;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemGallery extends MyItem implements IMyCollection, Comparable<ItemGallery>{
    private List<MyImage> imageList = new ArrayList<>();

    @Override
    public void addItem(MyItem item, int index) {
        if (index == Utility.DEFAULT_INDEX_TO_ADD)
            imageList.add((MyImage) item);
        else
            imageList.add(index, (MyImage) item);
    }

    @Override
    public void sort() {
        Collections.sort(imageList);
    }

    @Override
    public String getTitle(){
        LocalDate date = LocalDate.parse(getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        if(date.getYear() == LocalDate.now().getYear()){
            Period period = Period.between(date, LocalDate.now());
            if (period.getMonths() > 0 || period.getDays() > 1)
                return String.format("%d Th%d", date.getDayOfMonth(), date.getMonthValue());
            else if (period.getDays() == 1 && period.getMonths() == 0)
                return "Hôm qua";
            return "Hôm nay";
        }
        else{
            return String.format("%d Th%d %d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        }
    }

    public MyImage[] toArray() {
        MyImage[] images = new MyImage[imageList.size()];
        imageList.toArray(images);
        return images;
    }

    @Override
    public int compareTo(ItemGallery o) {
        LocalDate date = LocalDate.parse(getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate otherDate = LocalDate.parse(o.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        return otherDate.compareTo(date);
    }

    // đội 4 thôn Long Vĩnh xâ Bình Long huyện Bình Sơn Quảng Ngãi 2h chiều tomorrow
}
