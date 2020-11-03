package com.example.mymagicapp.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.RecyclerViewMainAdapter;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.CollectionOfDay;
import com.example.mymagicapp.models.CollectionOfDayBuilder;
import com.example.mymagicapp.models.MyImageBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionMainFragment extends Fragment {

    public RecyclerView recyclerViewMain;
    public List<CollectionOfDay> collectionList = new ArrayList<>();

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

        recyclerViewMain.setAdapter(new RecyclerViewMainAdapter(collectionList, getActivity()));
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void init() {
        String json = Utility.getData(getActivity(), Utility.KEY_NAME_COLLECTION);
        if (json != null) {
            List<CollectionOfDay> uploadCollectionList = Utility.collectionListFromString(json);
            collectionList.addAll(uploadCollectionList);
        }
        String jsonImageData = Utility.getData(getActivity(), Utility.KEY_NAME_IMAGE_DATA);
        if (jsonImageData != null) {
            Gson gson = new Gson();
            CollectionOfDay collectionData = gson.fromJson(jsonImageData, CollectionOfDay.class);
            int index = -1;
            for (int i = 0; i < collectionList.size(); i++) {
                if (collectionList.get(i).compareTo(collectionData) == 0) {
                    collectionList.remove(i);
                    collectionList.add(i, collectionData);
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                collectionList.add(collectionData);
                Collections.sort(collectionList);
            }
            Utility.saveImageList(collectionList, getActivity());
        }
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