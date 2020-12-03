package com.example.mymagicapp.models;

import com.example.mymagicapp.helper.Constraints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Album implements IMyCollection{
    private List<ItemAlbum> itemAlbums = new ArrayList<>();

    @Override
    public void addItem(MyItem item, int index) {
        if (index == Constraints.INDEX_TO_ADD_IMAGE) {
            addImage((MyImage) item);
        } else {
            itemAlbums.add((ItemAlbum) item);
        }
    }

    @Override
    public void sort() {
        Collections.sort(itemAlbums);
    }

    @Override
    public int size() {
        return itemAlbums.size();
    }

    @Override
    public ItemAlbum[] toArray(){
        ItemAlbum[] temp = new ItemAlbum[itemAlbums.size()];
        itemAlbums.toArray(temp);
        return temp;
    }

    private void addImage(MyImage image){
        String nameAlbum = image.getDescription();
        ItemAlbum item = findItemByName(nameAlbum);
        if(item != null){
            item.addItem(image, Constraints.DEFAULT_INDEX_TO_ADD);
        }
        else{
            item = new ItemAlbum();
            item.setName(nameAlbum);
            item.addItem(image, Constraints.DEFAULT_INDEX_TO_ADD);
            itemAlbums.add(item);
        }
    }

    private ItemAlbum findItemByName(String name){
        ItemAlbum item = itemAlbums.stream()
                .filter(x -> x.getName().compareTo(name)==0)
                .findAny()
                .orElse(null); // return null if not found
        return item;
    }

}
