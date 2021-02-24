package com.example.mymagicapp.activities;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.RecyclerViewItemDataAdapter;
import com.example.mymagicapp.helper.Constraints;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowAlbumDataActivity extends BaseShowAlbumActivity {
    private RecyclerViewItemDataAdapter adapter = null;
    private FloatingActionButton fab;
    private static final int PICK_IMAGES = 1;
    private static final int PICK_IMAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fab = findViewById(R.id.fab);
        if(Constraints.couldAddImages(itemAlbum.getName())){
            fab.setVisibility(View.VISIBLE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_show_album_data;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        if(adapter == null);
            adapter = new RecyclerViewItemDataAdapter(images, this);
        return adapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}