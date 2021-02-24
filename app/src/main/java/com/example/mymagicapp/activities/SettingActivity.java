package com.example.mymagicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.fragments.AlbumFragment;
import com.example.mymagicapp.helper.IOnFragmentManager;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.models.ItemAlbum;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class SettingActivity extends AppCompatActivity implements IOnFragmentManager {
    private static final int MY_READ_PERMISSION_CODE = 101;
    private List<ItemAlbum> itemAlbums = new ArrayList<>();
    private int itemSelected = -1;
    private AlbumFragment albumFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("Setting");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        albumFragment = new AlbumFragment(SaveSystem.KEY_NAME_DATA_ALBUM);
        fragmentTransaction.add(R.id.fragment_container, albumFragment, "ALBUM");
        fragmentTransaction.commit();
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

    @Override
    public void onItemClick(ItemAlbum itemAlbum) {
        Intent intent = new Intent(this, ShowAlbumDataActivity.class);
        Gson gson = new Gson();
        String albumInfo = gson.toJson(itemAlbum);
        intent.putExtra("ITEM_ALBUM", albumInfo);
        startActivity(intent);
    }
}