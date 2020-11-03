package com.example.mymagicapp.helper;

import android.content.ClipData;
import android.net.Uri;

public interface ISettingFragment {
    void addItem();
    void addImages(int position, ClipData clipData);
    void removeItem();
    void setImage(int colPos, int imgPos, String uri);
    void saveData();
}
