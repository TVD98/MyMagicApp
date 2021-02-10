package com.example.mymagicapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mymagicapp.R;
import com.example.mymagicapp.adapter.RecyclerViewDataAdapter;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.RecyclerItemClickListener;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.models.ItemAlbum;
import com.example.mymagicapp.models.MyImage;

import java.util.List;

public class DefaultDataFragment extends Fragment {
    RecyclerView recyclerView;
    EditText editText;
    RecyclerViewDataAdapter adapter;
    List<MyImage> myImages;
    ItemAlbum itemAlbum;
    protected int dataId;
    protected int itemSelected;

    public DefaultDataFragment(int dataId) {
        this.dataId = dataId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_default_data, container, false);
        recyclerView = v.findViewById(R.id.recyclerViewSettingCard);
        editText = v.findViewById(R.id.edit_text_number);

        init();

        adapter = new RecyclerViewDataAdapter(myImages, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constraints.SPAN_COUNT_ITEM_IMAGE));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                itemSelected = position;
                DefaultDataFragment.this.onItemClick(v, position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                itemSelected = position;
                if (couldChangeData()) {
                    showEditText();
                }
            }
        }));

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                    case EditorInfo.IME_ACTION_NEXT:
                    case EditorInfo.IME_ACTION_PREVIOUS:
                        editTextIsDone(editText.getText().toString());
                }
                return false;
            }
        });

        return v;
    }

    public void onItemClick(View v, int position) {

    }

    public boolean couldChangeData() {
        return true;
    }

    private void editTextIsDone(String number) {
        editText.setVisibility(View.INVISIBLE);
        if (!number.isEmpty()) {
            int stt = Integer.parseInt(number);
            myImages.get(itemSelected).setName(Integer.toString(stt));
            adapter.notifyItemChanged(itemSelected);
            // save data album
            saveItemAlbum();
        }
    }

    private void showEditText() {
        editText.setVisibility(View.VISIBLE);
        if (editText.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void init() {
        itemAlbum = SaveSystem.getDataAlbum(getActivity(), dataId);
        myImages = itemAlbum.getImageList();
    }

    protected void saveItemAlbum() {
        SaveSystem.saveData(getActivity(), Integer.toString(dataId), itemAlbum);
    }

}