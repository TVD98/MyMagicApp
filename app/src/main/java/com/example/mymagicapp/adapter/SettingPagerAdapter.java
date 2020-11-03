package com.example.mymagicapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mymagicapp.fragments.AlbumSettingFragment;
import com.example.mymagicapp.fragments.CollectionSettingFragment;
import com.example.mymagicapp.fragments.DataSettingFragment;
import com.example.mymagicapp.helper.ISettingFragment;

import java.util.ArrayList;
import java.util.List;

public class SettingPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    public SettingPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragmentList.add(new CollectionSettingFragment());
        fragmentList.add(new AlbumSettingFragment());
        fragmentList.add(new DataSettingFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public ISettingFragment getFragment(int pos){
        return (ISettingFragment)fragmentList.get(pos);
    }



}
