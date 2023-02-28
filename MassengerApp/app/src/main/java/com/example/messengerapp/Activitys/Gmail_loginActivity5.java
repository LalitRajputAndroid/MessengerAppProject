package com.example.messengerapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.messengerapp.R;
import com.google.android.material.button.MaterialButton;

public class Gmail_loginActivity5 extends AppCompatActivity {

    TextView textView;
    EditText emailED;
    MaterialButton verifyemailbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail_login5);

        textView = findViewById(R.id.loginwithnumber_id);
        emailED = findViewById(R.id.gmailedittext_id);
        verifyemailbtn = findViewById(R.id.gmailgetotp_btn_id);

        verifyemailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Gmail_loginActivity5.this, GetOTP_Activity3.class));
            }
        });

    }
}