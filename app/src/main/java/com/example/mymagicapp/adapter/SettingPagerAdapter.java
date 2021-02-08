package com.example.mymagicapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mymagicapp.fragments.DefaultDataFragment;
import com.example.mymagicapp.fragments.OptionDataFragment;
import com.example.mymagicapp.helper.Constraints;

import java.util.ArrayList;
import java.util.List;


public class SettingPagerAdapter extends FragmentStateAdapter {
    List<Fragment> fragments = new ArrayList<>();

    public SettingPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments.add(new DefaultDataFragment(Constraints.CARD_DATA_ID));
        fragments.add(new DefaultDataFragment(Constraints.FOOD_DATA_ID));
        fragments.add(new OptionDataFragment(Constraints.OPTION_DATA_ID));
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
