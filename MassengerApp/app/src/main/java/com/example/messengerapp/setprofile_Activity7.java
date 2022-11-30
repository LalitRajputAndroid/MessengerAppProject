package com.example.messengerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class setprofile_Activity7 extends AppCompatActivity {

    MaterialButton continuebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setprofile7);

        continuebtn = findViewById(R.id.continuebtn_id);

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(setprofile_Activity7.this,MainActivity.class));
            }
        });
    }
}