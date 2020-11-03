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
import com.example.mymagicapp.models.Album;
import com.example.mymagicapp.models.MyImage;

import java.util.List;

public class RecyclerViewAlbumMainAdapter extends RecyclerView.Adapter<RecyclerViewAlbumMainAdapter.RecyclerViewAlbumMainHolder> {

    public List<Album> albumList;
    public Context context;

    public RecyclerViewAlbumMainAdapter(List<Album> albumList, Context context) {
        this.albumList = albumList;
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
        Album album = albumList.get(position);
        MyImage image = album.getMyImage();
        Glide.with(context).load(image.getUrl())
                .centerCrop()
                .error(R.drawable.like)
                .into(holder.imageView);
        holder.textTitle.setText(album.getTitle());
        holder.textAmount.setText(Integer.toString(album.getAmount()));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
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
