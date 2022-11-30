package com.example.messengerapp;

import androidx.recyclerview.widget.RecyclerView;

public class Modal  {

    private int img;
    private String user_name;
    private String recent_msg;
    private String chat_time;
    private String msg_N;

    public Modal(int img, String user_name, String recent_msg, String chat_time, String msg_N) {
        this.img = img;
        this.user_name = user_name;
        this.recent_msg = recent_msg;
        this.chat_time = chat_time;
        this.msg_N = msg_N;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRecent_img() {
        return recent_msg;
    }

    public void setRecent_img(String recent_img) {
        this.recent_msg = recent_img;
    }

    public String getChat_time() {
        return chat_time;
    }

    public void setChat_time(String chat_time) {
        this.chat_time = chat_time;
    }

    public String getMsg_N() {
        return msg_N;
    }

    public void setMsg_N(String msg_N) {
        this.msg_N = msg_N;
    }
}
