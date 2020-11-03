package com.example.mymagicapp.helper;

public class EventManager {
    private static final EventManager instance = new EventManager();

    private EventManager(){}

    public static EventManager getInstance(){
        return instance;
    }

    public OnUploadImageClickedListener clickedListener;
    public OnUploadImageLongClickedListener longClickedListener;
    public OnCheckBoxImageClickedListener checkBoxImageClickedListener;
    public OnShowImageClickedListener showImageClickedListener;
    public OnPhotoViewClickedListener photoViewClickedListener;

    public void setOnUploadImageClickedListener(OnUploadImageClickedListener listener) {
        this.clickedListener = listener;
    }

    public void setOnUploadImageLongClickedListener(OnUploadImageLongClickedListener listener) {
        this.longClickedListener = listener;
    }

    public void setOnCheckBoxImageClickedListener(OnCheckBoxImageClickedListener listener){
        this.checkBoxImageClickedListener = listener;
    }

    public void setOnShowImageClickedListener(OnShowImageClickedListener listener){
        this.showImageClickedListener = listener;
    }

    public void setOnPhotoViewClickedListener(OnPhotoViewClickedListener listener){
        this.photoViewClickedListener = listener;
    }
}
