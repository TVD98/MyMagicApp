package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Constraints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageContainer extends MyItem implements IMyCollection{
    protected List<MyImage> imageList = new ArrayList<>();

    public ImageContainer(){}

    @Override
    public String getTitle() {
        return getName();
    }

    @Override
    public void addItem(MyItem item, int index) {
        if (index == Constraints.DEFAULT_INDEX_TO_ADD)
            imageList.add((MyImage) item);
        else
            imageList.add(index, (MyImage) item);
    }

    @Override
    public void sort() {
        Collections.sort(imageList);
    }

    @Override
    public int size() {
        return imageList.size();
    }

    @Override
    public MyImage[] toArray() {
        MyImage[] myImages = new MyImage[imageList.size()];
        imageList.toArray(myImages);
        return myImages;
    }

}
