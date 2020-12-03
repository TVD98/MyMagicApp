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

    public static int widthPixelsDp(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int widthOfImage(Context context) {
        return (widthPixelsDp(context) / Constraints.SPAN_COUNT_ITEM_IMAGE) - 5;
    }

    public static LocalDate secondsToLocalDate(long seconds) {
        return Instant.ofEpochSecond(seconds).atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDate();
    }

    public static LocalDate secondsToLocalDate(String seconds) {
        long second = Long.parseLong(seconds);
        return secondsToLocalDate(second);
    }

}