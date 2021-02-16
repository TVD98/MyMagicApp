package com.example.mymagicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymagicapp.R;
import com.example.mymagicapp.helper.Constraints;
import com.example.mymagicapp.helper.FirebaseSingleton;
import com.example.mymagicapp.helper.SaveSystem;
import com.example.mymagicapp.helper.Utility;
import com.example.mymagicapp.models.Code;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;

public class ShopActivity extends AppCompatActivity {
    private EditText editText;
    private Button buttonSend;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        setTitle("Shop");

        editText = findViewById(R.id.edit_text_id);
        buttonSend = findViewById(R.id.button_send);

        database = FirebaseDatabase.getInstance().getReference();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String codeId = s.toString();
                updateUI(codeId);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCodeId();
            }
        });
    }

    private void updateUI(String codeId) {
        if (codeId.length() == Constraints.CODE_ID_LENGTH) {
            buttonSend.setEnabled(true);
        } else buttonSend.setEnabled(false);
    }

    private void sendCodeId() {
        FirebaseSingleton.getInstance().database.child("codes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                boolean ready = false;
                for (DataSnapshot child : children
                ) {
                    Code code = child.getValue(Code.class);
                    int compare = checkCodeId(code);
                    if (compare == 0) {
                        updateCode(code);
                        SaveSystem.unlockApp(ShopActivity.this);
                        finishShop();
                        ready = true;
                        break;
                    }
                    else if(compare == -1){
                        ready = true;
                        Toast.makeText(ShopActivity.this, "Code used", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if(!ready){
                    Toast.makeText(ShopActivity.this, "Code wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int checkCodeId(Code code) {
        String codeId = editText.getText().toString();
        if (codeId.compareTo(code.getId()) == 0) {
            if (code.isReady())
                return 0;
            return -1;
        }
        return 1;
    }

    private void updateCode(Code code) {
        DatabaseReference child = database.child("codes").child(code.getId());
        child.child("userName").setValue(Utility.getMacAddress());
        child.child("date").setValue(Utility.localDateTimeToString(LocalDateTime.now().plusHours(-7)));
    }

    private void finishShop() {
        Toast.makeText(this, "Mở khóa thành công", Toast.LENGTH_SHORT).show();
        finish();
    }
}