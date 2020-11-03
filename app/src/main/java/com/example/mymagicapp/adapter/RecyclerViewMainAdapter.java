package com.example.mymagicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.Constraint;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.CollectionOfDay;

import java.util.List;

public class RecyclerViewMainAdapter extends RecyclerView.Adapter<RecyclerViewMainAdapter.RecyclerViewMainHolder> {
    public List<CollectionOfDay> collectionList;
    public FragmentActivity context;

    public RecyclerViewMainAdapter(List<CollectionOfDay> collectionList, FragmentActivity context) {
        this.collectionList = collectionList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_collection_main, parent, false);
        return new RecyclerViewMainHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMainHolder holder, int position) {
        CollectionOfDay collection = collectionList.get(position);
        holder.textTitle.setText(collection.getTitle());
        holder.textDescription.setText(collection.getDescription());
        holder.recyclerView.setAdapter(new RecyclerViewItemMainAdapter(collection.getImageList(), context));
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, Utility.SPAN_COUNT_ITEM_IMAGE));
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    public class RecyclerViewMainHolder extends RecyclerView.ViewHolder{
        public TextView textTitle;
        public TextView textDescription;
        public RecyclerView recyclerView;

        public RecyclerViewMainHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textViewTitle);
            textDescription = itemView.findViewById(R.id.textViewDescription);
            recyclerView = itemView.findViewById(R.id.recyclerViewItemMain);
        }
    }
}
