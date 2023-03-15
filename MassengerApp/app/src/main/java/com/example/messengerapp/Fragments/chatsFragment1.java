package com.example.messengerapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.messengerapp.UsersAdapter;
import com.example.messengerapp.Modals.User;
import com.example.messengerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chatsFragment1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public chatsFragment1() {

    }


    public static chatsFragment1 newInstance(String param1, String param2) {
        chatsFragment1 fragment = new chatsFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView recyclerView;
    ArrayList<User> chatlist;
    FirebaseDatabase database ;
    FirebaseAuth auth ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats1, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        chatlist = new ArrayList<>();

        UsersAdapter recyclerAdapter = new UsersAdapter(chatlist);
        recyclerView.setAdapter(recyclerAdapter);

        database = FirebaseDatabase.getInstance();

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatlist.clear();
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);

                    if(!user.getUserID().equals(FirebaseAuth.getInstance().getUid())) {

                        chatlist.add(user);
                    }
                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {//
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String currantUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.getReference().child("Presence").child(currantUID).setValue("Online");
    }

    @Override
    public void onPause() {
        super.onPause();
        String currantUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.getReference().child("Presence").child(currantUID).setValue("offline");
    }
}