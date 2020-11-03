package com.example.mymagicapp.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.Constraint;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.CollectionOfDay;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class RecyclerViewSettingAdapter extends RecyclerView.Adapter<RecyclerViewSettingAdapter.RecyclerViewSettingHolder> {
    public List<CollectionOfDay> collectionList;
    public Context context;
    public int selectedPos = RecyclerView.NO_POSITION;
    private boolean allowCheckBox = false;

    public RecyclerViewSettingAdapter(List<CollectionOfDay> collectionList, Context context) {
        this.collectionList = collectionList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewSettingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_collection_setting, parent, false);
        return new RecyclerViewSettingHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewSettingHolder holder, int position) {
        CollectionOfDay collection = collectionList.get(position);
        holder.buttonSelectDate.setText(collection.getTitle());
        holder.buttonSelectDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onSelectedCollection(position);
                return true;
            }
        });

        holder.buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectingDate(collection, position);
            }
        });

        holder.editTextDescription.setText(collection.getDescription());
        holder.editTextDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                collection.setDescription(s.toString());
            }
        });

        RecyclerViewItemSettingAdapter adapter = new RecyclerViewItemSettingAdapter(position, collection.getImageList(), allowCheckBox, context);
        holder.recyclerViewItemSetting.setAdapter(adapter);
        holder.recyclerViewItemSetting.setLayoutManager(new GridLayoutManager(context, Utility.SPAN_COUNT_ITEM_IMAGE));

        holder.itemView.setBackgroundColor(selectedPos == position ? Color.GRAY : Color.TRANSPARENT);
    }

    private void onSelectingDate(CollectionOfDay collection, int position){
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
                        int result = isTrueDate(date);
                        if (result == Constraint.ERROR_OUT_OF_DATE)
                            Toast.makeText(context, "Không chọn ngày sau hôm nay", Toast.LENGTH_SHORT).show();
                        else {
                            if (result == Constraint.ERROR_SAME_DATE)
                                Toast.makeText(context, "Ngày này đã được chọn", Toast.LENGTH_SHORT).show();
                            collection.setDate(date);
                            notifyItemChanged(position);
                        }
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void clearSelectedPos() {
        selectedPos = RecyclerView.NO_POSITION;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    public void changeAllowCheckBox() {
        allowCheckBox = !allowCheckBox;
        notifyDataSetChanged();
    }

    private void onSelectedCollection(int position) {
        if (position == selectedPos) {
            selectedPos = RecyclerView.NO_POSITION;
            notifyItemChanged(position);
        } else {
            notifyItemChanged(selectedPos);
            selectedPos = position;
            notifyItemChanged(selectedPos);
        }
    }

    private int isTrueDate(LocalDate date) {
        if (date.compareTo(LocalDate.now()) > 0)
            return Constraint.ERROR_OUT_OF_DATE;
        for (CollectionOfDay collection : collectionList
        ) {
            if (date.compareTo(collection.getDate()) == 0)
                return Constraint.ERROR_SAME_DATE;
        }
        return Constraint.TRUE_DATE;
    }

    public class RecyclerViewSettingHolder extends RecyclerView.ViewHolder {
        public Button buttonSelectDate;
        public EditText editTextDescription;
        public RecyclerView recyclerViewItemSetting;

        public RecyclerViewSettingHolder(View itemView) {
            super(itemView);
            buttonSelectDate = itemView.findViewById(R.id.buttonSelectDate);
            editTextDescription = itemView.findViewById(R.id.editTextDescription);
            recyclerViewItemSetting = itemView.findViewById(R.id.recyclerViewItemSetting);
        }
    }

}
