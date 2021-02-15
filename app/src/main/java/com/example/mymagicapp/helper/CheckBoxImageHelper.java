package com.example.mymagicapp.helper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckBoxImageHelper {
    private List<Integer> imageIdList = new ArrayList<>();

    public CheckBoxImageHelper() {

    }

    public Integer[] toArray(){
        Collections.sort(imageIdList);
        Integer[] array = new Integer[imageIdList.size()];
        imageIdList.toArray(array);
        return array;
    }

    public void changeCheckBox(int id) {
        int index = imageIdList.indexOf(id);
        if (index == -1) {
            imageIdList.add(id);
        } else {
            imageIdList.remove(index);
        }
    }

    public boolean contain(int id){
        int index = imageIdList.indexOf(id);
        if(index == -1)
            return false;
        return true;
    }

    public int size(){
        return imageIdList.size();
    }

    public void clear(){
        imageIdList.clear();
    }
}
