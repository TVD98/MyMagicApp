package com.example.mymagicapp.models;

public interface IMyCollection {
    void addItem(MyItem item, int index);

    void sort();

    int size();

    MyItem[] toArray();
}
