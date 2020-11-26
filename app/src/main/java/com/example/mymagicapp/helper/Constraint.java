package com.example.mymagicapp.helper;

import com.example.mymagicapp.R;

public class Constraint {
    public static final int DEFAULT_IMAGE_DATA_ID = 0;
    public static final String TRANSITION_NAME = "TRANSITION_NAME";

    public static int[] imageCardIdList = {R.drawable.card_sword_k, R.drawable.card_heart_a, R.drawable.card_heart_2, R.drawable.card_heart_3, R.drawable.card_heart_4, R.drawable.card_heart_5,
            R.drawable.card_heart_6, R.drawable.card_heart_7, R.drawable.card_heart_8, R.drawable.card_heart_9, R.drawable.card_heart_10, R.drawable.card_diamond_a,
            R.drawable.card_diamond_2, R.drawable.card_diamond_3, R.drawable.card_diamond_4, R.drawable.card_diamond_5, R.drawable.card_diamond_6, R.drawable.card_diamond_7,
            R.drawable.card_diamond_8, R.drawable.card_diamond_9, R.drawable.card_diamond_10, R.drawable.card_club_a, R.drawable.card_club_2, R.drawable.card_club_3,
            R.drawable.card_club_4, R.drawable.card_club_5, R.drawable.card_club_6, R.drawable.card_club_7, R.drawable.card_club_8, R.drawable.card_club_9, R.drawable.card_club_10,
            R.drawable.card_sword_a, R.drawable.card_sword_2, R.drawable.card_sword_3, R.drawable.card_sword_4, R.drawable.card_sword_5, R.drawable.card_sword_6, R.drawable.card_sword_7,
            R.drawable.card_sword_8, R.drawable.card_sword_9, R.drawable.card_sword_10, R.drawable.card_heart_j, R.drawable.card_diamond_j, R.drawable.card_club_j, R.drawable.card_sword_j,
            R.drawable.card_heart_q, R.drawable.card_diamond_q, R.drawable.card_club_q, R.drawable.card_sword_q, R.drawable.card_heart_k, R.drawable.card_diamond_k,
            R.drawable.card_club_k};

    public static int getImageDataId(int imgId, int dataId) {
        if (dataId == DEFAULT_IMAGE_DATA_ID)
            return imageCardIdList[imgId % imageCardIdList.length];
        else return R.drawable.blank_circle;
    }
}
