package com.example.mymagicapp.models;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.mymagicapp.helper.Constraint;
import com.example.mymagicapp.helper.Utility;

import java.time.LocalDate;
import java.time.Period;

public class MyImage implements Comparable<MyImage> {
    private String url;
    private int resId;
    private String description;
    private String date;

    public MyImage(String url, int resId, String description, LocalDate date) {
        this.url = url;
        this.resId = resId;
        this.description = description;
        this.date = Utility.dateToString(date);
    }

    public boolean isImageByUrl() {
        if (url.isEmpty())
            return false;
        return true;
    }

    public boolean isImageByResId() {
        if (resId == Constraint.DEFAULT_RES_ID) {
            return false;
        }
        return true;
    }

    public String getTitle() {
        LocalDate d = Utility.stringToDate(date);
        if (d.compareTo(LocalDate.MIN) == 0)
            return d.toString();
        else
            return String.format("%d Tháng %d, %d", d.getDayOfMonth(), d.getMonthValue(), d.getYear());
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return Utility.stringToDate(date);
    }

    public void setDate(LocalDate date) {
        this.date = Utility.dateToString(date);
    }

    @Override
    public int compareTo(MyImage other) {
        if (isImageByUrl() && other.isImageByUrl()) {
            return getUrl().compareTo(other.getUrl());
        } else if (isImageByResId() && other.isImageByResId()) {
            if (this.resId == other.getResId())
                return 0;
        }
        return 1;
    }


}
