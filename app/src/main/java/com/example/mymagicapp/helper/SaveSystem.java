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
    public static final String KEY_NAME_COLLECTION = "collection";
    public static final String KEY_NAME_ALBUM = "album";
    public static final String KEY_NAME_DATA_ALBUM = "data_album";
    public static final String KEY_NAME_SPECIAL_IMAGE = "special_image";
    public static final String DEVICE_ID = "mac_address";
    public static final String DATA_ID = "data_id";

    public static void saveData(Context context, String keyDataName, Object data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        saveString(context, keyDataName, json);
    }

    public static void saveString(Context context, String keyDataName, String data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCES_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(keyDataName, data);
        editor.apply();
    }

    public static <T> T getData(Context context, String keyDataName, Type type) {
        String data = getString(context, keyDataName);
        if (data == null)
            return null;
        else {
            try {
                Gson gson = new Gson();
                return gson.fromJson(data, type);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static String getString(Context context, String keyDataName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyDataName, null);
    }

    public static void saveGalleryToShared(Gallery gallery, Context context) {
        saveData(context, KEY_NAME_COLLECTION, gallery);
    }

    public static void saveAllDataAlbum(Context context) {
        Album album = Constraints.getDataAlbum();
        saveData(context, KEY_NAME_DATA_ALBUM, album);
    }


    public static void findAndSaveGalleryToShared(Activity activity) {
        ImageSample[] imageSamples = GalleryHelper.fetchGalleryImages(activity); // get all images from gallery

        Gallery gallery = new Gallery(); // create a gallery
        Album album = new Album(); // create a album
        album.setName("album");

        int imageCount = imageSamples.length;
        for (int i = 0; i < imageCount; i++) {
            ImageSample imageSample = imageSamples[i];
            // create image then set info for it
            MyImage myImage = new MyImage();
            myImage.setUri(imageSample.getPath());
            myImage.setDate(imageSample.getDate());
            myImage.setDescription(imageSample.getNameAlbum());

            gallery.addItem(myImage, Constraints.INDEX_TO_ADD_IMAGE); // add image to gallery
            album.addItem(myImage, Constraints.INDEX_TO_ADD_IMAGE); // add image to album
        }

        saveGalleryToShared(gallery, activity); // save gallery to shared
        saveData(activity, KEY_NAME_ALBUM, album); // save album to shared
    }

    public static void saveDataId(int id, Context context) {
        saveString(context, DATA_ID, Integer.toString(id));
    }

    public static int getDataId(Context context) {
        String id = getString(context, DATA_ID);
        if (id != null)
            return Integer.parseInt(id);
        else return Constraints.CARD_DATA_ID;
    }

    public static void unlockApp(Context context) {
        // save mac address
        String macAddress = Utility.getDeviceId(context);
        SaveSystem.saveString(context, SaveSystem.DEVICE_ID, macAddress);

        // unlock Gadgets
        SaveSystem.saveAllDataAlbum(context); // create image data

    }

}