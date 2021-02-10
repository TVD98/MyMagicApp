package com.example.mymagicapp.helper;

import android.content.Context;

import com.example.mymagicapp.R;
import com.example.mymagicapp.models.Album;
import com.example.mymagicapp.models.ItemAlbum;
import com.example.mymagicapp.models.MyImage;

public class Constraints {
    public static final int DEFAULT_SPECIAL_IMAGE_ID = 0;
    public static final String TRANSITION_NAME = "TRANSITION_NAME";
    public static final int SPAN_COUNT_ITEM_IMAGE = 4;
    public static final int DEFAULT_INDEX_TO_ADD = -1;
    public static final int INDEX_TO_ADD_IMAGE = 100;
    public static final int DEFAULT_IMAGE_ID = 0;
    public static final String LOCAL_DATE_TIME_MIN = "000";
    public static final int MILLIS_COUNT_IN_MINUTE = 60;
    public static final int MINUTE_COUNT_IN_HOUR = 60;
    public static final int CODE_ID_LENGTH = 8;
    public static final int CARD_DATA_ID = 0;
    public static final int FOOD_DATA_ID = 1;
    public static final int OPTION_DATA_ID = 2;
    public static final int MAX_DATA_COUNT = 3;

    public static int[] imageCardIdList = {R.drawable.card_heart_a, R.drawable.card_heart_2, R.drawable.card_heart_3, R.drawable.card_heart_4, R.drawable.card_heart_5,
            R.drawable.card_heart_6, R.drawable.card_heart_7, R.drawable.card_heart_8, R.drawable.card_heart_9, R.drawable.card_heart_10, R.drawable.card_diamond_a,
            R.drawable.card_diamond_2, R.drawable.card_diamond_3, R.drawable.card_diamond_4, R.drawable.card_diamond_5, R.drawable.card_diamond_6, R.drawable.card_diamond_7,
            R.drawable.card_diamond_8, R.drawable.card_diamond_9, R.drawable.card_diamond_10, R.drawable.card_club_a, R.drawable.card_club_2, R.drawable.card_club_3,
            R.drawable.card_club_4, R.drawable.card_club_5, R.drawable.card_club_6, R.drawable.card_club_7, R.drawable.card_club_8, R.drawable.card_club_9, R.drawable.card_club_10,
            R.drawable.card_sword_a, R.drawable.card_sword_2, R.drawable.card_sword_3, R.drawable.card_sword_4, R.drawable.card_sword_5, R.drawable.card_sword_6, R.drawable.card_sword_7,
            R.drawable.card_sword_8, R.drawable.card_sword_9, R.drawable.card_sword_10, R.drawable.card_heart_j, R.drawable.card_diamond_j, R.drawable.card_club_j, R.drawable.card_sword_j,
            R.drawable.card_heart_q, R.drawable.card_diamond_q, R.drawable.card_club_q, R.drawable.card_sword_q, R.drawable.card_heart_k, R.drawable.card_diamond_k,
            R.drawable.card_club_k, R.drawable.card_sword_k};

    public static int[] imageFoodIdList = {R.drawable.anh_nen_1, R.drawable.anh_nen_2, R.drawable.anh_nen_3};

    public static int[] imageOptionIdList = {R.drawable.share};

    public static ItemAlbum imageListToItemAlbum(int[] list, String nameAlbum){
        ItemAlbum itemAlbum = new ItemAlbum();
        itemAlbum.setName(nameAlbum);
        int cardIdListLength = list.length;
        for (int i = 0; i < cardIdListLength; i++) {
            MyImage image = new MyImage();
            image.setImageId(list[i]);
            image.setName(Integer.toString(i + 1));
            image.setDescription(nameAlbum);
            itemAlbum.addItem(image, DEFAULT_INDEX_TO_ADD);
        }
        return itemAlbum;
    }

    public static ItemAlbum imageOptionIdListToItemAlbum(){
        ItemAlbum itemAlbum = new ItemAlbum();
        String nameAlbum = Integer.toString(OPTION_DATA_ID);
        itemAlbum.setName(nameAlbum);
        MyImage image = new MyImage();
        image.setImageId(imageOptionIdList[0]);
        image.setName("0");
        image.setDescription(nameAlbum);
        itemAlbum.addItem(image, DEFAULT_INDEX_TO_ADD);
        return itemAlbum;
    }

}
