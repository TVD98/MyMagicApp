package com.example.mymagicapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.activities.ShowAlbumActivity;
import com.example.mymagicapp.adapter.RecyclerViewAlbumAdapter;
import com.example.mymagicapp.helper.IOnFragmentManager;
import com.example.mymagicapp.helper.ItemClickSupport;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.models.Album;
import com.example.mymagicapp.models.ItemAlbum;
import com.google.gson.Gson;

import java.util.List;

public class AlbumFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerViewAlbumAdapter adapter;
    private Album album;
    private List<ItemAlbum> itemAlbums;
    private String nameAlbum;
    private boolean onStop = false;

    private IOnFragmentManager listener;

    public AlbumFragment(String nameAlbum) {
        this.nameAlbum = nameAlbum;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof IOnFragmentManager)
            listener = (IOnFragmentManager) context;
        init();
    }

    private void init() {
        album = SaveSystem.getData(context, nameAlbum, Album.class);
        if (album == null) {
            album = new Album();
        }
        album.setName(nameAlbum);
        itemAlbums = album.getItemAlbums();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = getView().findViewById(R.id.recyclerViewCollection);

        adapter = new RecyclerViewAlbumAdapter(itemAlbums, context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                listener.onItemClick(album.getItem(position));
            }

            @Override
            public void onItemDoubleClicked(RecyclerView recyclerView, int position, View v) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        onStop = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (onStop) {
            saveAlbum();
            init();
            adapter = new RecyclerViewAlbumAdapter(itemAlbums, context);
            recyclerView.setAdapter(adapter);
            onStop = false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_collection, container, false);

        return v;
    }

    public void saveAlbum() {
        SaveSystem.saveData(getActivity(), nameAlbum, album);
    }


}