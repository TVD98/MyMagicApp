package com.example.mymagicapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.Album;
import com.example.mymagicapp.models.Gallery;
import com.example.mymagicapp.models.ItemAlbum;
import com.example.mymagicapp.models.MyImage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button buttonDelete;
    LinearLayout contain;
    List<View> viewList = new ArrayList<>();
    private String password = "";
    private final int PASSWORD_COUNT_MAX = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hideSystemUI();

        button0 = findViewById(R.id.button_0);
        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);
        button4 = findViewById(R.id.button_4);
        button5 = findViewById(R.id.button_5);
        button6 = findViewById(R.id.button_6);
        button7 = findViewById(R.id.button_7);
        button8 = findViewById(R.id.button_8);
        button9 = findViewById(R.id.button_9);
        buttonDelete = findViewById(R.id.button_delete);
        contain = findViewById(R.id.linearLayout);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        for (int i = 0; i < PASSWORD_COUNT_MAX; i++) {
            viewList.add(contain.getChildAt(i));
        }
    }

    private void updatePasswordView(int count) {
        for (int i = 0; i < viewList.size(); i++) {
            if (i < count) {
                viewList.get(i).setBackgroundResource(R.drawable.selected_circle);
            } else viewList.get(i).setBackgroundResource(R.drawable.blank_circle);
        }
    }

    private void hideOrShowButtonDelete(int count) {
        if (count == 0)
            buttonDelete.setVisibility(View.INVISIBLE);
        else buttonDelete.setVisibility(View.VISIBLE);
    }

    private void updateUI() {
        int count = password.length();
        updatePasswordView(count);
        hideOrShowButtonDelete(count);
    }

    private void login() {
        saveData();
        exitApplication();
    }

    private void exitApplication() {
        finishAffinity();
    }

    private void saveData() {
        MyImage image = passwordToImage();
        Gallery gallery = SaveSystem.getData(this, SaveSystem.KEY_NAME_COLLECTION, Gallery.class); // get gallery from shared..
        MyImage lastSpecialImage = SaveSystem.getData(this, SaveSystem.KEY_NAME_SPECIAL_IMAGE, MyImage.class); // get last special image
        if (lastSpecialImage != null) {
            gallery.removeItem(lastSpecialImage); // delete last data image
        }
        gallery.addItem(image, Constraints.INDEX_TO_ADD_IMAGE); // add image to gallery
        gallery.sort();
        SaveSystem.saveGalleryToShared(gallery, this); // save gallery to shared
        SaveSystem.saveData(this, SaveSystem.KEY_NAME_SPECIAL_IMAGE, image); // save special image selected
    }

    private MyImage passwordToImage() {
        String imageId = password.substring(0, 2);
        int dataId = SaveSystem.getDataId(this);
        Album album = SaveSystem.getData(this, SaveSystem.KEY_NAME_DATA_ALBUM, Album.class);
        MyImage image = album.findImageByName(Constraints.dataNameList[dataId], Integer.toString(Integer.parseInt(imageId)));
        LocalDateTime dateTime = passwordToDateTime();
        image.setDate(Utility.localDateTimeToString(dateTime));
        return image;
    }

    private LocalDateTime passwordToDateTime() {
        int day = Integer.parseInt(password.substring(2, 4)) - 10;
        int month = Integer.parseInt(password.substring(4, 6)) - 10;
        LocalDate date = LocalDate.of(LocalDate.now().getYear(), month, day);
        LocalTime time = Utility.randomTime(8, 20); // random time from 8AM to 20PM
        LocalDateTime dateTime;
        if (date.compareTo(LocalDate.now()) > 0)
            date = LocalDate.of(date.getYear() - 1, month, day);
        dateTime = date.atTime(time);
        return dateTime;
    }

    private void setPassword(String number) {
        int len = password.length();
        if (number.isEmpty()) {
            password = password.substring(0, len - 1);
        } else if (len < PASSWORD_COUNT_MAX) {
            password += number;
        }
        updateUI();
        if (password.length() == PASSWORD_COUNT_MAX) {
            login();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_0:
                setPassword("0");
                break;
            case R.id.button_1:
                setPassword("1");
                break;
            case R.id.button_2:
                setPassword("2");
                break;
            case R.id.button_3:
                setPassword("3");
                break;
            case R.id.button_4:
                setPassword("4");
                break;
            case R.id.button_5:
                setPassword("5");
                break;
            case R.id.button_6:
                setPassword("6");
                break;
            case R.id.button_7:
                setPassword("7");
                break;
            case R.id.button_8:
                setPassword("8");
                break;
            case R.id.button_9:
                setPassword("9");
                break;
            case R.id.button_delete:
                deletePassword();
                break;
        }
    }

    private void deletePassword() {
        if (password.length() > 0) {
            setPassword("");
        }
    }

    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

    }
}