package com.example.mymagicapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.SettingPagerAdapter;
import com.example.mymagicapp.helper.Constraint;
import com.example.mymagicapp.helper.EventManager;
import com.example.mymagicapp.helper.ISettingFragment;
import com.example.mymagicapp.helper.OnUploadImageClickedListener;
import com.example.mymagicapp.helper.Utility;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SettingActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    public ViewPager2 viewPager2;
    public SettingPagerAdapter adapter;
    private int selectedPager = 0;
    private int colIndex = 0;
    private int imgIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("Setting");

        tabLayout = findViewById(R.id.tabLayoutSetting);
        viewPager2 = findViewById(R.id.viewPagerSetting);

        adapter = new SettingPagerAdapter(this);
        viewPager2.setAdapter(adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Ảnh");
                        break;
                    case 1:
                        tab.setText("Album");
                        break;
                    case 2:
                        tab.setText("Dữ liệu");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                selectedPager = position;
            }
        });
        EventManager.getInstance().setOnUploadImageClickedListener(new OnUploadImageClickedListener() {
            @Override
            public void OnUploadImageClicked(int colId, int imgId, int code) {
                colIndex = colId;
                imgIndex = imgId;
                if (code == Constraint.CODE_IMG_GALLERY) {
                    loadImage();
                } else {
                    loadImages();
                }
            }
        });
    }

    private void loadImages() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Chọn nhiều ảnh"),
                Constraint.CODE_MULTIPLE_IMG_GALLERY);
    }

    private void loadImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, Constraint.CODE_IMG_GALLERY);
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
            case R.id.itemSave:
                save();
                break;
            case R.id.itemAdd:
                addItem();
                break;
            case R.id.itemRemove:
                removeItem();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constraint.CODE_MULTIPLE_IMG_GALLERY && resultCode == RESULT_OK) {
            final ClipData clipData = data.getClipData();
            if (clipData == null)
                return;
            ISettingFragment fragment = adapter.getFragment(selectedPager);
            fragment.addImages(colIndex, clipData);
        } else if (requestCode == Constraint.CODE_IMG_GALLERY && resultCode == RESULT_OK) {
            final Uri uri = data.getData();
            ISettingFragment fragment = adapter.getFragment(selectedPager);
            fragment.setImage(colIndex, imgIndex, uri.toString());
        }
    }

    private void addItem() {
        ISettingFragment fragment = adapter.getFragment(selectedPager);
        fragment.addItem();
    }

    private void removeItem() {
        ISettingFragment fragment = adapter.getFragment(selectedPager);
        fragment.removeItem();
    }

    private void save() {
        ISettingFragment fragment = adapter.getFragment(selectedPager);
        fragment.saveData();
    }

}