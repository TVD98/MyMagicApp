package com.example.mymagicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.EventManager;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.MyImage;

public class RecyclerViewItemAdapter extends RecyclerView.Adapter<RecyclerViewItemAdapter.RecyclerViewItemMainHolder> {
    public MyImage[] imageList;
    public Context context;

    public RecyclerViewItemAdapter(MyImage[] imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewItemMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_image, parent, false);
        return new RecyclerViewItemMainHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItemMainHolder holder, int position) {
        MyImage image = imageList[position];
        if (image.imageIdIsNull()) {
            Glide.with(context).load(image.getUri())
                    .centerCrop()
                    .into(holder.imageView);
        } else {
            Glide.with(context).load(image.getImageId())
                    .centerCrop()
                    .into(holder.imageView);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object[] datas = {v, image};
                EventManager.getInstance().showImageClickedListener.onShowImageClicked(datas);
            }
        });

        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Object[] datas = {image};
                EventManager.getInstance().showImageLongClickedListener.onShowImageLongClicked(datas);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.length;
    }

    public class RecyclerViewItemMainHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public RecyclerViewItemMainHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewItem);
            setLayoutItemView(itemView);
        }
    }

    private void setLayoutItemView(View itemView) {
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.width = Utility.widthOfImage(context);
        layoutParams.height = Utility.widthOfImage(context);
        itemView.setLayoutParams(layoutParams);
    }
}
