package com.example.mymagicapp.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mymagicapp.models.Album;
import com.example.mymagicapp.models.Gallery;
import com.example.mymagicapp.models.MyImage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Utility {
    public static final int SPAN_COUNT_ITEM_IMAGE = 4;
    public static final String SHARE_PREFERENCES_NAME = "TVD";
    public static final String KEY_NAME_COLLECTION = "KEY_NAME_COLLECTION";
    public static final String KEY_NAME_ALBUM = "KEY_NAME_ALBUM";
    public static final String KEY_NAME_IMAGE_LIST = "KEY_NAME_IMAGE_LIST";
    public static final int DEFAULT_IMAGE_ID = 0;
    public static final int DEFAULT_INDEX_TO_ADD = -1;
    public static final int INDEX_TO_ADD_IMAGE = 100;

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

    public static void saveData(Context context, String keyDataName, Object data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString(keyDataName, json);
        editor.apply();
    }

    public static <T> T getData(Context context, String keyNameData, Type type) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString(keyNameData, null);
        if (data == null)
            return null;
        else {
            Gson gson = new Gson();
            return gson.fromJson(data, type);
        }
    }

    public static void saveRealGalleryToSharedAndFirebase(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_date_added, column_index_display_name;

        Gallery gallery = new Gallery(); // create gallery
        Album album = new Album(); // create album

        String absolutePathOfImage = null;
        String nameAlbumOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_ADDED,};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, "date_added DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_date_added = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED);
        column_index_display_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            nameAlbumOfImage = cursor.getString(column_index_display_name);

            long seconds = Long.parseLong(cursor.getString(column_index_date_added));
            LocalDate date = secondsToLocalDate(seconds); // get date added

            MyImage image = new MyImage(absolutePathOfImage);
            image.setDate(date.toString()); // set date of image

            gallery.addItem(image, Utility.INDEX_TO_ADD_IMAGE); // add image to gallery
            album.addImage(image, nameAlbumOfImage); // add image to album
        }
        saveGallery(gallery, activity); // save gallery to shared
        saveData(activity, Utility.KEY_NAME_ALBUM, album); // save album to shared

//        saveGalleryToFirebase(gallery);
    }

    public static LocalDate secondsToLocalDate(long seconds) {
        return Instant.ofEpochSecond(seconds).atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDate();
    }

    public static void saveGalleryToFirebase(Gallery gallery) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        List<MyImage> images = gallery.toImageArray();
        for (MyImage image : images
        ) {
            Uri file = Uri.fromFile(new File(image.getUri()));
            String[] split = image.getUri().split("/");
            StorageReference riversRef = storageRef.child("images/" + split[split.length - 1]);

            riversRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }
    }

    public static void saveGallery(Gallery gallery, Context context){
        saveData(context, Utility.KEY_NAME_COLLECTION, gallery);
        saveData(context, Utility.KEY_NAME_IMAGE_LIST, gallery.toImageArray().toArray());
    }
}
