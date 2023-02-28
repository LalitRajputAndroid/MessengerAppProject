package com.example.messengerapp.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerapp.MessageAdapter;
import com.example.messengerapp.Modals.MessageModal;
import com.example.messengerapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class singlechat_Activity extends AppCompatActivity {

    TextView profile_name;
    ImageView backarrow;
    CircleImageView profile_image;
    EditText typemsg;
    FloatingActionButton sendmsgBtn;
    RecyclerView recyclerView;
    Toolbar toolbar;
    DatabaseReference database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlechat);

        profile_name = findViewById(R.id.Username_id);
        toolbar = findViewById(R.id.toolbar_id);
        backarrow = findViewById(R.id.back_id);
        profile_image = findViewById(R.id.p_img_id);
        recyclerView = findViewById(R.id.userchats_recyclerID);

        sendmsgBtn = findViewById(R.id.sendmsg_btn_id);
        typemsg = findViewById(R.id.typemessage_id);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        database = FirebaseDatabase.getInstance().getReference();
        database.keepSynced(true);
        auth = FirebaseAuth.getInstance();

        final String sender_id = auth.getUid();
        String reciver_id = getIntent().getStringExtra("uid");

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final ArrayList<MessageModal> chatlist = new ArrayList<>();
        final MessageAdapter adapter = new MessageAdapter(chatlist, this);

        recyclerView.setAdapter(adapter);

        final String senderRoom = sender_id + reciver_id;
        final String reciverRoom = reciver_id + sender_id;


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
                DateFormat format = new SimpleDateFormat("h:mm a");
                String time = format.format(Calendar.getInstance().getTime());

                final MessageModal modal = new MessageModal(message, sender_id, time);
                typemsg.setText("");

                String key = database.push().getKey();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("lastmsg",modal.getMessage());
                hashMap.put("lastmsgtime",time);

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
}