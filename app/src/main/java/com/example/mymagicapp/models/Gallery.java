package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Constraints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gallery implements IMyCollection {
    private List<ItemGallery> itemGalleryList = new ArrayList<>();

    public Gallery() { }

    @Override
    public void addItem(MyItem item, int index) {
        if (index == Constraints.INDEX_TO_ADD_IMAGE) {
            addImage((MyImage) item);
        } else {
            itemGalleryList.add((ItemGallery) item);
        }
    }

    @Override
    public void sort() {
        Collections.sort(itemGalleryList);
    }

    @Override
    public int size() {
        return itemGalleryList.size();
    }

    @Override
    public ItemGallery[] toArray() {
        ItemGallery[] itemGalleries = new ItemGallery[itemGalleryList.size()];
        itemGalleryList.toArray(itemGalleries);
        return itemGalleries;
    }

    private void addImage(MyImage image) {
        String date = image.getDate();
        ItemGallery tempItem = findItemByDate(date);
        if (tempItem != null) { // found item
            tempItem.addItem(image, Constraints.DEFAULT_INDEX_TO_ADD); // just add image to item gallery
        } else { // not found item
            tempItem = new ItemGallery(); // create new item gallery to add image
            tempItem.setDate(date);
            tempItem.addItem(image, Constraints.DEFAULT_INDEX_TO_ADD);
            itemGalleryList.add(tempItem);
        }
    }

    private ItemGallery findItemByDate(String date) {
        ItemGallery temp = itemGalleryList.stream()
                .filter(x -> x.getDate().equals(date))
                .findAny()
                .orElse(null); // return null if not found
        return temp;
    }

    private ItemGallery findItemContainingSpecialImage() {
        ItemGallery temp = itemGalleryList.stream()
                .filter(x -> x.isContainingSpecialImage())
                .findAny()
                .orElse(null);
        return temp;
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

    public void removeSpecialImage() {
        ItemGallery item = findItemContainingSpecialImage();
        if (item != null) {
            item.removeSpecialImage();
            // if item just have an image then remove item
            if (item.size() == 0) {
                itemGalleryList.remove(item);
            }
        }
    }
}