package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.Utility;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemGallery extends ImageContainer implements IMyCollection {

    @Override
    public String title() {
        LocalDate date = Utility.secondsToLocalDate(getDate());
        if (date.getYear() == LocalDate.now().getYear()) {  // compare with current time
            Period period = Period.between(date, LocalDate.now());
            if (period.getMonths() > 0 || period.getDays() > 1)
                return String.format("%d Thg%d", date.getDayOfMonth(), date.getMonthValue());
            else if (period.getDays() == 1 && period.getMonths() == 0)
                return "Hôm qua";
            return "Hôm nay";
        } else {
            return String.format("%d Thg%d %d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        }
    }

    public void removeImageByUri(MyImage image) {
        for (MyImage item : imageList
        ) {
            if (item.getUri().compareTo(image.getUri()) == 0) {
                imageList.remove(item);
                break;
            }
        }
    }
}
