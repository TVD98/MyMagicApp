package com.example.mymagicapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.RecyclerViewDataAdapter;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.models.ItemAlbum;

public class FoodDataFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerViewDataAdapter adapter;
    ItemAlbum album;

    public FoodDataFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food_data, container, false);
        recyclerView = v.findViewById(R.id.recyclerViewSettingFood);

        init();

        adapter = new RecyclerViewDataAdapter(album.toArray(), getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constraints.SPAN_COUNT_ITEM_IMAGE));
        return v;
    }

    private void init() {
        album = SaveSystem.getDataAlbum(getActivity(), 1);
    }
}