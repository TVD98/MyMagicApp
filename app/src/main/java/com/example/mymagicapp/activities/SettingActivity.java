package com.example.mymagicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.MainPagerAdapter;
import com.example.mymagicapp.adapter.SettingPagerAdapter;
import com.example.mymagicapp.helper.SaveSystem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class SettingActivity extends AppCompatActivity {
    private static final int MY_READ_PERMISSION_CODE = 101;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private SettingPagerAdapter settingPagerAdapter;
    private boolean modeRemove = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("Setting");

        tabLayout = findViewById(R.id.tabLayoutSetting);
        viewPager2 = findViewById(R.id.viewPagerSetting);

        attachFragments();
    }

    private void attachFragments() {
        settingPagerAdapter = new SettingPagerAdapter(this);
        viewPager2.setAdapter(settingPagerAdapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Card");
                        break;
                    case 1:
                        tab.setText("Food");
                        break;
                    case  2:
                        tab.setText("Option");
                        break;
                }
            }
        }
        );
        tabLayoutMediator.attach();
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
        switch (item.getItemId()){
            case R.id.itemSync:
                syncGallery();
                break;
            case R.id.itemRemove:
                changeModeRemove();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isModeRemove() {
        return modeRemove;
    }

    public void changeModeRemove() {
        modeRemove = !modeRemove;
        Toast.makeText(this, "Mode remove: " + Boolean.toString(modeRemove), Toast.LENGTH_SHORT).show();
    }
}