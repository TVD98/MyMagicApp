package com.example.mymagicapp.fragments;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.models.MyImage;

import static android.app.Activity.RESULT_OK;

public class OptionDataFragment extends DefaultDataFragment {
    private final int RESULT_LOAD_IMAGE = 0;
    private final int RESULT_LOAD_IMAGES = 1;
    private final int firstItem = 0;

    public OptionDataFragment(int dataId) {
        super(dataId);
    }

    @Override
    public void onItemClick(View v, int position) {
        if (itemSelected == firstItem) {
            pickImagesFromGallery();
        } else pickImageFromGallery();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

        } else if (requestCode == RESULT_LOAD_IMAGES && resultCode == RESULT_OK && null != data) {
            Uri[] uris = new Uri[1];
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                int itemCount = clipData.getItemCount();
                uris = new Uri[itemCount];
                for (int i = 0; i < itemCount; i++) {
                    uris[i] = clipData.getItemAt(i).getUri();
                }
            }
            else if (data.getData() != null) {
                Uri uri = data.getData();
                uris[0] = uri;
            }
            addImagesToAlbum(uris);
        }
    }

    private void pickImagesFromGallery() {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(i, RESULT_LOAD_IMAGES);
    }

    private void pickImageFromGallery() {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    private void addImagesToAlbum(Uri[] uris) {
        int index = itemAlbum.size();
        for (Uri uri : uris
        ) {
            MyImage image = new MyImage();
            image.setUri(uri.toString());
            image.setName(Integer.toString(index + 1));
            image.setDescription(Integer.toString(dataId));
            itemAlbum.addItem(image, Constraints.DEFAULT_INDEX_TO_ADD);
            index++;
            adapter.notifyItemInserted(index);
        }
        saveItemAlbum();
//        SaveSystem.saveData(getActivity(), Integer.toString(Constraints.CARD_DATA_ID), itemAlbum);
    }
}
