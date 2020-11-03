package com.example.mymagicapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mymagicapp.fragments.AlbumMainFragment;
import com.example.mymagicapp.fragments.CollectionMainFragment;


public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new CollectionMainFragment();
            default:
                return new AlbumMainFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
