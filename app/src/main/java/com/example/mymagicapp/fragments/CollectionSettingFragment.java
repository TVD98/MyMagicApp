package com.example.mymagicapp.fragments;

import android.content.ClipData;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.RecyclerViewSettingAdapter;
import com.example.mymagicapp.helper.CheckBoxImageHelper;
import com.example.mymagicapp.helper.EventManager;
import com.example.mymagicapp.helper.ISettingFragment;
import com.example.mymagicapp.helper.OnCheckBoxImageClickedListener;
import com.example.mymagicapp.helper.OnUploadImageLongClickedListener;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.CollectionOfDay;
import com.example.mymagicapp.models.CollectionOfDayBuilder;
import com.example.mymagicapp.models.MyImage;
import com.example.mymagicapp.models.MyImageBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionSettingFragment extends Fragment implements ISettingFragment {
    public RecyclerView recyclerViewSetting;
    public Toolbar toolbar;
    public List<CollectionOfDay> collectionList = new ArrayList<>();
    public RecyclerViewSettingAdapter adapter;

    private List<CheckBoxImageHelper> checkBoxList = new ArrayList<>();

    public CollectionSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        adapter = new RecyclerViewSettingAdapter(collectionList, getActivity());
        recyclerViewSetting.setAdapter(adapter);
        recyclerViewSetting.setLayoutManager(new LinearLayoutManager(getActivity()));
        toolbar.inflateMenu(R.menu.collection_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemRemoveImages:
                        removeImages();
                        break;
                }
                return true;
            }
        });

        EventManager.getInstance().setOnUploadImageLongClickedListener(new OnUploadImageLongClickedListener() {
            @Override
            public void OnUploadImageLongClicked(int colIndex, int imgIndex) {
                changeVisibilityToolbar();
                adapter.changeAllowCheckBox();
            }
        });

        EventManager.getInstance().setOnCheckBoxImageClickedListener(new OnCheckBoxImageClickedListener() {
            @Override
            public void OnCheckBoxImageClicked(int collectionIndex, int imgIndex) {
                onCheckBoxImageClicked(collectionIndex, imgIndex);
            }
        });
    }

    private void changeVisibilityToolbar(){
        if(toolbar.getVisibility() == View.VISIBLE)
            toolbar.setVisibility(View.GONE);
        else toolbar.setVisibility(View.VISIBLE);
    }

    private void onCheckBoxImageClicked(int colIndex, int imgIndex) {
        CheckBoxImageHelper checkBoxImageHelper = checkBoxList.stream()
                .filter(x -> x.getCollectionId() == colIndex)
                .findAny()
                .orElse(null);
        if (checkBoxImageHelper == null) {
            CheckBoxImageHelper cb = new CheckBoxImageHelper(colIndex);
            cb.changeCheckBox(imgIndex);
            checkBoxList.add(cb);
        } else checkBoxImageHelper.changeCheckBox(imgIndex);
    }

    private void init() {
        String json = Utility.getData(getActivity(), Utility.KEY_NAME_COLLECTION);
        if (json == null) {
            CollectionOfDay collection = new CollectionOfDayBuilder().buildCollectionOfDay();
            collection.addImage(new MyImageBuilder().buildMyImage());
            collectionList.add(collection);
        } else {
            List<CollectionOfDay> uploadCollectionList = Utility.collectionListFromString(json);
            Utility.uploadDataToSettingData(uploadCollectionList);
            collectionList.addAll(uploadCollectionList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_collection_setting, container, false);
        recyclerViewSetting = v.findViewById(R.id.recyclerViewSetting);
        toolbar = v.findViewById(R.id.toolBarCollection);
        return v;
    }


    @Override
    public void addItem() {
        CollectionOfDay collection = new CollectionOfDayBuilder().buildCollectionOfDay();
        collection.addImage(new MyImageBuilder().buildMyImage());
        collectionList.add(collection);
        adapter.notifyItemInserted(collectionList.size() - 1);
        Toast.makeText(getActivity(), "Thêm thành công 1 collection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addImages(int position, ClipData clipData) {
        CollectionOfDay collection = collectionList.get(position);
        for (int i = 0; i < clipData.getItemCount(); i++) {
            final Uri uri = clipData.getItemAt(i).getUri();
            collection.addImage(new MyImageBuilder().url(uri.toString()).buildMyImage());
        }
        adapter.notifyItemChanged(position);
    }

    private void removeImages() {
        int removedImagesCount = 0;
        for (CheckBoxImageHelper x : checkBoxList) {
            CollectionOfDay collection = collectionList.get(x.getCollectionId());
            collection.removeImages(x.getImageIdList());
            removedImagesCount += x.getImageIdList().size();
        }
        if (removedImagesCount == 0) {
            Toast.makeText(getActivity(), "Chưa chọn ảnh nào", Toast.LENGTH_SHORT).show();
        } else {
            checkBoxList.clear();
            changeVisibilityToolbar();
            adapter.changeAllowCheckBox();
            Toast.makeText(getActivity(), String.format("Đã xóa %d ảnh", removedImagesCount), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void removeItem() {
        int index = adapter.selectedPos;
        if (index != RecyclerView.NO_POSITION) {
            collectionList.remove(index);
//            adapter.notifyItemRemoved(index);
            adapter.clearSelectedPos();
            Toast.makeText(getActivity(), "Xóa thành công 1 collection", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Không có collection nào được chọn", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setImage(int colPos, int imgPos, String uri) {
        CollectionOfDay collection = collectionList.get(colPos);
        collection.setImage(imgPos, uri);
        adapter.notifyItemChanged(colPos);
    }

    @Override
    public void saveData() {
        Collections.sort(collectionList);
        Utility.settingDataToUpLoadData(collectionList); // convert setting collection to main collection
        String json = Utility.stringFromCollectionList(collectionList);
        Utility.saveData(getActivity(), Utility.KEY_NAME_COLLECTION, json);
        Utility.saveImageList(collectionList, getActivity());
        Toast.makeText(getActivity(), "Lưu thành công bộ sưu tập", Toast.LENGTH_SHORT).show();
        Utility.uploadDataToSettingData(collectionList); // convert main collection to setting collection
    }



}