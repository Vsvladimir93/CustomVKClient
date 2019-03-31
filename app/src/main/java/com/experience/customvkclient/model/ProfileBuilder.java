package com.experience.customvkclient.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProfileBuilder {

    private final String LOG_TAG = getClass().getSimpleName();
    private Profile profile;
    private Gson gson;

    public ProfileBuilder() {
        profile = new Profile();
        gson = new Gson();
    }

    /**
     * Create Profile with Vk response in json from two parts
     *
     * @param jsonProfile    first part Profile credentials in json String
     * @param jsonPhotoArray second part Profile's List of Photos in json String
     * @return complete Profile object
     */

    public Profile buildProfile(String jsonProfile, String jsonPhotoArray) {
        parseProfileFromJson(jsonProfile);
        parsePhotoArrayFromJson(jsonPhotoArray);
        return profile;
    }

    /**
     * Create List<Profile> of friends from Vk response in json
     *
     * @param json pure Vk response with friends array
     * @return List<Profile>
     */

    public List<Profile> buildFriends(String json) {
        return parseFriendsFromJson(prepareFriendsArray(json));
    }


    /**
     * Parse json String with Profile credentials and init Profile
     *
     * @param json String with Profile credentials from Vk response
     */
    private void parseProfileFromJson(String json) {
        profile = gson.fromJson(prepareProfileObject(json), Profile.class);
    }

    /**
     * Parsing Json String and initialize Profile's List<Photo> with data
     *
     * @param json String with Photo array from Vk response
     */

    private void parsePhotoArrayFromJson(String json) {
        //get Type of List<String> and pass it to gson for deserialize
        Type photos = new TypeToken<Collection<Photo>>() {
        }.getType();
        profile.setPhotosUrl(gson.fromJson(preparePhotoArray(json), photos));

        Collections.reverse(profile.getPhotosUrl());
    }

    /**
     * Parsing Json String to create array of Profiles which
     * represent friends in List<Profile>
     *
     * @param json already prepared json String with Profile array
     * @return List<Profile>
     */

    private List<Profile> parseFriendsFromJson(String json) {
        Gson gson = new Gson();
        Type friendCol = new TypeToken<Collection<Profile>>() {
        }.getType();
        return gson.fromJson(json, friendCol);

    }

    /**
     * This method cleans Json response from unnecessary parameters
     *
     * @param json - pure JSON String with Photo array, which is taken from Vk response
     * @return prepared String for Gson parsing
     */

    private String preparePhotoArray(String json) {
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject response = obj.getJSONObject("response");
            jsonArray = response.getJSONArray("items");
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return jsonArray.toString();
    }

    /**
     * This method cleans Json response from unnecessary parameters
     *
     * @param json - pure JSON String with Profile credentials, which is taken from Vk response
     * @return prepared String for Gson parsing
     */

    private String prepareProfileObject(String json) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray("response");
            jsonObject = arr.getJSONObject(0);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return jsonObject.toString();
    }

    /**
     * This method cleans Json response from unnecessary parameters
     *
     * @param json - pure JSON String with Profile array, which is taken from Vk response
     * @return prepared String for Gson parsing
     */

    private String prepareFriendsArray(String json) {
        JSONArray items = new JSONArray();
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject resp = obj.getJSONObject("response");
            items = resp.getJSONArray("items");
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return items.toString();
    }

}
