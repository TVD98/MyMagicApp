package com.example.mymagicapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.Constraint;
import com.example.mymagicapp.helper.EventManager;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.MyImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewItemSettingAdapter extends RecyclerView.Adapter<RecyclerViewItemSettingAdapter.RecyclerViewItemSettingHolder> {
    public int collectionIndex = 0;
    public List<MyImage> imageList = new ArrayList<>();
    public Context context;
    private boolean allowCheckBox;

    public RecyclerViewItemSettingAdapter(int colIndex, List<MyImage> imageList, boolean allowCheckBox, Context context) {
        this.collectionIndex = colIndex;
        this.imageList = imageList;
        this.allowCheckBox = allowCheckBox;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewItemSettingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_image, parent, false);
        return new RecyclerViewItemSettingHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItemSettingHolder holder, int position) {
        MyImage image = imageList.get(position);
        if (image.isImageByUrl()) {
            Glide.with(context).load(image.getUrl())
                    .centerCrop()
                    .error(R.drawable.like)
                    .into(holder.imageView);
        } else holder.imageView.setImageResource(image.getResId());

        if (allowCheckBox && image.isImageByUrl()) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else holder.checkBox.setVisibility(View.INVISIBLE);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventManager.getInstance().checkBoxImageClickedListener.OnCheckBoxImageClicked(collectionIndex, position);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allowCheckBox) {
                    int code = 0;
                    if (image.isImageByUrl())
                        code = Constraint.CODE_IMG_GALLERY;
                    else code = Constraint.CODE_MULTIPLE_IMG_GALLERY;
                    EventManager.getInstance().clickedListener.OnUploadImageClicked(collectionIndex, position, code);
                }

           }
        });

        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (image.isImageByUrl()) {
                    EventManager.getInstance().longClickedListener.OnUploadImageLongClicked(collectionIndex, position);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class RecyclerViewItemSettingHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public CheckBox checkBox;

        public RecyclerViewItemSettingHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewItem);
            checkBox = itemView.findViewById(R.id.checkboxImage);
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
