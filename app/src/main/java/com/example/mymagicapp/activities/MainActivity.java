package com.example.mymagicapp.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.MainPagerAdapter;
import com.example.mymagicapp.fragments.AlbumFragment;
import com.example.mymagicapp.fragments.CollectionFragment;
import com.example.mymagicapp.helper.Broadcast;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.FirebaseSingleton;
import com.example.mymagicapp.helper.IOnFragmentManager;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.Album;
import com.example.mymagicapp.models.Code;
import com.example.mymagicapp.models.ItemAlbum;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements IOnFragmentManager {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private Broadcast broadcast;
    private MainPagerAdapter mainPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBarMain);
        toolbar.setTitle(""); // clear title of toolbar
        setSupportActionBar(toolbar);

        // Create one broadcast to receive screen off action
        broadcast = new Broadcast();
        IntentFilter filter = new IntentFilter("android.intent.action.SCREEN_OFF");
        registerReceiver(broadcast, filter);

        tabLayout = findViewById(R.id.tabLayoutMain);
        viewPager2 = findViewById(R.id.viewPagerMain);

        attachFragments();

        String codeId = SaveSystem.getString(this, SaveSystem.CODE);
        if (codeId != null) {
            FirebaseSingleton.getInstance().database.child("codes").child(codeId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot != null) {
                        Code code = snapshot.getValue(Code.class);
                        if (code == null) {
                            SaveSystem.removeAll(MainActivity.this);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void attachFragments() {
        mainPagerAdapter = new MainPagerAdapter(this);
        mainPagerAdapter.add(new CollectionFragment());
        mainPagerAdapter.add(new AlbumFragment(SaveSystem.KEY_NAME_ALBUM));
        viewPager2.setAdapter(mainPagerAdapter);
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
        });
        tabLayoutMediator.attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSetting:
                if (hasBeenUnlocked())
                    startSettingActivity();
                else checkUserName();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean hasBeenUnlocked() {
        String codeId = SaveSystem.getString(this, SaveSystem.CODE);
        if (codeId != null)
            return true;
        return false;
    }

    private void startSettingActivity() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    private void checkUserName() {
        FirebaseSingleton.getInstance().database.child("codes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                String deviceId = Utility.getDeviceId(MainActivity.this);
                boolean unlock = false;
                for (DataSnapshot child : children
                ) {
                    Code code = child.getValue(Code.class);
                    if (code.getUserName().compareTo(deviceId) == 0 && !code.isReady()) {
                        unlock = true;
                        SaveSystem.unlockApp(MainActivity.this, code.getId());
                        startSettingActivity();
                        break;
                    }
                }
                if (!unlock)
                    startShopActivity();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void startShopActivity() {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            changeDataId(-1);
        }
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            changeDataId(1);
        }
        return true;
    }

    private void changeDataId(int result) {
        int id = SaveSystem.getDataId(this);
        id += result;
        if (id < 0)
            id = Constraints.MAX_DATA_COUNT;
        if (id >= Constraints.MAX_DATA_COUNT)
            id = 0;
        Toast.makeText(this, Integer.toString(id), Toast.LENGTH_LONG).show();
        SaveSystem.saveDataId(id, this);
    }

    public Fragment getCurrentFragment() {
        return mainPagerAdapter.getCurrentFragment(viewPager2.getCurrentItem());
    }

    @Override
    public void onItemClick(Album album, int position) {
        Intent intent = new Intent(this, ShowAlbumActivity.class);
        Gson gson = new Gson();
        String albumInfo = gson.toJson(album);
        intent.putExtra("ALBUM", albumInfo);
        intent.putExtra("POSITION", position);
        startActivity(intent);
    }

}