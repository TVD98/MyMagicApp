package com.example.mymagicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.ShowImagePagerAdapter;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.models.MyImage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;


public class ShowImageActivity extends AppCompatActivity {
    public MyImage[] imageList;
    public ViewPager viewPager;
    public BottomNavigationView bottomNavigationView;
    public Toolbar toolbar;
    private MyImage currentImage;
    private boolean show = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        Gson gson = new Gson();
        Bundle bundle = getIntent().getExtras();
        if (bundle.getStringArray("DATAS") != null) {
            String[] datas = bundle.getStringArray("DATAS");
            currentImage = gson.fromJson(datas[0], MyImage.class); // get information of clicked image
            imageList = gson.fromJson(datas[1], MyImage[].class);
        }

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = findViewById(R.id.viewPagerShowImage);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        int currentId = getCurrentImageIndex();
        changeTitleToolBar(currentId);

        viewPager.setAdapter(new ShowImagePagerAdapter(imageList, this));
        viewPager.setCurrentItem(currentId);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTitleToolBar(position); // Title changes according to the image
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void updateUI() {
        if (show) {
            hideSystemUI();
            toolbar.setVisibility(View.INVISIBLE);
            bottomNavigationView.setVisibility(View.INVISIBLE);
            viewPager.setBackgroundColor(getResources().getColor(R.color.black));
            show = false;
        } else {
            showSystemUI();
            toolbar.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.VISIBLE);
            viewPager.setBackgroundColor(getResources().getColor(R.color.white));
            show = true;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        toolbar.setVisibility(View.INVISIBLE);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        super.onBackPressed();
    }

    private void hideSystemUI() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private void showSystemUI() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void changeTitleToolBar(int pos) {
        MyImage image = imageList[pos];
        getSupportActionBar().setTitle(image.title());
        getSupportActionBar().setSubtitle(image.subTitle());
    }

    private int getCurrentImageIndex() {
        int len = imageList.length;
        for (int i = 0; i < len; i++) {
            if (imageList[i].getUri().compareTo(currentImage.getUri()) == 0) // the same uri
                return i;
        }
        return 0;
    }
}