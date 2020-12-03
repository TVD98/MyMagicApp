package com.example.mymagicapp.helper;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class GalleryHelper {

    public static ImageSample[] fetchGalleryImages(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_date_added, column_index_display_name;

        String absolutePathOfImage = null;
        String nameAlbumOfImage = null;
        String dateAddedOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_ADDED,};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, "date_added DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_date_added = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED);
        column_index_display_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        int imageCount = cursor.getCount();
        ImageSample[] imageSamples = new ImageSample[imageCount];

        int count = 0;
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            nameAlbumOfImage = cursor.getString(column_index_display_name);
            dateAddedOfImage = cursor.getString(column_index_date_added);

            ImageSample imageSample = new ImageSample(absolutePathOfImage, dateAddedOfImage, nameAlbumOfImage);
            imageSamples[count] = imageSample;

            count++;
        }

        return imageSamples;
    }

}

// The object has attributes: absolute path, name album and date added of image.
class ImageSample{
    private String path;
    private String dateAdded;
    private String nameAlbum;

    public ImageSample(String path, String date, String nameAlbum) {
        this.path = path;
        this.dateAdded = date;
        this.nameAlbum = nameAlbum;
    }

    public String getPath() {
        return path;
    }

    public String getDate() {
        return dateAdded;
    }

    public String getNameAlbum() {
        return nameAlbum;
    }
}


