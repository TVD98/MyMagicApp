package com.example.mymagicapp.activities;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.RecyclerViewItemAdapter;

public class ShowAlbumActivity extends BaseShowAlbumActivity {
    private RecyclerViewItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_show_album;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        if(adapter == null);
        adapter = new RecyclerViewItemAdapter(images, this);
        return adapter;
    }
}