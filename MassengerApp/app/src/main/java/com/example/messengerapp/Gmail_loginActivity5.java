package com.example.messengerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

import org.w3c.dom.Text;

public class Gmail_loginActivity5 extends AppCompatActivity {

 TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail_login5);

        textView=findViewById(R.id.loginwithnumber_id);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Gmail_loginActivity5.this,GetOTP_Activity3.class));
            }
        });

    }
}