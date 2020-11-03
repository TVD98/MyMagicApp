package com.example.mymagicapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.EventManager;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.MyImage;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewItemMainAdapter extends RecyclerView.Adapter<RecyclerViewItemMainAdapter.RecyclerViewItemMainHolder> {
    public List<MyImage> imageList = new ArrayList<>();
    public FragmentActivity context;

    public RecyclerViewItemMainAdapter(List<MyImage> imageList, FragmentActivity context) {
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
        MyImage image = imageList.get(position);
        if (image.isImageByUrl()) {
            Glide.with(context).load(Uri.parse(image.getUrl()))
                    .centerCrop()
                    .error(R.drawable.like)
                    .into(holder.imageView);
        } else holder.imageView.setImageResource(image.getResId());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object[] datas = {v, image};
                EventManager.getInstance().showImageClickedListener.onShowImageClicked(datas);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
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
