package com.example.mymagicapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.Constraint;
import com.example.mymagicapp.helper.EventManager;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.MyImage;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class ShowImagePagerAdapter extends PagerAdapter {
    public List<MyImage> imageList;
    public Context context;

    public ShowImagePagerAdapter(List<MyImage> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PhotoView imageView = new PhotoView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        if (imageList.get(position).isImageByUrl()) {
            Glide.with(context).load(imageList.get(position).getUrl())
                    .fitCenter()
                    .error(R.drawable.ic_add_image)
                    .into(imageView);
        }
        else {
            Glide.with(context).load(imageList.get(position).getResId())
                    .fitCenter()
                    .error(R.drawable.ic_add_image)
                    .into(imageView);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventManager.getInstance().photoViewClickedListener.onPhotoViewClicked();
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        PhotoView imageView = (PhotoView) object;
        container.removeView(imageView);
    }




}
