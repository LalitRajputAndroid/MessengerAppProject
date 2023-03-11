package com.example.messengerapp.Modals;

public class Status {
    private String s_imgurl;
    private String s_time;

    public Status() {
    }

    public Status(String s_imgurl, String s_time) {
        this.s_imgurl = s_imgurl;
        this.s_time = s_time;
    }

    public String getS_imgurl() {
        return s_imgurl;
    }

    public void setS_imgurl(String s_imgurl) {
        this.s_imgurl = s_imgurl;
    }

    public String getS_time() {
        return s_time;
    }

    public void setS_time(String s_time) {
        this.s_time = s_time;
    }
}
