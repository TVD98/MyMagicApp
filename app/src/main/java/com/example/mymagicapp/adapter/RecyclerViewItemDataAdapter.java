package com.example.mymagicapp.adapter;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymagicapp.R;
import com.example.mymagicapp.activities.ShowAlbumActivity;
import com.example.mymagicapp.helper.CheckBoxImageHelper;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.IRecyclerViewImage;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.MyImage;

import java.util.List;

public class RecyclerViewItemDataAdapter extends RecyclerView.Adapter<RecyclerViewItemDataAdapter.RecyclerViewItemDataHolder>
        implements IRecyclerViewImage {
    public List<MyImage> imageList;
    public Context context;
    public CheckBoxImageHelper checkBoxImageHelper;
    public boolean removeMode = false;

    public RecyclerViewItemDataAdapter(List<MyImage> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
        checkBoxImageHelper = new CheckBoxImageHelper();
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
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (removeMode && Constraints.couldRemoveImage(image)) {
                    changeCheckBox(position);
                } else if (Constraints.couldChangeImage(image))
                    imageOnClick(position);
            }
        });
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (Constraints.couldRemoveImage(image))
                    imageOnLongClick(position);
                return true;
            }
        });

        holder.textId.setText(image.getName());
        holder.textId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textId.setVisibility(View.INVISIBLE);
                holder.editText.setVisibility(View.VISIBLE);
                holder.editText.setText(holder.textId.getText());
                holder.editText.requestFocus();
            }
        });

        holder.editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null &&
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed()) {
                        // the user is done typing.
                        editTextEnded();
                        return true;
                    }
                }
                return false; // pass on to other listeners.
            }

            private void editTextEnded() {
                String text = holder.editText.getText().toString();
                holder.textId.setVisibility(View.VISIBLE);
                holder.editText.setVisibility(View.INVISIBLE);
                if (!text.isEmpty()) {
                    MyImage image = imageList.get(position);
                    image.setName(Integer.toString(Integer.parseInt(text)));
                }
                ShowAlbumActivity activity = (ShowAlbumActivity) context;
                // sort image list
                activity.sortItemAlbum();
                notifyDataSetChanged();
                // save album
                activity.saveAlbum();
            }
        });

        if (checkBoxImageHelper.contain(position)) {
            holder.select.setVisibility(View.VISIBLE);
        } else {
            holder.select.setVisibility(View.INVISIBLE);
        }
    }

    private void imageOnClick(int position) {
        MyImage image = imageList.get(position);
        if (Constraints.couldChangeImage(image)) {
            ShowAlbumActivity activity = (ShowAlbumActivity) context;
            activity.pickImageFromGallery(position);
        }
    }

    private void imageOnLongClick(int position) {
        changeCheckBox(position);
        setRemoveMode(true);
    }

    private void changeCheckBox(int position) {
        checkBoxImageHelper.changeCheckBox(position);
        notifyItemChanged(position);
        ShowAlbumActivity activity = (ShowAlbumActivity) context;
        activity.updateUI(removeMode, checkBoxImageHelper.size());
    }

    @Override
    public void setRemoveMode(boolean result) {
        removeMode = result;
        if (!removeMode) {
            clearCheckBox();
            notifyDataSetChanged();
        }
        ShowAlbumActivity activity = (ShowAlbumActivity) context;
        activity.updateUI(removeMode, checkBoxImageHelper.size());
    }

    @Override
    public void selectAll() {
        checkBoxImageHelper.clear();
        for (int i = 0; i < imageList.size(); i++) {
            changeCheckBox(i);
        }
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

    @Override
    public Integer[] getImageIdListToRemove() {
        return checkBoxImageHelper.toArray();
    }

    @Override
    public void clearCheckBox() {
        checkBoxImageHelper.clear();
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
