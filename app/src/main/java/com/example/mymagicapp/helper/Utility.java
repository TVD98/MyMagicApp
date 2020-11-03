package com.example.mymagicapp.helper;

import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.mymagicapp.models.Album;
import com.example.mymagicapp.models.CollectionOfDay;
import com.example.mymagicapp.models.MyImage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Utility {
    public static final int SPAN_COUNT_ITEM_IMAGE = 4;
    public static final String SHARE_PREFERENCES_NAME = "TVD";
    public static final String KEY_NAME_COLLECTION = "KEY_NAME_COLLECTION";
    public static final String KEY_NAME_ALBUM = "KEY_NAME_ALBUM";
    public static final String KEY_NAME_IMAGE_LIST = "KEY_NAME_IMAGE_LIST";
    public static final String KEY_NAME_DATA = "KEY_NAME_DATA";
    public static final String KEY_NAME_IMAGE_DATA = "KEY_NAME_IMAGE_DATA";

    public static int widthPixelsDp(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int heightPixelsDp(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static int widthOfImage(Context context) {
        return (int) (widthPixelsDp(context) / SPAN_COUNT_ITEM_IMAGE) - 5;
    }

    public static void saveData(Context context, String keyDataName, String data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(keyDataName, data);
        editor.apply();
    }

    public static String getData(Context context, String keyNameData) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString(keyNameData, null);
        return data;
    }

    public static LocalDate stringToDate(String date) {
        if (date.length() == 8) {
            int day = Integer.parseInt(date.subSequence(0, 2).toString());
            int month = Integer.parseInt(date.subSequence(2, 4).toString());
            int year = Integer.parseInt(date.subSequence(4, 8).toString());
            return LocalDate.of(year, month, day);
        } else return LocalDate.MIN;
    }

    public static String dateToString(LocalDate date) {
        String result = "";
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();
        if (day < 10) {
            result += "0" + day;
        } else result += day;
        if (month < 10) {
            result += "0" + month;
        } else result += month;
        result += year;
        return result;
    }

    public static String stringFromCollectionList(List<CollectionOfDay> conversations) {
        Gson gson = new Gson();
        String str = gson.toJson(conversations);
        return str;
    }

    public static List<CollectionOfDay> collectionListFromString(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CollectionOfDay>>() {
        }.getType();
        ArrayList<CollectionOfDay> list = gson.fromJson(json, type);
        return list;
    }

    public static String stringFromAlbumList(List<Album> conversations) {
        Gson gson = new Gson();
        String str = gson.toJson(conversations);
        return str;
    }

    public static List<Album> albumListFromString(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Album>>() {
        }.getType();
        ArrayList<Album> list = gson.fromJson(json, type);
        return list;
    }

    public static void settingDataToUpLoadData(List<CollectionOfDay> collectionList) {
        for (CollectionOfDay collection : collectionList
        ) {
            collection.removeImage(0);
        }
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static void uploadDataToSettingData(List<CollectionOfDay> collectionList) {
        for (CollectionOfDay collection : collectionList
        ) {
            collection.addUploadImage();
        }
    }

    public static void saveImageList(List<CollectionOfDay> collectionList, Context context){
        List<MyImage> imageList = new ArrayList<>();
        for (CollectionOfDay collection: collectionList
        ) {
            imageList.addAll(collection.getImageList());
        }
        Gson gson = new Gson();
        String json = gson.toJson(imageList);
        Utility.saveData(context, Utility.KEY_NAME_IMAGE_LIST, json);
    }
}
