package com.example.messengerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<Chat_viewholder> {

    ArrayList<Modal> chatlist = new ArrayList<>();

    public ChatRecyclerAdapter(ArrayList<Modal> chatlist) {
        this.chatlist = chatlist;
    }

    @NonNull
    @Override
    public Chat_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater  = LayoutInflater.from(parent.getContext());
       View view =  inflater.inflate(R.layout.chat_singlerow,parent,false);

        return new Chat_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Chat_viewholder holder, int position) {

        holder.chat_img.setImageResource(chatlist.get(position).getImg());
        holder.recentmsg.setText(chatlist.get(position).getRecent_img());
        holder.chattime.setText(chatlist.get(position).getChat_time());
        holder.msgN.setText(chatlist.get(position).getMsg_N());

    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }
}
