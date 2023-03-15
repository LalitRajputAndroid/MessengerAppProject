package com.example.messengerapp.Activitys;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerapp.MessageAdapter;
import com.example.messengerapp.Modals.MessageModal;
import com.example.messengerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class singlechat_Activity extends AppCompatActivity {

    TextView profile_name, statusVisi;
    ImageView backarrow, fileopenIcon;
    CircleImageView profile_image;
    EditText typemsg;
    FloatingActionButton sendmsgBtn;
    RecyclerView recyclerView;
    Toolbar toolbar;
    DatabaseReference database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    ProgressDialog dialog;

    String sender_id;
    String reciver_id;
    String reciverRoom;
    String senderRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlechat);

        profile_name = findViewById(R.id.Username_id);
        toolbar = findViewById(R.id.toolbar_id);
        backarrow = findViewById(R.id.back_id);
        profile_image = findViewById(R.id.p_img_id);
        recyclerView = findViewById(R.id.userchats_recyclerID);
        fileopenIcon = findViewById(R.id.openmediaIcon_id);
        statusVisi = findViewById(R.id.uservisiblity_id);

        sendmsgBtn = findViewById(R.id.sendmsg_btn_id);
        typemsg = findViewById(R.id.typemessage_id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Sending Image...");
        dialog.setCancelable(false);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        database = FirebaseDatabase.getInstance().getReference();
        database.keepSynced(true);
        auth = FirebaseAuth.getInstance();
//        storage = FirebaseStorage.getInstance().getReference().getStorage();

        sender_id = auth.getUid();
        reciver_id = getIntent().getStringExtra("uid");

        String UN = getIntent().getStringExtra("name");
        String pimg = getIntent().getStringExtra("profileimage");


        profile_name.setText(UN);
        Picasso.get().load(pimg)
                .placeholder(R.drawable.profileimg)
                .into(profile_image);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(singlechat_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        database.child("Presence").child(reciver_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    String status = snapshot.getValue(String.class);
                    if (!status.isEmpty()) {

                        if (status.equals("offline")) {
                            statusVisi.setText(status);
                            statusVisi.setVisibility(View.GONE);
                        } else {
                            statusVisi.setText(status);
                            statusVisi.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(singlechat_Activity.this, "status noo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(singlechat_Activity.this, "snapshot noo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final ArrayList<MessageModal> chatlist = new ArrayList<>();
        final MessageAdapter adapter = new MessageAdapter(chatlist, this);

        recyclerView.setAdapter(adapter);

        reciverRoom = sender_id + reciver_id;
        senderRoom = reciver_id + sender_id;


        database.child("UserChats")
                .child(senderRoom)
                .child("Messages")
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatlist.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            MessageModal modal = dataSnapshot.getValue(MessageModal.class);
                            modal.setMessageID(dataSnapshot.getKey());
                            chatlist.add(modal);

                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(singlechat_Activity.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        sendmsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = typemsg.getText().toString();

                if (!message.isEmpty()) {

                    DateFormat format = new SimpleDateFormat("h:mm a");
                    String time = format.format(Calendar.getInstance().getTime());

                    final MessageModal modal = new MessageModal(message, sender_id, time);
                    typemsg.setText("");

                    String key = database.push().getKey();

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("lastmsg", modal.getMessage());
                    hashMap.put("lastmsgtime", time);

                    database.child("UserChats").child(senderRoom).updateChildren(hashMap);
                    database.child("UserChats").child(reciverRoom).updateChildren(hashMap);


                    database.child("UserChats")
                            .child(senderRoom)
                            .child("Messages")
                            .child(key)
                            .setValue(modal)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    database.child("UserChats")
                                            .child(reciverRoom)
                                            .child("Messages")
                                            .child(key)
                                            .setValue(modal)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            });
                                }
                            });
                }

            }
        });

        fileopenIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 200);
            }
        });

        final Handler handler = new Handler();
        typemsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                database.child("Presence").child(sender_id).setValue("Typing...");
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(runnable, 1000);
            }

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    database.child("Presence").child(sender_id).setValue("Online");
                }
            };
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200) {
            if (data != null) {
                if (data.getData() != null) {
                    Uri selectIMG = data.getData();

                    Calendar calendar = Calendar.getInstance();
                    StorageReference reference = FirebaseStorage.getInstance().getReference().child("Chats").child(calendar.getTimeInMillis() + "");
                    dialog.show();
                    reference.putFile(selectIMG).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {

                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String selectimguri = uri.toString();
                                        Toast.makeText(singlechat_Activity.this, "done" + selectimguri, Toast.LENGTH_SHORT).show();
                                        String message = typemsg.getText().toString();
                                        DateFormat format = new SimpleDateFormat("h:mm a");
                                        String time = format.format(Calendar.getInstance().getTime());

                                        final MessageModal modal = new MessageModal(message, sender_id, time);
                                        modal.setMessage("photo");
                                        modal.setImgurl(selectimguri);

                                        String key = database.push().getKey();

                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("lastmsg", modal.getMessage());
                                        hashMap.put("lastmsgtime", time);

                                        database.child("UserChats").child(senderRoom).updateChildren(hashMap);
                                        database.child("UserChats").child(reciverRoom).updateChildren(hashMap);


                                        database.child("UserChats")
                                                .child(senderRoom)
                                                .child("Messages")
                                                .child(key)
                                                .setValue(modal)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        database.child("UserChats")
                                                                .child(reciverRoom)
                                                                .child("Messages")
                                                                .child(key)
                                                                .setValue(modal)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {

                                                                    }
                                                                });
                                                    }
                                                });

                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(singlechat_Activity.this, "failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println("error" + e);
                        }
                    });
                } else {
                    Toast.makeText(this, "no image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.singlechatmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        String currantUID = FirebaseAuth.getInstance().getUid();
        database.child("Presence").child(currantUID).setValue("Online");
    }
    @Override
    public void onPause() {
        super.onPause();
        String currantUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.child("Presence").child(currantUID).setValue("offline");
    }
}