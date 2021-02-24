package com.example.mymagicapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.CheckBoxImageHelper;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.ItemClickSupport;
import com.example.mymagicapp.models.ItemAlbum;
import com.example.mymagicapp.models.MyImage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseShowAlbumActivity extends AppCompatActivity {
    protected List<MyImage> images = new ArrayList<>();
    protected RecyclerView recyclerView;
    protected BottomNavigationView navigationView;
    protected ItemAlbum itemAlbum;
    protected CheckBoxImageHelper checkSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("ITEM_ALBUM") != null) {
            String albumInfo = bundle.getString("ITEM_ALBUM");
            Gson gson = new Gson();
            itemAlbum = gson.fromJson(albumInfo, ItemAlbum.class);
            setTitle(itemAlbum.getName());
            images = itemAlbum.getImageList();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerView = findViewById(R.id.recyclerViewShowAlbum);
        navigationView = findViewById(R.id.bottomNavigation);

        recyclerView.setAdapter(getAdapter());
        recyclerView.setLayoutManager(new GridLayoutManager(this, Constraints.SPAN_COUNT_ITEM_IMAGE));
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }

            @Override
            public void onItemDoubleClicked(RecyclerView recyclerView, int position, View v) {
                saveAlbum();
            }
        });

        checkSupport = new CheckBoxImageHelper(false);
    }

    protected void saveAlbum(){

    }

    protected void updateUI(){
        if(checkSupport.getCheckMode())
            navigationView.setVisibility(View.VISIBLE);
        else navigationView.setVisibility(View.INVISIBLE);
    }

    public abstract int getLayoutResourceId();

    public abstract RecyclerView.Adapter getAdapter();

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
