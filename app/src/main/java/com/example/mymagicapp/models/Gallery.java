package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gallery implements IMyCollection {
    private List<ItemGallery> itemGalleryList = new ArrayList<>();

    public Gallery() {

    }

    @Override
    public void addItem(MyItem item, int index) {
        if (index == Utility.INDEX_TO_ADD_IMAGE) {
            addImage((MyImage) item);
        } else {
            itemGalleryList.add(index, (ItemGallery) item);
        }
    }

    @Override
    public void sort() {
        Collections.sort(itemGalleryList);
    }

    private void addImage(MyImage image) {
        String date = image.getDate();
        ItemGallery temp = findItemByDate(date);
        if (temp != null) {
            temp.addItem(image, Utility.DEFAULT_INDEX_TO_ADD); // just add image to item gallery
        } else {
            temp = new ItemGallery(); // create new item gallery to add image
            temp.setDate(date);
            temp.addItem(image, Utility.DEFAULT_INDEX_TO_ADD);
            itemGalleryList.add(temp);
        }
    }

    private ItemGallery findItemByDate(String date) {
        ItemGallery temp = itemGalleryList.stream()
                .filter(x -> x.getDate().equals(date))
                .findAny()
                .orElse(null);
        return temp;
    }

    public ItemGallery[] toArray() {
        ItemGallery[] itemList = new ItemGallery[itemGalleryList.size()];
        itemGalleryList.toArray(itemList);
        return itemList;
    }

    public List<MyImage> toImageArray() {
        List<MyImage> imageList = new ArrayList<>();
        for (ItemGallery item : itemGalleryList
        ) {
            MyImage[] imagesTemp = item.toArray();
            for (MyImage image : imagesTemp
            ) {
                imageList.add(image);
            }
        }
        return imageList;
    }
}
