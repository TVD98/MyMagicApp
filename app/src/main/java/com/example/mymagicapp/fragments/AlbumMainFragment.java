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
import com.example.mymagicapp.adapter.RecyclerViewAlbumAdapter;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.Album;
import com.google.gson.Gson;

public class AlbumMainFragment extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerViewAlbumAdapter adapter;
    public Album album;

    public AlbumMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        recyclerView.setAdapter(new RecyclerViewAlbumAdapter(album.toArray(), getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void init() {
        album = Utility.getData(getActivity(), Utility.KEY_NAME_ALBUM, Album.class);
        if(album == null){
            album = new Album();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_album_main, container, false);
        recyclerView = v.findViewById(R.id.recyclerViewMainAlbum);
        return v;
    }
}