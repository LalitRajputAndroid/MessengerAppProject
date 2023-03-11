package com.example.messengerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerapp.Modals.MessageModal;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModal> chatModals;
    Context context;

    final int SENDER_VIEW_TYPE = 1;
    final int RECIVER_VIEW_TYPE = 2;

    public MessageAdapter(ArrayList<MessageModal> chatModals, Context context) {
        this.chatModals = chatModals;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SENDER_VIEW_TYPE) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.sendmsg_layout, parent, false);

            return new SenderViewHolder(view);

        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recivemsg_layout, parent, false);

            return new ReciverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (chatModals.get(position).getSenderID().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        } else {
            return RECIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModal modal = chatModals.get(position);

        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(new int[]{
                        R.drawable.ic_fb_like,
                        R.drawable.ic_fb_love,
                        R.drawable.ic_fb_laugh,
                        R.drawable.ic_fb_wow,
                        R.drawable.ic_fb_sad,
                        R.drawable.ic_fb_angry
                })
                .build();

        ReactionPopup popup = new ReactionPopup(context, config, (pos) -> {
            return true; // true is closing popup, false is requesting a new selection
        });

        if (holder.getClass() == SenderViewHolder.class) {
            SenderViewHolder viewHolder = (SenderViewHolder) holder;

//            if (modal.getMessage().equals("photo")){
//
//                viewHolder.sendImgs.setVisibility(View.VISIBLE);
//                viewHolder.sendermsg.setVisibility(View.GONE);
//                Picasso.get().load(modal.getImgurl()).placeholder(R.drawable.placeholder_image)
//                        .into(((SenderViewHolder) holder).sendImgs);
//            }

            viewHolder.sendermsg.setText(modal.getMessage());
            viewHolder.sendertime.setText(String.valueOf(modal.getMsgtime()));

            viewHolder.sendermsg.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v, event);
                    return false;
                }
            });

        } else {
            ReciverViewHolder viewHolder = (ReciverViewHolder) holder;

//            if (modal.getMessage().equals("photo")){
//
//                viewHolder.sendImgr.setVisibility(View.VISIBLE);
//                viewHolder.recivermsg.setVisibility(View.GONE);
//                Picasso.get().load(modal.getImgurl()).placeholder(R.drawable.placeholder_image)
//                        .into(((ReciverViewHolder) holder).sendImgr);
//            }

            viewHolder.recivermsg.setText(modal.getMessage());
            viewHolder.recivertime.setText(String.valueOf(modal.getMsgtime()));

            viewHolder.recivermsg.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v, event);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return chatModals.size();
    }

    public class ReciverViewHolder extends RecyclerView.ViewHolder {
        TextView recivermsg, recivertime;
        ImageView sendImgr;

        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);
            recivermsg = itemView.findViewById(R.id.recivermsg_id);
            recivertime = itemView.findViewById(R.id.recivertime_id);
            sendImgr = itemView.findViewById(R.id.sendImgReciver_id);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView sendermsg, sendertime;
        ImageView sendImgs;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            sendermsg = itemView.findViewById(R.id.sendermsg_id);
            sendertime = itemView.findViewById(R.id.sendertime_id);
            sendImgs = itemView.findViewById(R.id.sendImgSender_id);
        }
    }

}
