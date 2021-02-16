package com.example.mymagicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.ItemClickSupport;
import com.example.mymagicapp.models.ItemGallery;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewMainHolder> {
    private List<ItemGallery> itemGalleries;
    private RecyclerViewItemAdapter[] recyclerViewItemAdapters;
    private Context context;

    public RecyclerViewAdapter(List<ItemGallery> itemGalleries, Context context) {
        this.itemGalleries = itemGalleries;
        this.recyclerViewItemAdapters = new RecyclerViewItemAdapter[itemGalleries.size()];
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerViewMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_collection, parent, false);
        return new RecyclerViewMainHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMainHolder holder, int position) {
        ItemGallery itemGallery = itemGalleries.get(position);
        holder.textTitle.setText(itemGallery.title());
        recyclerViewItemAdapters[position] = new RecyclerViewItemAdapter(itemGallery.getImageList(), context); // create recycleViewItemAdapter
        holder.recyclerView.setAdapter(recyclerViewItemAdapters[position]);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, Constraints.SPAN_COUNT_ITEM_IMAGE));
    }

    @Override
    public int getItemCount() {
        return itemGalleries.size();
    }

    public class RecyclerViewMainHolder extends RecyclerView.ViewHolder{
        public TextView textTitle;
        public RecyclerView recyclerView;

        public RecyclerViewMainHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textViewTitle);
            recyclerView = itemView.findViewById(R.id.recyclerViewItemMain);
        }
    }
}
