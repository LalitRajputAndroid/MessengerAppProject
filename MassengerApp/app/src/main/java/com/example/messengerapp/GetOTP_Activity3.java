package com.example.messengerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Random;

public class GetOTP_Activity3 extends AppCompatActivity {

    EditText mobilenumber;
    MaterialButton getotp_btn;
    MaterialTextView okbtm,editnumberbtn,setnumbertext,loginwithgmail_text;

    private static final String channal_id = "my channel";
    private static final int Notification_id = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_otp3);

        mobilenumber = findViewById(R.id.mobilenumber_id);
        getotp_btn = findViewById(R.id.getotp_btn_id);
        loginwithgmail_text = findViewById(R.id.loginwithgmail_text_id);

        loginwithgmail_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GetOTP_Activity3.this,Gmail_loginActivity5.class));
            }
        });

        String ot = String.valueOf(otpgen(4));

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.messengericon, null);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;

        Bitmap large_icon = bitmapDrawable.getBitmap();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this)
                    .setLargeIcon(large_icon)
                    .setSmallIcon(R.drawable.messengericon)
                    .setSubText("OTP for your Messenger account.")
                    .setContentText(ot + " is your Messenger varification code.")
                    .setChannelId(channal_id)
                    .build();

            nm.createNotificationChannel(new NotificationChannel(channal_id, "new chennal", NotificationManager.IMPORTANCE_HIGH));
        } else {

            notification = new Notification.Builder(this)
                    .setLargeIcon(large_icon)
                    .setSmallIcon(R.drawable.messengericon)
                    .setSubText("OTP for your Signal account.")
                    .setContentText(ot)
                    .build();
        }

        getotp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String M_number = mobilenumber.getText().toString();

                if (M_number.isEmpty()){
                    Toast.makeText(GetOTP_Activity3.this, "Please Enter a number ", Toast.LENGTH_SHORT).show();
                }else {
                    Dialog dialog = new Dialog(GetOTP_Activity3.this);
                    dialog.setContentView(R.layout.alertdailog_layout);

                    okbtm = dialog.findViewById(R.id.okbtn_id);
                    editnumberbtn = dialog.findViewById(R.id.edit_numbtn_id);
                    setnumbertext = dialog.findViewById(R.id.setnumbertextV_id);

                    setnumbertext.setText(M_number);
                    dialog.show();

                    Intent intent = new Intent(GetOTP_Activity3.this,otp_verification_Activity4.class);

                    okbtm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            nm.notify(Notification_id, notification);
                            intent.putExtra("otp",ot);
                            intent.putExtra("number",M_number);
                            startActivity(intent);
                        }
                    });

                    editnumberbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(GetOTP_Activity3.this,GetOTP_Activity3.class));
                        }
                    });
                }
            }
        });
    }

    private static char[] otpgen(int length){
        String number = "1234567890";
        Random random = new Random();
        char[] otp = new char[length];

        for (int i=0;i<length;i++)
        {
            otp[i] = number.charAt(random.nextInt(number.length()));
        }
        return otp;
    }
}