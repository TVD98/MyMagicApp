package com.example.mymagicapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.IRecyclerViewImage;
import com.example.mymagicapp.helper.RecyclerViewFactory;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.models.Album;
import com.example.mymagicapp.models.ItemAlbum;
import com.example.mymagicapp.models.MyImage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ShowAlbumActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public List<MyImage> images = new ArrayList<>();
    public IRecyclerViewImage adapter;
    public RecyclerView recyclerView;
    public FloatingActionButton fab;
    public BottomNavigationView bottomNavigationView;
    public Album album;
    public ItemAlbum itemAlbum;
    public int itemSelected;
    private boolean removeMode = false;
    private static final int PICK_IMAGES = 1;
    private static final int PICK_IMAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_album);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getStringArray("ALBUM_INFO") != null) {
            String[] albumInfo = bundle.getStringArray("ALBUM_INFO");
            Gson gson = new Gson();
            int position = Integer.parseInt(albumInfo[0]);
            album = gson.fromJson(albumInfo[1], Album.class);
            itemAlbum = album.getItem(position);
            setTitle(itemAlbum.getName());
            images = itemAlbum.getImageList();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        fab = findViewById(R.id.fab);
        if (Constraints.couldAddImages(album.getName(), itemAlbum.getName()))
            fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImagesFromGallery();
            }
        });

        recyclerView = findViewById(R.id.recyclerViewShowAlbum);

        adapter = RecyclerViewFactory.getRecyclerViewImage(album.getName(), images, this);
        recyclerView.setAdapter((RecyclerView.Adapter) adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, Constraints.SPAN_COUNT_ITEM_IMAGE));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void pickImagesFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES);
    }

    public void pickImageFromGallery(int position) {
        setItemSelected(position);
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.show_album_menu, menu);
        if (!removeMode)
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);
        }
        else{
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_cancel:
                adapter.setRemoveMode(false);
                break;
            case R.id.item_select_all:
                adapter.selectAll();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGES) {
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                addImages(clipData);
            } else {
                Uri uri = data.getData();
                if (uri != null) {
                    addImage(uri);
                    saveAlbum();
                }
            }
        } else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri uri = data.getData();
            changeImage(uri);
        }
    }


    private void addImages(ClipData clipData) {
        for (int i = 0; i < clipData.getItemCount(); i++) {
            ClipData.Item item = clipData.getItemAt(i);
            Uri uri = item.getUri();
            addImage(uri);
        }
        saveAlbum();
    }

    private void addImage(Uri uri) {
        MyImage image = new MyImage();
        image.setUri(uri.toString());
        itemAlbum.addItem(image, Constraints.DEFAULT_INDEX_TO_ADD);
        ((RecyclerView.Adapter) adapter).notifyItemInserted(itemAlbum.size() - 1);
    }

    public void changeImage(Uri uri) {
        MyImage image = itemAlbum.getItem(itemSelected);
        image.setUri(uri.toString());
        ((RecyclerView.Adapter) adapter).notifyItemChanged(itemSelected);
        saveAlbum();
    }

    public void setItemSelected(int id) {
        itemSelected = id;
    }

    public void saveAlbum() {
        SaveSystem.saveData(this, album.getName(), album);
    }

    public void sortItemAlbum() {
        itemAlbum.sortByName();
    }

    public void updateUI(boolean removeMode, int count) {
        this.removeMode = removeMode;
        invalidateOptionsMenu();
        updateToolBar(count);
        if (removeMode) {
            fab.setVisibility(View.INVISIBLE);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            if (Constraints.couldAddImages(album.getName(), itemAlbum.getName()))
                fab.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.INVISIBLE);
        }
    }

    public void updateToolBar(int count){
        if(this.removeMode){
            setTitle(String.format("%s mục", count));
        }
        else setTitle(itemAlbum.getName());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_remove:
                showDialogToRemoveImages();
        }
        return false;
    }

    public void showDialogToRemoveImages() {
        Integer[] indexs = adapter.getImageIdListToRemove();
        if (indexs.length > 0) {
            String message = String.format("Xóa %d mục?", indexs.length);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message);
            builder.setPositiveButton("Yes", new Dialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    removeImages(indexs);
                    dialog.cancel();
                }
            });

            builder.setNegativeButton("No", new Dialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    private void removeImages(Integer[] indexs) {
        for (int i = 0; i < indexs.length; i++) {
            int indexRemove = indexs[i] - i;
            itemAlbum.removeItem(indexRemove);
        }
        adapter.clearCheckBox();
        updateToolBar(0);
        ((RecyclerView.Adapter)adapter).notifyDataSetChanged();
        saveAlbum();
    }
}