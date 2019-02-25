package com.experience.customvkclient.util;

import android.util.Log;

import com.experience.customvkclient.model.Photo;
import com.experience.customvkclient.model.Profile;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MyVkApiRequest {

    private static final String LOG_TAG = "MyVkApiRequest";

    public static void requestVkProfile(OnResponseListener<Profile> onResponseListener) {

        Profile profile = new Profile();

        VKRequest requestUser = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_max"));
        VKRequest requestPhoto = new VKRequest("photos.get", VKParameters.from(VKApiConst.ALBUM_ID, "profile"));

        VKBatchRequest batch = new VKBatchRequest(requestUser, requestPhoto);
        batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
            @Override
            public void onComplete(VKResponse[] responses) {
                super.onComplete(responses);
                try {
                    //first response profile info
                    JSONObject jsonObject = new JSONObject(responses[0].responseString);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    //second response photos
                    JSONObject obj = new JSONObject(responses[1].json.toString());
                    JSONObject response = obj.getJSONObject("response");
                    JSONArray photosArr = response.getJSONArray("items");

                    int count = response.getInt("count");
                    List<Photo> photosList = new ArrayList<>();
                    for(int i = count - 1; i >= 0; i--){
                        JSONObject photo = photosArr.getJSONObject(i);
                        photosList.add(new Photo(photo.get("photo_604").toString()));
                    }
                    profile.parseFromJson(jsonArray.getJSONObject(0));
                    profile.setPhotos(photosList);
                } catch (Exception e) {
                    profile.setUserName("Error");
                    Log.e(LOG_TAG, e.getMessage());
                }

                Log.d(LOG_TAG, responses[1].json.toString());
                onResponseListener.onResponse(profile);
            }
        });
    }

    public static void requestFriends(OnResponseListener<List<Profile>> onResponseListener) {
        List<Profile> friends = new ArrayList<>();

        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "photo_max"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    JSONObject obj = new JSONObject(response.responseString);
                    JSONObject resp = obj.getJSONObject("response");
                    JSONArray items = resp.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        Profile profile = new Profile();
                        profile.parseFromJson(
                                new JSONObject(items.get(i).toString()));
                        friends.add(i,profile);
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
                onResponseListener.onResponse(friends);
            }
        });
    }

    public static void requestPhotosById(OnResponseListener<List<Photo>> onResponseListener, int id){

        List<Photo> photos = new ArrayList<>();
        VKRequest requestPhoto = new VKRequest("photos.get",
                VKParameters.from(VKApiConst.ALBUM_ID, "profile", VKApiConst.OWNER_ID, id));
        requestPhoto.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    JSONObject obj = new JSONObject(response.json.toString());
                    JSONObject resp = obj.getJSONObject("response");
                    JSONArray photosArr = resp.getJSONArray("items");

                    int count = resp.getInt("count");
                    for (int i = count - 1; i >= 0; i--) {
                        JSONObject photo = photosArr.getJSONObject(i);
                        photos.add(new Photo(photo.get("photo_604").toString()));
                    }
                }catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
                onResponseListener.onResponse(photos);
            }
        });

    }

}
