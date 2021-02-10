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
import com.example.mymagicapp.models.MyImage;

import java.util.List;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.RecyclerViewDataHolder> {
    private List<MyImage> myImages;
    private Context context;

    public RecyclerViewDataAdapter(List<MyImage> myImages, Context context) {
        this.context = context;
        this.myImages = myImages;
    }

    @NonNull
    @Override
    public RecyclerViewDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_data_image, parent, false);
        return new RecyclerViewDataHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewDataHolder holder, int position) {
        MyImage image = myImages.get(position);
        if (image.imageIdIsNull()) {
            Glide.with(context).load(image.getUri())
                    .centerCrop()
                    .into(holder.imageView);
        } else {
            Glide.with(context).load(image.getImageId())
                    .centerCrop()
                    .into(holder.imageView);
        }
        holder.textView.setText(image.getName());
    }

    @Override
    public int getItemCount() {
        return myImages.size();
    }


    public class RecyclerViewDataHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public RecyclerViewDataHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewDataItem);
            textView = itemView.findViewById(R.id.text_stt);
        }
    }
}
