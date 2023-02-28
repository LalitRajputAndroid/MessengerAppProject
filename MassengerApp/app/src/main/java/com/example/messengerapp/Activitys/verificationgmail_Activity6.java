package com.example.messengerapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.messengerapp.R;
import com.google.android.material.button.MaterialButton;

public class verificationgmail_Activity6 extends AppCompatActivity {

    MaterialButton Gmailnext_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationgmail6);

        Gmailnext_btn = findViewById(R.id.Gmail_nextbtn_id);

        Gmailnext_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(verificationgmail_Activity6.this, setprofile_Activity7.class));
            }
        });
    }
}