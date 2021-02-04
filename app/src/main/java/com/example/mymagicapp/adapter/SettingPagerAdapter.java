package com.example.mymagicapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mymagicapp.fragments.CardDataFragment;
import com.example.mymagicapp.fragments.FoodDataFragment;
import com.example.mymagicapp.fragments.OptionDataFragment;

import java.util.ArrayList;
import java.util.List;


public class SettingPagerAdapter extends FragmentStateAdapter {
    List<Fragment> fragments = new ArrayList<>();

    public SettingPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments.add(new CardDataFragment());
        fragments.add(new FoodDataFragment());
        fragments.add(new OptionDataFragment());
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
}
