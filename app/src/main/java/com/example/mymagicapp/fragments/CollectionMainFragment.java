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
import com.example.mymagicapp.activities.MainActivity;
import com.example.mymagicapp.adapter.RecyclerViewAdapter;
import com.example.mymagicapp.helper.EventManager;
import com.example.mymagicapp.helper.OnShowImageLongClickedListener;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.Gallery;
import com.example.mymagicapp.models.ItemGallery;
import com.example.mymagicapp.models.MyImage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CollectionMainFragment extends Fragment {
    public RecyclerView recyclerViewMain;
    public Gallery gallery;
    public List<ItemGallery> itemGalleryList = new ArrayList<>();
    private RecyclerViewAdapter adapter;

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

        EventManager.getInstance().setOnShowImageLongClickedListener(new OnShowImageLongClickedListener() {
            @Override
            public void onShowImageLongClicked(Object[] datas) {
                MainActivity activity = (MainActivity) getActivity();
                if (activity.isModeRemove()) {
                    MyImage myImage = (MyImage) datas[0];
                    removeImage(myImage);
                    SaveSystem.saveGalleryToShared(gallery, getActivity()); // save gallery to shared
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_collection_main, container, false);
        recyclerViewMain = v.findViewById(R.id.recyclerViewMain);

        init();

        adapter = new RecyclerViewAdapter(itemGalleryList, getActivity());
        recyclerViewMain.setAdapter(adapter);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    private void init() {
        gallery = SaveSystem.getData(getActivity(), SaveSystem.KEY_NAME_COLLECTION, Gallery.class);
        if (gallery == null)
            gallery = new Gallery();
        itemGalleryList = gallery.getItemGalleryList();
    }

    private void removeImage(MyImage image) {
        gallery.removeItem(image); // remove image
        adapter.notifyDataSetChanged();
    }

}