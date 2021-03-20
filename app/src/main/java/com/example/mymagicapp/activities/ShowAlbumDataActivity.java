package com.example.mymagicapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.RecyclerViewItemDataAdapter;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.models.Gallery;
import com.example.mymagicapp.models.MyImage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowAlbumDataActivity extends BaseShowAlbumActivity {
    private RecyclerViewItemDataAdapter adapter = null;
    private FloatingActionButton fab;
    private static final int PICK_IMAGES = 1;
    public static final int PICK_IMAGE = 0;
    private int selectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fab = findViewById(R.id.fab);
        if (Constraints.couldAddImages(itemAlbum.getName())) {
            fab.setVisibility(View.VISIBLE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImages();
            }
        });
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_show_album_data;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (adapter == null) ;
        adapter = new RecyclerViewItemDataAdapter(images, this);
        return adapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.album_data_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save:
                Toast.makeText(this, "alo", Toast.LENGTH_SHORT).show();
                cleanGallery();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cleanGallery() {
        Gallery gallery = SaveSystem.getData(this, SaveSystem.KEY_NAME_COLLECTION, Gallery.class);
        gallery.removeImages(itemAlbum);
        SaveSystem.saveGalleryToShared(gallery, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri uri = clipData.getItemAt(i).getUri();
                    MyImage image = new MyImage();
                    image.setUri(uriToString(uri));
                    itemAlbum.addImage(image);
                    saveAlbum();
                }
            } else if (data.getData() != null) {
                Uri uri = data.getData();
                MyImage image = new MyImage();
                image.setUri(uriToString(uri));
                itemAlbum.addImage(image);
                saveAlbum();
            }
            adapter.notifyDataSetChanged();
        } else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                Uri uri = data.getData();
                MyImage image = itemAlbum.getItem(selectedPosition);
                image.setUri(uriToString(uri));
                adapter.notifyItemChanged(selectedPosition);
                saveAlbum();
            }
        }

    }

    private void pickImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGES);
    }

    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ShowAlbumDataActivity.PICK_IMAGE);
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    private String uriToString(Uri uri){
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }


}