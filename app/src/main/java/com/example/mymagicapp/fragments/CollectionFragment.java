package com.example.mymagicapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.mymagicapp.models.Gallery;
import com.example.mymagicapp.models.ItemGallery;
import com.example.mymagicapp.models.MyImage;

import java.util.ArrayList;
import java.util.List;

public class CollectionFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private Gallery gallery;
    private List<ItemGallery> itemGalleryList = new ArrayList<>();
    private RecyclerViewAdapter adapter;

    public CollectionFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

        init();
    }

    private void init() {
        gallery = SaveSystem.getData(context, SaveSystem.KEY_NAME_COLLECTION, Gallery.class);
        if (gallery == null)
            gallery = new Gallery();
        itemGalleryList = gallery.getItemGalleryList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_collection, container, false);
        recyclerView = v.findViewById(R.id.recyclerViewCollection);

        adapter = new RecyclerViewAdapter(itemGalleryList, context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return v;
    }

    public List<MyImage> getImageList(){
        return gallery.toImageArray();
    }

}