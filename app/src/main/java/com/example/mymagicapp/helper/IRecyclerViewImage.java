package com.example.mymagicapp.helper;

import androidx.recyclerview.widget.RecyclerView;

public interface IRecyclerViewImage{
    Integer[] getImageIdListToRemove();
    void clearCheckBox();
    void setRemoveMode(boolean removeMode);
    void selectAll();
}
