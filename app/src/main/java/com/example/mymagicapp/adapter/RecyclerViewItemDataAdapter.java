package com.example.mymagicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.MyImage;

import java.util.List;

public class RecyclerViewItemDataAdapter extends RecyclerView.Adapter<RecyclerViewItemDataAdapter.RecyclerViewItemDataHolder> {
    private List<MyImage> imageList;
    private Context context;

    public RecyclerViewItemDataAdapter(List<MyImage> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewItemDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_data_image, parent, false);
        return new RecyclerViewItemDataHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItemDataHolder holder, int position) {
        MyImage image = imageList.get(position);
        if (image.imageIdIsNull()) {
            Glide.with(context).load(image.getUri())
                    .centerCrop()
                    .into(holder.imageView);
        } else {
            Glide.with(context).load(image.getImageId())
                    .centerCrop()
                    .into(holder.imageView);
        }

        holder.textId.setText(image.getName());

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    private void setLayoutItemView(View itemView) {
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.width = Utility.widthOfImage(context);
        layoutParams.height = Utility.widthOfImage(context);
        itemView.setLayoutParams(layoutParams);
    }

    public class RecyclerViewItemDataHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textId;
        public ImageView select;
        public EditText editText;

        public RecyclerViewItemDataHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewDataItem);
            textId = itemView.findViewById(R.id.text_stt);
            select = itemView.findViewById(R.id.check);
            editText = itemView.findViewById(R.id.edit_stt);
            setLayoutItemView(itemView);
        }
    }


}
