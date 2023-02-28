package com.example.messengerapp.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.messengerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
public class otp_verification_Activity4 extends AppCompatActivity {

    MaterialTextView textView;
    MaterialButton nextbtn;
    EditText enterotp;
    FirebaseAuth auth;
    String otpid;
    String number;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification4);

        textView = findViewById(R.id.verifiNum_id);
        nextbtn = findViewById(R.id.nextbtn_id);
        enterotp = findViewById(R.id.enterotp_id);

        enterotp.requestFocus();

        number = getIntent().getStringExtra("mobile__number");
        textView.setText(number);

        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setTitle("Verify Your Code");

        initiateotp();
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String otpbox = enterotp.getText().toString();
                if (otpbox.isEmpty()) {
                    Toast.makeText(otp_verification_Activity4.this, "Blank Field can not be processed", Toast.LENGTH_SHORT).show();
                } else if (otpbox.length() != 6) {
                    Toast.makeText(otp_verification_Activity4.this, "Invalid otp", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, otpbox);
                    signInwithphoneAuthcredition(credential);
                }
            }
        });
    }

    private void initiateotp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otpid = s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInwithphoneAuthcredition(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(otp_verification_Activity4.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void signInwithphoneAuthcredition(PhoneAuthCredential credential) {
        dialog.show();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(otp_verification_Activity4.this, "Verify Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(otp_verification_Activity4.this, setprofile_Activity7.class));

                            SharedPreferences preferences = getSharedPreferences("userdata", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("open", true);
                            editor.apply();

                            finishAffinity();
                        } else {
                            Toast.makeText(otp_verification_Activity4.this, "SignIn code Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        dialog.dismiss();
//                        Toast.makeText(otp_verification_Activity4.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
    }
}