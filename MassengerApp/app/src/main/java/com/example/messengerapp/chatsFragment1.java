package com.example.messengerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class chatsFragment1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chats1,container,false);
        recyclerView = view.findViewById(R.id.recyclerview_id);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ArrayList<Modal> chatlist = new ArrayList<>();

        chatlist.add(new Modal(R.drawable.img1,"Lalit","hlo dear......","10.30PM","2"));
        chatlist.add(new Modal(R.drawable.img2,"Gourav","Whats your name ? ","today","5"));

        chatlist.add(new Modal(R.drawable.a,"Henry Enricks","I am doing good.Get well soon","10:31 Am","8"));
        chatlist.add(new Modal(R.drawable.b,"Kevin Gloster","How are you","06:47 Am","10"));
        chatlist.add(new Modal(R.drawable.c,"Sundar Pichai","Hello","Today","14"));
        chatlist.add(new Modal(R.drawable.d,"Nora","Whats your name?","01:21 Am","25"));
        chatlist.add(new Modal(R.drawable.e,"Charistopher","l`ve planned for a dinner","Yesterday","1"));
        chatlist.add(new Modal(R.drawable.f,"Henry Enricks","I am doing good.Get well soon","10:31 Am","6"));
        chatlist.add(new Modal(R.drawable.g,"Henry Enricks","I am doing good.Get well soon","10:31 Am","2"));
        chatlist.add(new Modal(R.drawable.h,"Henry Enricks","I am doing good.Get well soon","10:31 Am","11"));
        chatlist.add(new Modal(R.drawable.i,"Henry Enricks","I am doing good.Get well soon","10:31 Am","12"));
        chatlist.add(new Modal(R.drawable.j,"Henry Enricks","I am doing good.Get well soon","10:31 Am","4"));
        chatlist.add(new Modal(R.drawable.k,"Henry Enricks","I am doing good.Get well soon","10:31 Am","7"));
        chatlist.add(new Modal(R.drawable.l,"Henry Enricks","I am doing good.Get well soon","10:31 Am","6"));

        ChatRecyclerAdapter recyclerAdapter = new ChatRecyclerAdapter(chatlist);
        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }
}