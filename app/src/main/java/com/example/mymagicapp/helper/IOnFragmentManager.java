package com.example.mymagicapp.helper;

import com.example.mymagicapp.models.Album;
import com.example.mymagicapp.models.ItemAlbum;

public interface IOnFragmentManager {
    void onItemClick(Album album, int position);
}
