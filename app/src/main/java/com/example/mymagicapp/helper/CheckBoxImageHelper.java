package com.example.mymagicapp.helper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckBoxImageHelper{
    private List<Integer> imageIdList = new ArrayList<>();
    private boolean checkMode;

    public CheckBoxImageHelper(boolean checkMode) {
        this.checkMode = checkMode;
    }

    public void changeCheckMode() {
        checkMode = !checkMode;
    }

    public Integer[] getCheckedList() {
        Collections.sort(imageIdList);
        Integer[] array = new Integer[imageIdList.size()];
        imageIdList.toArray(array);
        return array;
    }

    public void check(int id) {
        int index = imageIdList.indexOf(id);
        if (index == -1) {
            imageIdList.add(id);
        } else {
            imageIdList.remove(index);
        }
    }

    public void clearCheckedList() {
        imageIdList.clear();
    }

    public boolean getCheckMode() {
        return this.checkMode;
    }

    public int count() {
        return imageIdList.size();
    }
}
