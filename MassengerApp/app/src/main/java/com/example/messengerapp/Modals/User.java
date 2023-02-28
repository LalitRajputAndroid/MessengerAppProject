package com.example.messengerapp.Modals;

public class User {

    String profileimg;
    String profilename;
    String phonenumber;
    String userID;

    public User() {
    }

    public User(String profileimg, String profilename, String phonenumber, String userID) {
        this.profileimg = profileimg;
        this.profilename = profilename;
        this.phonenumber = phonenumber;
        this.userID = userID;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
