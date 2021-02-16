package com.example.mymagicapp.helper;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mymagicapp.adapter.RecyclerViewItemAdapter;
import com.example.mymagicapp.adapter.RecyclerViewItemDataAdapter;
import com.example.mymagicapp.models.MyImage;

import java.util.List;

public class RecyclerViewFactory {
    public static IRecyclerViewImage getRecyclerViewImage(String nameAlbum, List<MyImage> images, Context context) {
        if (nameAlbum.compareTo(SaveSystem.KEY_NAME_ALBUM) == 0)
            return new RecyclerViewItemAdapter(images, context);
        return new RecyclerViewItemDataAdapter(images, context);
    }
}
