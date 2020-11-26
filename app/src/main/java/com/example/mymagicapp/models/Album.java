package com.example.mymagicapp.models;

import android.net.Uri;

import com.example.mymagicapp.helper.Constraint;

import java.util.ArrayList;
import java.util.List;

public class Album implements IMyCollection{
    private List<ItemAlbum> itemAlbums = new ArrayList<>();

    @Override
    public void addItem(MyItem item, int index) {
        itemAlbums.add(index, (ItemAlbum) item);
    }

    @Override
    public void sort() {

    }

    public void addImage(MyImage image, String nameAlbum){
        ItemAlbum item = findItemByName(nameAlbum);
        if(item != null){
            item.addImage(image);
        }
        else{
            item = new ItemAlbum();
            item.setName(nameAlbum);
            item.addImage(image);
            itemAlbums.add(item);
        }
    }

    private ItemAlbum findItemByName(String name){
        ItemAlbum item = itemAlbums.stream()
                .filter(x -> x.getName().compareTo(name)==0)
                .findAny()
                .orElse(null);
        return item;
    }

    public ItemAlbum[] toArray(){
        ItemAlbum[] temp = new ItemAlbum[itemAlbums.size()];
        itemAlbums.toArray(temp);
        return temp;
    }
}
