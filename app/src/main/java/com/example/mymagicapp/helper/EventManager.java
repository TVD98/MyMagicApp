package com.example.mymagicapp.helper;

public class EventManager {
    private static final EventManager instance = new EventManager();

    private EventManager(){}

    public static EventManager getInstance(){
        return instance;
    }

    public OnShowImageClickedListener showImageClickedListener;
    public OnShowImageLongClickedListener showImageLongClickedListener;
    public OnPhotoViewClickedListener photoViewClickedListener;

    public void setOnShowImageClickedListener(OnShowImageClickedListener listener){
        this.showImageClickedListener = listener;
    }

    public void setOnPhotoViewClickedListener(OnPhotoViewClickedListener listener){
        this.photoViewClickedListener = listener;
    }

    public void setOnShowImageLongClickedListener(OnShowImageLongClickedListener listener){
        this.showImageLongClickedListener = listener;
    }
}
