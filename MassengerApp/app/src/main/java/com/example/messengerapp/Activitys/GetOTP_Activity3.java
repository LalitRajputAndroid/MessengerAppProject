package com.example.messengerapp.Activitys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messengerapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.hbb20.CountryCodePicker;

public class GetOTP_Activity3 extends AppCompatActivity {

    EditText mobilenumber;
    CountryCodePicker ccp;
    MaterialButton getotp_btn;
    MaterialTextView okbtm, editnumberbtn, setnumbertext;
    String M_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_otp3);

        mobilenumber = findViewById(R.id.mobilenumber_id);
        ccp = findViewById(R.id.ccp_id);
        ccp.registerCarrierNumberEditText(mobilenumber);

        getotp_btn = findViewById(R.id.getotp_btn_id);

        mobilenumber.requestFocus();
        getotp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                M_number = mobilenumber.getText().toString();

                if (M_number.isEmpty()) {
                    Toast.makeText(GetOTP_Activity3.this, "Please Enter a number ", Toast.LENGTH_SHORT).show();
                } else if (M_number.length()!=10) {
                    mobilenumber.setError("Enter 10 digit number");
                } else {
                    Dialog dialog = new Dialog(GetOTP_Activity3.this);
                    dialog.setContentView(R.layout.alertdailog_layout);

                    okbtm = dialog.findViewById(R.id.okbtn_id);
                    editnumberbtn = dialog.findViewById(R.id.edit_numbtn_id);
                    setnumbertext = dialog.findViewById(R.id.setnumbertextV_id);
                    setnumbertext.setText(M_number);

                    Intent intent = new Intent(GetOTP_Activity3.this, otp_verification_Activity4.class);

                    okbtm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            intent.putExtra("mobile__number", ccp.getFullNumberWithPlus().replace(" ",""));
                            startActivity(intent);
                        }
                    });

                    editnumberbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
}