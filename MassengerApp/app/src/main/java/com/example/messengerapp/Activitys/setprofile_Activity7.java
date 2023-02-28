package com.example.messengerapp.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.messengerapp.Modals.User;
import com.example.messengerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class setprofile_Activity7 extends AppCompatActivity {

    MaterialButton continuebtn;
    CircleImageView setimgP;

    TextInputEditText profilename;
    private final static int Img_Req_code = 100;

    FirebaseAuth auth;
    DatabaseReference database;
    FirebaseStorage storage;
    Uri selectimg;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setprofile7);

        continuebtn = findViewById(R.id.continuebtn_id);
        setimgP = findViewById(R.id.setimageP_id);
        profilename = findViewById(R.id.profile_name_id);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        database.keepSynced(true);

        storage = FirebaseStorage.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Log in");
        progressDialog.setMessage("Please Wait Processing");
//        progressDialog.setCanceledOnTouchOutside(false);

        setimgP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGalllry = new Intent(Intent.ACTION_PICK);
                iGalllry.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGalllry, Img_Req_code);
            }
        });

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = profilename.getText().toString();
                if (name.isEmpty()) {
                    profilename.setError("Please type  name");
                } else {
                    if (selectimg != null) {
                        progressDialog.show();
                        StorageReference reference = storage.getReference().child("Profiles").child(auth.getUid());
                        reference.putFile(selectimg).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {
                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imgUri = uri.toString();
                                            String number = auth.getCurrentUser().getPhoneNumber();
                                            String uid = auth.getUid();

                                            User user = new User(imgUri, name, number, uid);

                                            database.child("Users").child(uid)
                                                    .setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            progressDialog.dismiss();
                                                            startActivity(new Intent(setprofile_Activity7.this, MainActivity.class));
                                                            profilename.setText("");
                                                            finish();
                                                        }
                                                    });
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        String number = auth.getCurrentUser().getPhoneNumber();
                        String uid = auth.getUid();

                        progressDialog.show();
                        User user = new User("no image", name, number, uid);
                        database.child("Users").child(name + ": " + uid)
                                .setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        startActivity(new Intent(setprofile_Activity7.this, MainActivity.class));
                                        profilename.setText("");
                                        finish();
                                    }
                                });
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == Img_Req_code) {

                if (data != null) {

                    if (data.getData() != null) {
                        selectimg = data.getData();
                        setimgP.setImageURI(selectimg);
                    }
                }
            }
        }
    }
}