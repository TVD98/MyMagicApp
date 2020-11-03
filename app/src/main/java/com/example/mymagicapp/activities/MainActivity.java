package com.example.mymagicapp.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.MainPagerAdapter;
import com.example.mymagicapp.helper.Broadcast;
import com.example.mymagicapp.helper.Constraint;
import com.example.mymagicapp.helper.EventManager;
import com.example.mymagicapp.helper.OnShowImageClickedListener;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.MyImage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    public ViewPager2 viewPager2;
    private Broadcast broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBarMain);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        broadcast = new Broadcast();
        IntentFilter filter = new IntentFilter("android.intent.action.SCREEN_OFF");
        registerReceiver(broadcast, filter);

        tabLayout = findViewById(R.id.tabLayoutMain);
        viewPager2 = findViewById(R.id.viewPagerMain);

        viewPager2.setAdapter(new MainPagerAdapter(this));
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
                }
            }
        }
        );
        tabLayoutMediator.attach();

        EventManager.getInstance().setOnShowImageClickedListener(new OnShowImageClickedListener() {
            @Override
            public void onShowImageClicked(Object[] datas) {
                View imageView = (View)datas[0];
                MyImage image = (MyImage)datas[1];
                startShowImage(imageView, image);
            }
        });
    }

    private void startShowImage(View imageView, MyImage image){
        Intent intent = new Intent(this, ShowImageActivity.class);
        Gson gson = new Gson();
        intent.putExtra("IMAGE", gson.toJson(image));
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(imageView, Constraint.TRANSITION_NAME);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
        startActivity(intent, options.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSetting:
                startSetting();
                break;
            default:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSetting() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

}