package com.example.mymagicapp.fragments;

import android.content.ClipData;
import android.net.Uri;
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
import com.example.mymagicapp.adapter.RecyclerViewAlbumSettingAdapter;
import com.example.mymagicapp.helper.Constraint;
import com.example.mymagicapp.helper.ISettingFragment;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.Album;
import com.example.mymagicapp.models.MyImage;

import java.util.ArrayList;
import java.util.List;

public class AlbumSettingFragment extends Fragment implements ISettingFragment {
    public List<Album> albumList = new ArrayList<>();
    public RecyclerView recyclerView;
    public RecyclerViewAlbumSettingAdapter adapter;

    public AlbumSettingFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        adapter = new RecyclerViewAlbumSettingAdapter(albumList, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void init() {
        String json = Utility.getData(getActivity(), Utility.KEY_NAME_ALBUM);
        if(json != null){
            List<Album> list = Utility.albumListFromString(json);
            albumList.addAll(list);
        }
        else albumList.add(new Album());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_album_setting, container, false);
        recyclerView = v.findViewById(R.id.recyclerViewSettingAlbum);
        return v;
    }

    @Override
    public void addItem() {
        albumList.add(new Album());
        adapter.notifyItemInserted(albumList.size() - 1);
        Toast.makeText(getActivity(), "Thêm thành công 1 album", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addImages(int position, ClipData clipData) {
        // Do not anything
    }

    @Override
    public void removeItem() {
        int index = adapter.selectedPos;
        if (index != RecyclerView.NO_POSITION) {
            albumList.remove(index);
            adapter.clearSelectedPos();
            Toast.makeText(getActivity(), "Xóa thành công 1 album", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Không có album nào được chọn", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setImage(int colPos, int imgPos, String uri) {
        Album album = albumList.get(colPos);
        album.setMyImage(uri);
        adapter.notifyItemChanged(colPos);
    }

    @Override
    public void saveData() {
        String json = Utility.stringFromAlbumList(albumList);
        Utility.saveData(getActivity(), Utility.KEY_NAME_ALBUM, json);
        Toast.makeText(getActivity(), "Lưu thành công album", Toast.LENGTH_SHORT).show();
    }
}