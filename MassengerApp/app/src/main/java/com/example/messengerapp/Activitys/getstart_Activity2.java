package com.example.messengerapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.messengerapp.R;
import com.google.android.material.button.MaterialButton;

public class getstart_Activity2 extends AppCompatActivity {

    MaterialButton startbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstart2);

        startbtn = findViewById(R.id.startbtn_id);

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getstart_Activity2.this, GetOTP_Activity3.class));
            }
        });
    }
}