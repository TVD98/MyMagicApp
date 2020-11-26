package com.example.mymagicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.ShowImagePagerAdapter;
import com.example.mymagicapp.helper.EventManager;
import com.example.mymagicapp.helper.OnPhotoViewClickedListener;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.MyImage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        if (bundle.getString("IMAGE") != null) {
            String json = bundle.getString("IMAGE");
            currentImage = gson.fromJson(json, MyImage.class); // get information of clicked image
        }

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = findViewById(R.id.viewPagerShowImage);

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
                changeTitleToolBar(position); // Title changes according to the image
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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    private void init() {
        imageList = Utility.getData(this, Utility.KEY_NAME_IMAGE_LIST, MyImage[].class);
        if(imageList == null)
            imageList = new MyImage[0];
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private void showSystemUI() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void changeTitleToolBar(int pos) {
        getSupportActionBar().setTitle(imageList[pos].getTitle());
    }

    private int getCurrentImageId() {
        int len = imageList.length;
        for (int i = 0; i< len; i++)
        {
            if(imageList[i].compareTo(currentImage)==0)
                return i;
        }
        return 0;
    }
}