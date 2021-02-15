package com.example.mymagicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.RecyclerViewAlbumAdapter;
import com.example.mymagicapp.adapter.RecyclerViewDataAdapter;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.RecyclerItemClickListener;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.models.ItemAlbum;
import com.example.mymagicapp.models.MyImage;

import java.util.ArrayList;
import java.util.List;


public class SettingActivity extends AppCompatActivity {
    private static final int MY_READ_PERMISSION_CODE = 101;
    private RecyclerView recyclerView;
    private RecyclerViewAlbumAdapter adapter;
    private List<ItemAlbum> itemAlbums = new ArrayList<>();
    private int itemSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("Setting");

        recyclerView = findViewById(R.id.recyclerViewSetting);

        init();

        adapter = new RecyclerViewAlbumAdapter(itemAlbums, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                itemSelected = position;
                showItemAlbum(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
        adapter.notifyItemChanged(itemSelected);
    }

    private void showItemAlbum(int position) {
        Intent intent = new Intent(this, ShowItemAlbumActivity.class);
        intent.putExtra("DATA_ID", position);
        startActivity(intent);
    }

    private void init() {
        itemAlbums.clear();
        itemAlbums.add(SaveSystem.getDataAlbum(this, Constraints.CARD_DATA_ID));
        itemAlbums.add(SaveSystem.getDataAlbum(this, Constraints.FOOD_DATA_ID));
        itemAlbums.add(SaveSystem.getDataAlbum(this, Constraints.OPTION_DATA_ID));
    }

    private void syncGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,},
                    MY_READ_PERMISSION_CODE);
        } else {
            startSyncingGallery();
        }
    }

    private void startSyncingGallery() {
        SaveSystem.findAndSaveGalleryToShared(this);
        Toast.makeText(this, "Đồng bộ với bộ sưu tập thành công", Toast.LENGTH_SHORT).show(); // display successful sync
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_READ_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSyncingGallery();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSync:
                syncGallery();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}