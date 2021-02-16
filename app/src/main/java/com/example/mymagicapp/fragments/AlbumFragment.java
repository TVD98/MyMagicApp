package com.example.mymagicapp.fragments;

import android.content.Intent;
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
import com.example.mymagicapp.activities.ShowAlbumActivity;
import com.example.mymagicapp.adapter.RecyclerViewAlbumAdapter;
import com.example.mymagicapp.helper.ItemClickSupport;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.models.Album;
import com.example.mymagicapp.models.ItemAlbum;
import com.google.gson.Gson;

import java.util.List;

public class AlbumFragment extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerViewAlbumAdapter adapter;
    public Album album;
    public List<ItemAlbum> itemAlbums;
    public String nameAlbum;
    public int itemSelected = 0;
    public boolean onStop = false;

    public AlbumFragment(String nameAlbum) {
        this.nameAlbum = nameAlbum;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        recyclerView = getView().findViewById(R.id.recyclerViewCollection);

        adapter = new RecyclerViewAlbumAdapter(itemAlbums, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                itemSelected = position;
                startShowAlbumActivity(position);
            }

            @Override
            public void onItemDoubleClicked(RecyclerView recyclerView, int position, View v) {

            }
        });
    }

    private void startShowAlbumActivity(int position) {
        Intent intent = new Intent(getActivity(), ShowAlbumActivity.class);
        Gson gson = new Gson();
        String[] albumInfo = {Integer.toString(position), gson.toJson(album)};
        intent.putExtra("ALBUM_INFO", albumInfo);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        onStop = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(onStop){
            init();
            adapter = new RecyclerViewAlbumAdapter(itemAlbums, getActivity());
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

    private void init() {
        album = SaveSystem.getData(getActivity(), nameAlbum, Album.class);
        if (album == null) {
            album = new Album();
        }
        album.setName(nameAlbum);
        itemAlbums = album.getItemAlbums();
    }


}