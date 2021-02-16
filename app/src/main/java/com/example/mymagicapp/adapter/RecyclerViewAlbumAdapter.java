package com.example.mymagicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymagicapp.R;
import com.example.mymagicapp.models.ItemAlbum;
import com.example.mymagicapp.models.MyImage;

import java.util.List;

public class RecyclerViewAlbumAdapter extends RecyclerView.Adapter<RecyclerViewAlbumAdapter.RecyclerViewAlbumMainHolder> {
    public List<ItemAlbum> itemAlbumList;
    public Context context;

    public RecyclerViewAlbumAdapter(List<ItemAlbum> itemAlbumList, Context context) {
        this.itemAlbumList = itemAlbumList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAlbumMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RecyclerViewAlbumMainHolder(inflater.inflate(R.layout.item_album, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAlbumMainHolder holder, int position) {
        ItemAlbum itemAlbum = itemAlbumList.get(position);
        MyImage firstImage = itemAlbum.getFirstImage();
        if (firstImage != null) {
            if (firstImage.imageIdIsNull()) {
                Glide.with(context).load(firstImage.getUri())
                        .centerCrop()
                        .error(R.drawable.like)
                        .into(holder.imageView);
            } else {
                Glide.with(context).load(firstImage.getImageId())
                        .centerCrop()
                        .error(R.drawable.share)
                        .into(holder.imageView);
            }
        }
        holder.textTitle.setText(itemAlbum.title());
        holder.textAmount.setText(Integer.toString(itemAlbum.size()));
    }

    @Override
    public int getItemCount() {
        return itemAlbumList.size();
    }

    public class RecyclerViewAlbumMainHolder extends RecyclerView.ViewHolder {
        public TextView textTitle;
        public TextView textAmount;
        public ImageView imageView;

        public RecyclerViewAlbumMainHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textViewTitleAlbum);
            textAmount = itemView.findViewById(R.id.textViewAmount);
            imageView = itemView.findViewById(R.id.imageViewAlbumMain);
        }
    }
}
