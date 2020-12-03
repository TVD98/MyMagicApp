package com.example.mymagicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.helper.Utility;

public class SettingActivity extends AppCompatActivity {
    private static final int MY_READ_PERMISSION_CODE = 101;
    Button buttonSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("Setting");

        buttonSync = findViewById(R.id.button_sync);

        buttonSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncGallery();
            }
        });
    }

    public void syncGallery(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_READ_PERMISSION_CODE);
        }
        else{
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
        if(requestCode == MY_READ_PERMISSION_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startSyncingGallery();
            }
        }
    }

}