package com.example.mymagicapp.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mymagicapp.models.Album;
import com.example.mymagicapp.models.Gallery;
import com.example.mymagicapp.models.MyImage;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class SaveSystem {
    public static final String SHARE_PREFERENCES_NAME = "TVD";
    public static final String KEY_NAME_COLLECTION = "KEY_NAME_COLLECTION";
    public static final String KEY_NAME_ALBUM = "KEY_NAME_ALBUM";
    public static final String KEY_NAME_IMAGE_LIST = "KEY_NAME_IMAGE_LIST";

    public static void saveData(Context context, String keyDataName, Object data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString(keyDataName, json);
        editor.apply();
    }

    public static <T> T getData(Context context, String keyDataName, Type type) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString(keyDataName, null);
        if (data == null)
            return null;
        else {
            try{
            Gson gson = new Gson();
            return gson.fromJson(data, type);
            } catch (Exception e){
                return null;
            }
        }
    }

    public static void saveGalleryToShared(Gallery gallery, Context context){
        saveData(context, KEY_NAME_COLLECTION, gallery);
        saveData(context, KEY_NAME_IMAGE_LIST, gallery.toImageArray().toArray());
    }

    public static void findAndSaveGalleryToShared(Activity activity){
        ImageSample[] imageSamples = GalleryHelper.fetchGalleryImages(activity); // get all images from gallery

        Gallery gallery = new Gallery(); // create a gallery
        Album album = new Album(); // create a album

        int imageCount = imageSamples.length;
        for(int i=0;i<imageCount;i++){
            ImageSample imageSample = imageSamples[i];
            // create image then set info for it
            MyImage myImage = new MyImage();
            myImage.setUri(imageSample.getPath());
            myImage.setDate(Utility.secondsToLocalDate(imageSample.getDate()).toString());
            myImage.setDescription(imageSample.getNameAlbum());

            gallery.addItem(myImage, Constraints.INDEX_TO_ADD_IMAGE); // add image to gallery
            album.addItem(myImage, Constraints.INDEX_TO_ADD_IMAGE); // add image to album
        }

        saveGalleryToShared(gallery, activity); // save gallery to shared
        saveData(activity, KEY_NAME_ALBUM, album); // save album to shared
    }

}