package com.example.mymagicapp.fragments;

import android.content.ClipData;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.ISettingFragment;

public class DataSettingFragment extends Fragment implements ISettingFragment {

    public DataSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_setting, container, false);
    }

    @Override
    public void addItem() {

    }

    @Override
    public void addImages(int position, ClipData clipData) {

    }

    @Override
    public void removeItem() {

    }

    @Override
    public void setImage(int colPos, int imgPos, String uri) {

    }

    @Override
    public void saveData() {

    }
}