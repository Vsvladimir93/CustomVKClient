package com.experience.customvkclient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Profile {

    private final static String LOG_TAG = "Profile";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("bdate")
    @Expose
    private String bdate;
    @SerializedName("city")
    @Expose
    private City city = new City();
    @SerializedName("country")
    @Expose
    private Country country = new Country();
    @SerializedName("photo_max")
    @Expose
    private String photoMax;
    @SerializedName("online")
    @Expose
    private String online;

    private List<Photo> photosUrl = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getPhotoMax() {
        return photoMax;
    }

    public void setPhotoMax(String photoMax) {
        this.photoMax = photoMax;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public List<Photo> getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(List<Photo> photosUrl) {
        this.photosUrl = photosUrl;
    }

}
