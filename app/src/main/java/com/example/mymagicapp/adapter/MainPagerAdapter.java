package com.example.mymagicapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mymagicapp.fragments.AlbumFragment;
import com.example.mymagicapp.fragments.CollectionFragment;
import com.example.mymagicapp.helper.SaveSystem;

import java.util.ArrayList;
import java.util.List;


public class MainPagerAdapter extends FragmentStateAdapter {
    List<Fragment> fragments = new ArrayList<>();

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments.add(new CollectionFragment());
        fragments.add(new AlbumFragment(SaveSystem.KEY_NAME_ALBUM));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public Fragment getCurrentFragment(int pos){
        return fragments.get(pos);
    }

}
