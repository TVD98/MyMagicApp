package com.example.mymagicapp.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.RecyclerViewAdapter;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.Gallery;
import com.example.mymagicapp.models.MyImage;
import com.google.gson.Gson;

public class CollectionMainFragment extends Fragment {
    public RecyclerView recyclerViewMain;
    public Gallery gallery;

    public CollectionMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        recyclerViewMain.setAdapter(new RecyclerViewAdapter(gallery.toArray(), getActivity()));
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void init() {
        gallery = SaveSystem.getData(getActivity(), SaveSystem.KEY_NAME_COLLECTION, Gallery.class);
        if(gallery == null)
            gallery = new Gallery();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_collection_main, container, false);
        recyclerViewMain = v.findViewById(R.id.recyclerViewMain);
        return v;
    }

}