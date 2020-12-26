package com.example.mymagicapp.models;

public interface IMyCollection {
    void addItem(MyItem item, int index);

    void removeItem(MyItem item);

    void removeItem(int index);

    MyItem getItem(int index);

    void sort();

    int size();

    MyItem[] toArray();
}
