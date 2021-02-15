package com.example.mymagicapp.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymagicapp.R;
import com.example.mymagicapp.activities.ShowItemAlbumActivity;
import com.example.mymagicapp.helper.CheckBoxImageHelper;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.models.MyImage;

import java.util.Comparator;
import java.util.List;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.RecyclerViewDataHolder> {
    private List<MyImage> myImages;
    private Context context;
    public boolean removeMode = false;
    public CheckBoxImageHelper checkHelper;

    public RecyclerViewDataAdapter(List<MyImage> myImages, Context context) {
        this.context = context;
        this.myImages = myImages;
        this.checkHelper = new CheckBoxImageHelper();
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
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constraints.couldChangeName(image)) {
                    holder.textView.setVisibility(View.INVISIBLE);
                    if (holder.editText.requestFocus()) {
                        ((ShowItemAlbumActivity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                }
            }
        });
        holder.editText.setText(image.getName());
        holder.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                    case EditorInfo.IME_ACTION_NEXT:
                    case EditorInfo.IME_ACTION_PREVIOUS:
                        editTextIsDone(holder.editText.getText().toString());
                }
                return false;
            }

            private void editTextIsDone(String number) {
                holder.textView.setVisibility(View.VISIBLE);
                closeKeyboard();
                if (!number.isEmpty() && Integer.parseInt(number) != Integer.parseInt(image.getName())) {
                    int stt = Integer.parseInt(number);
                    myImages.get(position).setName(Integer.toString(stt));
                    ShowItemAlbumActivity activity = ((ShowItemAlbumActivity) context);
                    activity.itemAlbum.sortByName();
                    notifyDataSetChanged();
                    activity.saveItemAlbum();
                }
            }

        });

        if (removeMode) {
            if (checkHelper.contain(position))
                holder.check.setVisibility(View.VISIBLE);
            else holder.check.setVisibility(View.INVISIBLE);
        } else holder.check.setVisibility(View.INVISIBLE);
    }

    private void closeKeyboard() {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = ((ShowItemAlbumActivity) context).getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {
            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }

    @Override
    public int getItemCount() {
        return myImages.size();
    }

    public void removeImage() {
        Integer[] array = checkHelper.toArray();
        for (int i = 0; i < array.length; i++) {
            myImages.remove(array[i] - i);
        }
        checkHelper.clear();

        ShowItemAlbumActivity activity = (ShowItemAlbumActivity) context;
        if (myImages.size() == 1) {
            removeMode = false;
            activity.updateUI(false);
        } else activity.updateUI(true);
        notifyDataSetChanged();
        activity.saveItemAlbum();
    }

    public void enableRemoveMode(int position) {
        if (Constraints.couldChangeName(myImages.get(position))) {
            if (!removeMode) {
                removeMode = true;
            }
            checkHelper.changeCheckBox(position);
            ShowItemAlbumActivity activity = (ShowItemAlbumActivity) context;
            activity.updateUI(removeMode);
            notifyItemChanged(position);
        }
    }

    public void unEnableRemoveMode() {
        removeMode = false;
        checkHelper.clear();
        ShowItemAlbumActivity activity = (ShowItemAlbumActivity) context;
        activity.updateUI(false);
        notifyDataSetChanged();
    }

    public void checkAll() {
        checkHelper.clear();
        for (int i = 1; i < myImages.size(); i++) {
            checkHelper.changeCheckBox(i);
        }
        ShowItemAlbumActivity activity = (ShowItemAlbumActivity) context;
        activity.updateUI(true);
        notifyDataSetChanged();
    }


    public class RecyclerViewDataHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public EditText editText;
        public TextView textView;
        public ImageView check;

        public RecyclerViewDataHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewDataItem);
            editText = itemView.findViewById(R.id.edit_stt);
            textView = itemView.findViewById(R.id.text_stt);
            check = itemView.findViewById(R.id.check);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    enableRemoveMode(position);
                    return true;
                }
            });
        }
    }
}
