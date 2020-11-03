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
import com.example.mymagicapp.adapter.RecyclerViewAlbumMainAdapter;
import com.example.mymagicapp.adapter.RecyclerViewAlbumSettingAdapter;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumMainFragment extends Fragment {

    public List<Album> albumList = new ArrayList<>();
    public RecyclerView recyclerView;
    public RecyclerViewAlbumMainAdapter adapter;

    public AlbumMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        adapter = new RecyclerViewAlbumMainAdapter(albumList, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void init() {
        String json = Utility.getData(getActivity(), Utility.KEY_NAME_ALBUM);
        if(json != null){
            List<Album> list = Utility.albumListFromString(json);
            albumList.addAll(list);
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