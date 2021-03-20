package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.helper.Utility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gallery implements IMyCollection {
    private List<ItemGallery> itemGalleryList = new ArrayList<>();

    public List<ItemGallery> getItemGalleryList() {
        return itemGalleryList;
    }

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
    public void removeItem(MyItem item) {
        ItemGallery itemGallery = findItemByDate(item.getDate());
        if(itemGallery != null){
            itemGallery.removeItem(item);
            // if item just have an image then remove item
            if (itemGallery.size() == 0) {
                itemGalleryList.remove(itemGallery);
            }
        }
    }

    public void removeImages(ItemAlbum itemAlbum){
        for (ItemGallery item: itemGalleryList
             ) {
            for (MyImage image: itemAlbum.imageList
                 ) {
                item.removeImageByUri(image);
            }
        }
    }

    @Override
    public void removeItem(int index) {
        itemGalleryList.remove(index);
    }

    @Override
    public MyItem getItem(int index) {
        return itemGalleryList.get(index);
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
        LocalDate dateAdded = Utility.secondsToLocalDate(date);
        ItemGallery temp = itemGalleryList.stream()
                .filter(x -> Utility.secondsToLocalDate(x.getDate()).compareTo(dateAdded) == 0)
                .findAny()
                .orElse(null); // return null if not found
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
}