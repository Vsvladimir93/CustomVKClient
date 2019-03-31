package com.experience.customvkclient.repository.net;

import com.experience.customvkclient.model.Profile;
import com.experience.customvkclient.model.ProfileBuilder;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.List;

public class MyVkApiRequest {

    private static final String LOG_TAG = "MyVkApiRequest";

    /**
     * Request default Profile from Vk
     *
     * @param onResponseListener callback method which allow get response data in UI thread
     *                           at the time of completion
     */
    //TODO: Try Retrofit
    public static void requestVkProfile(OnResponseListener<Profile> onResponseListener) {

        ProfileBuilder profileBuilder = new ProfileBuilder();

        VKRequest requestUser = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_max, bdate, country, city"));
        VKRequest requestPhoto = new VKRequest("photos.get", VKParameters.from(VKApiConst.ALBUM_ID, "profile"));

        VKBatchRequest batch = new VKBatchRequest(requestUser, requestPhoto);
        batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
            @Override
            public void onComplete(VKResponse[] responses) {
                super.onComplete(responses);
                onResponseListener.onResponse(profileBuilder.buildProfile(responses[0].responseString, responses[1].responseString));
            }
        });
    }

    /**
     * Request Profile by id number
     *
     * @param onResponseListener callback method which allow get response data in UI thread
     *                           at the time of completion
     * @param id                 - simple Vk profile id
     */
    public static void requestVkProfileById(OnResponseListener<Profile> onResponseListener, int id) {

        ProfileBuilder profileBuilder = new ProfileBuilder();

        VKRequest requestUser = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_max, bdate, country, city", VKApiConst.USER_ID, id));
        VKRequest requestPhoto = new VKRequest("photos.get", VKParameters.from(VKApiConst.ALBUM_ID, "profile", VKApiConst.OWNER_ID, id));

        VKBatchRequest batch = new VKBatchRequest(requestUser, requestPhoto);
        batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
            @Override
            public void onComplete(VKResponse[] responses) {
                super.onComplete(responses);
                onResponseListener.onResponse(profileBuilder.buildProfile(responses[0].responseString, responses[1].responseString));
            }
        });
    }

    /**
     * Request all friends with minimum data
     *
     * @param onResponseListener callback method which allow get response data in UI thread
     *                           at the time of completion
     */
    public static void requestFriends(OnResponseListener<List<Profile>> onResponseListener) {

        ProfileBuilder profileBuilder = new ProfileBuilder();

        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "photo_max"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                onResponseListener.onResponse(profileBuilder.buildFriends(response.responseString));
            }
        });
    }

}
