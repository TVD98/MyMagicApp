package com.example.mymagicapp.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.Constraint;
import com.example.mymagicapp.helper.EventManager;
import com.example.mymagicapp.models.Album;
import com.example.mymagicapp.models.MyImage;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAlbumSettingAdapter extends RecyclerView.Adapter<RecyclerViewAlbumSettingAdapter.RecyclerViewAlbumSettingHolder> {
    public List<Album> albumList;
    public Context context;
    public int selectedPos = RecyclerView.NO_POSITION;

    public RecyclerViewAlbumSettingAdapter(List<Album> albumList, Context context) {
        this.albumList = albumList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAlbumSettingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_album_setting, parent, false);
        return new RecyclerViewAlbumSettingHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAlbumSettingHolder holder, int position) {
        Album album = albumList.get(position);
        MyImage image = album.getMyImage();
        if (image.isImageByUrl()) {
            Glide.with(context).load(image.getUrl())
                    .centerCrop()
                    .error(R.drawable.like)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(image.getResId());
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventManager.getInstance().clickedListener.OnUploadImageClicked(position, 0, Constraint.CODE_IMG_GALLERY);
            }
        });
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onSelectedAlbum(position);
                return true;
            }
        });

        holder.editTextTitle.setText(album.getTitle());
        holder.editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                album.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.editTextAmount.setText(Integer.toString(album.getAmount()));
        holder.editTextAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty())
                    album.setAmount(0);
                else album.setAmount(Integer.parseInt(s.toString()));
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.itemView.setBackgroundColor(selectedPos == position ? Color.GRAY : Color.TRANSPARENT);
    }

    private void onSelectedAlbum(int position) {
        if (position == selectedPos) {
            selectedPos = RecyclerView.NO_POSITION;
            notifyItemChanged(position);
        } else {
            notifyItemChanged(selectedPos);
            selectedPos = position;
            notifyItemChanged(selectedPos);
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public void clearSelectedPos() {
        selectedPos = RecyclerView.NO_POSITION;
        notifyDataSetChanged();
    }

    public class RecyclerViewAlbumSettingHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public EditText editTextTitle;
        public EditText editTextAmount;

        public RecyclerViewAlbumSettingHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewAlbum);
            editTextTitle = itemView.findViewById(R.id.editTextTitle);
            editTextAmount = itemView.findViewById(R.id.editTextAmount);
        }
    }
}
