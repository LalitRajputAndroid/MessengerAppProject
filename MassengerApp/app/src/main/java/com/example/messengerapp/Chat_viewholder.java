package com.example.messengerapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat_viewholder extends RecyclerView.ViewHolder {
    CircleImageView chat_img;
    TextView name,recentmsg,chattime;
    TextView msgN;
    public Chat_viewholder(@NonNull View itemView) {
        super(itemView);
        chat_img = itemView.findViewById(R.id.chat_img_id);
        name = itemView.findViewById(R.id.u_name_id);
        recentmsg = itemView.findViewById(R.id.recentmsg_id);
        chattime = itemView.findViewById(R.id.chattime_id);
        msgN = itemView.findViewById(R.id.msgN_id);
    }
}
