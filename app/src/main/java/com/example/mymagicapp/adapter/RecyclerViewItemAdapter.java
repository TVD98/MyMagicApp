package com.example.mymagicapp.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymagicapp.R;
import com.example.mymagicapp.activities.MainActivity;
import com.example.mymagicapp.activities.ShowImageActivity;
import com.example.mymagicapp.fragments.CollectionFragment;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.IRecyclerViewImage;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.MyImage;
import com.google.gson.Gson;

import java.util.List;

public class RecyclerViewItemAdapter extends RecyclerView.Adapter<RecyclerViewItemAdapter.RecyclerViewItemHolder>
    implements IRecyclerViewImage {
    public List<MyImage> imageList;
    public Context context;

    public RecyclerViewItemAdapter(List<MyImage> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_image, parent, false);
        return new RecyclerViewItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItemHolder holder, int position) {
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

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShowImageActivity((Activity) context, v, image);
            }
        });

        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    private void startShowImageActivity(Activity activity, View imageView, MyImage image) {
        Intent intent = new Intent(activity, ShowImageActivity.class);
        Gson gson = new Gson();
        String[] datas = {gson.toJson(image), gson.toJson(getImageList().toArray())};
        intent.putExtra("DATAS", datas); // put information of clicked image to intent
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(imageView, Constraints.TRANSITION_NAME);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);
        activity.startActivity(intent, options.toBundle());
    }

    private List<MyImage> getImageList() {
        try {
            MainActivity activity = (MainActivity) context;
            if (activity != null) {
                CollectionFragment fragment = (CollectionFragment) activity.getCurrentFragment();
                if (fragment != null) {
                    return fragment.getImageList();
                }
            }
            return imageList;
        } catch (Exception e) {
            return imageList;
        }
    }

    @Override
    public Integer[] getImageIdListToRemove() {
        return new Integer[0];
    }

    @Override
    public void clearCheckBox() {

    }

    @Override
    public void setRemoveMode(boolean removeMode) {

    }

    @Override
    public void selectAll() {

    }

    public class RecyclerViewItemHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public RecyclerViewItemHolder(@NonNull View itemView) {
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
