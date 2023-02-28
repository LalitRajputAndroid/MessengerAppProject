package com.example.messengerapp;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersViewholder extends RecyclerView.ViewHolder {
    CircleImageView chat_img;
    TextView user_name, lastmsg, lastmsgtime;
    TextView msgN;
    RelativeLayout singlerow;

    public UsersViewholder(@NonNull View itemView) {
        super(itemView);
        chat_img = itemView.findViewById(R.id.chat_img_id);
        user_name = itemView.findViewById(R.id.u_name_id);
        lastmsg = itemView.findViewById(R.id.lastmsg_id);
        lastmsgtime = itemView.findViewById(R.id.lastmsg_time_id);
        msgN = itemView.findViewById(R.id.msgN_id);
        singlerow = itemView.findViewById(R.id.singlerow_id);
    }
}
