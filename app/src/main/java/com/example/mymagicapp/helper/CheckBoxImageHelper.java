package com.example.mymagicapp.helper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxImageHelper {
    private int collectionId;
    private List<Integer> imageIdList = new ArrayList<>();

    public CheckBoxImageHelper(int collectionId) {
        this.collectionId = collectionId;
    }

    public int getCollectionId() {
        return this.collectionId;
    }

    public List<Integer> getImageIdList(){
        return this.imageIdList;
    }

    @NonNull
    @Override
    public String toString() {
        String result = Integer.toString(collectionId) + "(";
        for (Integer id : imageIdList) {
            result += id + ",";
        }
        return result + ")";
    }

    public void changeCheckBox(int id) {
        int index = imageIdList.indexOf(id);
        if (index == -1) {
            imageIdList.add(id);
        } else {
            imageIdList.remove(index);
        }
    }
}
