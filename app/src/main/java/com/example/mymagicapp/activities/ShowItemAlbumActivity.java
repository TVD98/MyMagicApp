package com.example.mymagicapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.RecyclerViewDataAdapter;
import com.example.mymagicapp.adapter.RecyclerViewItemAdapter;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.ItemClickSupport;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.models.ItemAlbum;
import com.example.mymagicapp.models.MyImage;

import java.util.List;

public class ShowItemAlbumActivity extends AppCompatActivity implements View.OnClickListener {
    public int dataId = 0;
    public ItemAlbum itemAlbum;
    public RecyclerView recyclerView;
    public RecyclerViewDataAdapter adapter;
    public TextView selectAll;
    public TextView show;
    public TextView remove;
    public TextView cancel;
    List<MyImage> myImageList;
    public int itemSelected = -1;
    private final int RESULT_LOAD_IMAGE = 0;
    private final int RESULT_LOAD_IMAGES = 1;
    private final int firstItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item_album);

        Bundle bundle = getIntent().getExtras();
        dataId = bundle.getInt("DATA_ID");

        recyclerView = findViewById(R.id.recyclerViewShowAlbum);
        selectAll = findViewById(R.id.text_tick_all);
        show = findViewById(R.id.text_show);
        remove = findViewById(R.id.text_remove);
        cancel = findViewById(R.id.text_cancel);

        init();

        adapter = new RecyclerViewDataAdapter(myImageList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, Constraints.SPAN_COUNT_ITEM_IMAGE));
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (adapter.removeMode) {
                    if (Constraints.couldChangeName(myImageList.get(position))) {
                        adapter.checkHelper.changeCheckBox(position);
                        updateUI(true);
                        adapter.notifyItemChanged(position);
                    }
                } else {
                    if (dataId == Constraints.OPTION_DATA_ID) {
                        itemSelected = position;
                        addImage();
                    }
                }
            }

            @Override
            public void onItemDoubleClicked(RecyclerView recyclerView, int position, View v) {

            }
        });

        selectAll.setOnClickListener(this);
        cancel.setOnClickListener(this);
        remove.setOnClickListener(this);
    }

    private void init() {
        itemAlbum = SaveSystem.getDataAlbum(this, dataId);
        myImageList = itemAlbum.getImageList();
    }

    public void addImage() {
        if (itemSelected == firstItem) {
            pickImagesFromGallery();
        } else pickImageFromGallery();
    }

    private void pickImagesFromGallery() {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(i, RESULT_LOAD_IMAGES);
    }

    private void pickImageFromGallery() {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();
            MyImage image = myImageList.get(itemSelected);
            image.setUri(uri.toString());
            adapter.notifyItemChanged(itemSelected);
            saveItemAlbum();
        } else if (requestCode == RESULT_LOAD_IMAGES && resultCode == RESULT_OK && null != data) {
            Uri[] uris = new Uri[1];
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                int itemCount = clipData.getItemCount();
                uris = new Uri[itemCount];
                for (int i = 0; i < itemCount; i++) {
                    uris[i] = clipData.getItemAt(i).getUri();
                }
            } else if (data.getData() != null) {
                Uri uri = data.getData();
                uris[0] = uri;
            }
            addImagesToAlbum(uris);
        }
    }

    public void updateUI(boolean removeMode) {
        if (removeMode) {
            selectAll.setVisibility(View.VISIBLE);
            remove.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
        } else {
            selectAll.setVisibility(View.INVISIBLE);
            remove.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
        }
        show.setText(String.format("Đã chọn %d mục", adapter.checkHelper.size()));
    }

    private void addImagesToAlbum(Uri[] uris) {
        int index = itemAlbum.size();
        for (Uri uri : uris
        ) {
            MyImage image = new MyImage();
            image.setUri(uri.toString());
            image.setName(Integer.toString(index));
            image.setDescription(Integer.toString(dataId));
            itemAlbum.addItem(image, Constraints.DEFAULT_INDEX_TO_ADD);
            adapter.notifyItemInserted(index);
            index++;
        }
        saveItemAlbum();
    }

    public void saveItemAlbum() {
        SaveSystem.saveData(this, Constraints.dataNameList[dataId], itemAlbum);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_tick_all:
                adapter.checkAll();
                break;
            case R.id.text_remove:
                adapter.removeImage();
                break;
            case R.id.text_cancel:
                adapter.unEnableRemoveMode();
                break;
        }
    }
}