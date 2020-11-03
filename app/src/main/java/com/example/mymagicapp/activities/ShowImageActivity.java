package com.example.mymagicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.ShowImagePagerAdapter;
import com.example.mymagicapp.helper.EventManager;
import com.example.mymagicapp.helper.OnPhotoViewClickedListener;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.MyImage;
import com.example.mymagicapp.models.MyImageBuilder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ShowImageActivity extends AppCompatActivity {
    public List<MyImage> imageList = new ArrayList<>();
    public ViewPager viewPager;
    public BottomNavigationView bottomNavigationView;
    private MyImage currentImage;
    private boolean show = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        Gson gson = new Gson();
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("IMAGE") != null) {
            String json = bundle.getString("IMAGE");
            currentImage = gson.fromJson(json, MyImage.class);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = findViewById(R.id.viewPagerShowImage);
//        setLayoutViewPager(viewPager);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        init();

        int currentId = getCurrentImageId();
        changeTitleToolBar(currentId);

        viewPager.setAdapter(new ShowImagePagerAdapter(imageList, this));
        viewPager.setCurrentItem(currentId);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTitleToolBar(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        EventManager.getInstance().setOnPhotoViewClickedListener(new OnPhotoViewClickedListener() {
            @Override
            public void onPhotoViewClicked() {
                if (show) {
                    hideSystemUI();
                    bottomNavigationView.setVisibility(View.INVISIBLE);
                    viewPager.setBackgroundColor(getResources().getColor(R.color.black));
                    show = false;
                } else {
                    showSystemUI();
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    viewPager.setBackgroundColor(getResources().getColor(R.color.white));
                    show = true;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    private void init() {
        String json = Utility.getData(this, Utility.KEY_NAME_IMAGE_LIST);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<MyImage>>() {
            }.getType();
            List<MyImage> list = gson.fromJson(json, type);
            imageList.addAll(list);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        bottomNavigationView.setVisibility(View.INVISIBLE);
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        bottomNavigationView.setVisibility(View.INVISIBLE);
        super.onBackPressed();
    }

    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


    }

    private void changeTitleToolBar(int pos) {
        getSupportActionBar().setTitle(imageList.get(pos).getTitle());
    }

    private void setLayoutViewPager(View imageView) {
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = Utility.widthOfImage(this);
        layoutParams.height = Utility.widthOfImage(this);
        imageView.setLayoutParams(layoutParams);
    }

    private int getCurrentImageId() {
        for (int i = 0; i < imageList.size(); i++) {
            if (imageList.get(i).compareTo(currentImage) == 0)
                return i;
        }
        return -1;
    }
}