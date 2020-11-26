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

public class RecyclerViewAlbumAdapter extends RecyclerView.Adapter<RecyclerViewAlbumAdapter.RecyclerViewAlbumMainHolder> {
    public ItemAlbum[] itemAlbumList;
    public Context context;

    public RecyclerViewAlbumAdapter(ItemAlbum[] itemAlbumList, Context context) {
        this.itemAlbumList = itemAlbumList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAlbumMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RecyclerViewAlbumMainHolder(inflater.inflate(R.layout.item_album_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAlbumMainHolder holder, int position) {
        ItemAlbum itemAlbum = itemAlbumList[position];
        MyImage image = itemAlbum.getCurrentImage();
        Glide.with(context).load(image.getUri())
                .centerCrop()
                .error(R.drawable.like)
                .into(holder.imageView);
        holder.textTitle.setText(itemAlbum.getTitle());
        holder.textAmount.setText(Integer.toString(itemAlbum.getAmount()));
    }

    @Override
    public int getItemCount() {
        return itemAlbumList.length;
    }

    public class RecyclerViewAlbumMainHolder extends RecyclerView.ViewHolder{
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
