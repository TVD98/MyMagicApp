package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Constraints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageContainer extends MyItem implements IMyCollection{
    protected List<MyImage> imageList = new ArrayList<>();

    public ImageContainer(){}

    @Override
    public String title() {
        return getName();
    }

    @Override
    public void addItem(MyItem item, int index) {
        if (index == Constraints.DEFAULT_INDEX_TO_ADD)
            imageList.add((MyImage) item);
        else
            imageList.add(index, (MyImage) item);
        this.sort(); // sort after add image.
    }

    @Override
    public void removeItem(MyItem item) {
        for (MyImage image: imageList
             ) {
            if(image.compareTo((MyImage)item) == 0){
                imageList.remove(image);
                return;
            }
        }
    }

    @Override
    public void removeItem(int index) {
        imageList.remove(index);
    }

    @Override
    public MyItem getItem(int index) {
        return imageList.get(index);
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

    public MyImage findByName(String imageName){
        for (MyImage image: imageList
             ) {
            if(image.getName() == imageName)
                return image;
        }
        return null;
    }

}
