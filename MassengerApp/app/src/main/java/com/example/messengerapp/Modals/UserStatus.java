package com.example.messengerapp.Modals;

import java.util.ArrayList;

public class UserStatus {
    private String profile_name,profile_image;
    private String lastUpdated;
    private ArrayList<Status> statuses ;

    public UserStatus() {
    }

    public UserStatus(String profile_name, String profile_image, String lastUpdated, ArrayList<Status> statuses) {
        this.profile_name = profile_name;
        this.profile_image = profile_image;
        this.lastUpdated = lastUpdated;
        this.statuses = statuses;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(ArrayList<Status> statuses) {
        this.statuses = statuses;
    }
}
