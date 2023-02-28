package com.example.messengerapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerapp.Activitys.singlechat_Activity;
import com.example.messengerapp.Modals.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersViewholder> {

    ArrayList<User> chatlist;

    public UsersAdapter(ArrayList<User> chatlist) {
        this.chatlist = chatlist;
    }

//    public void setfilfered_list(ArrayList<User> filterlist){
//        this.chatlist =filterlist;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public UsersViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chat_singlerow, parent, false);

        return new UsersViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewholder holder, int position) {
        User user = chatlist.get(position);

        String senderID = FirebaseAuth.getInstance().getUid();
        String senderroom = senderID + user.getUserID();


        FirebaseDatabase.getInstance().getReference().child("UserChats").child(senderroom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String lastmsg = snapshot.child("lastmsg").getValue(String.class);
                            String time = snapshot.child("lastmsgtime").getValue(String.class);

                            holder.lastmsg.setText(lastmsg);
                            holder.lastmsgtime.setText(time);

                        } else {

                            holder.lastmsg.setText("Tab to chat");
                            holder.lastmsgtime.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.user_name.setText(user.getProfilename());
        Picasso.get().load(user.getProfileimg())
                .placeholder(R.drawable.profileimg)
                .into(holder.chat_img);

        holder.singlerow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String u_name = holder.user_name.getText().toString();

                Intent intent = new Intent(view.getContext(), singlechat_Activity.class);
                intent.putExtra("name", u_name);
                intent.putExtra("uid", user.getUserID());
                intent.putExtra("profileimage", user.getProfileimg());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }
}
