package com.experience.customvkclient.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Profile implements Parcelable {

    private final static String LOG_TAG = "Profile";
    private int id;
    private String userName;
    private Photo mainPhoto;
    private String online;
    private List<Photo> photos;

    protected Profile(Parcel in) {
        id = in.readInt();
        userName = in.readString();
        online = in.readString();
        mainPhoto = in.readParcelable(Photo.class.getClassLoader());
        photos = new ArrayList<>();
        in.readTypedList(photos, Photo.CREATOR);
    }

    public Profile(){}

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userName);
        dest.writeString(online);
        dest.writeParcelable(mainPhoto, flags);
        dest.writeTypedList(photos);
    }

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

    public Photo getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(Photo mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void parseFromJson(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt("id");
            userName = String.format("%s %s"
                    , jsonObject.getString("first_name")
                    , jsonObject.getString("last_name"));
            mainPhoto = new Photo(jsonObject.getString("photo_max"));
            online =  (jsonObject.getInt("online") == 1) ? "Online" : "Offline";
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }
}
