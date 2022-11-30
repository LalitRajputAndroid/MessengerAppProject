package com.example.messengerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class otp_verification_Activity4 extends AppCompatActivity {

    MaterialTextView textView;
    MaterialButton nextbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification4);

        textView = findViewById(R.id.verifiNum_id);
        nextbtn = findViewById(R.id.nextbtn_id);

        String t1 = getIntent().getStringExtra("M_number");
        textView.setText(t1);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(otp_verification_Activity4.this,setprofile_Activity7.class));
            }
        });
    }
}