package com.experience.customvkclient.model;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Profile {

    private final static String LOG_TAG = "Profile";
    private int id;
    private String userName;
    //TODO change String to Boolean - isOnline
    private String online;
    private String mainPhotoUrl;
    private List<String> photosUrl = new ArrayList<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getMainPhotoUrl() {
        return mainPhotoUrl;
    }

    public void setMainPhotoUrl(String mainPhotoUrl) {
        this.mainPhotoUrl = mainPhotoUrl;
    }

    public List<String> getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(List<String> photosUrl) {
        this.photosUrl = photosUrl;
    }

    //TODO delete when inject GSON
    public void parseFromJson(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt("id");
            userName = String.format("%s %s"
                    , jsonObject.getString("first_name")
                    , jsonObject.getString("last_name"));
            mainPhotoUrl = jsonObject.getString("photo_max");
            online = (jsonObject.getInt("online") == 1) ? "Online" : "Offline";
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }
}
