package com.experience.customvkclient.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.experience.customvkclient.model.Profile;
import com.experience.customvkclient.repository.net.MyVkApiRequest;
import com.experience.customvkclient.repository.net.OnResponseListener;

import java.util.List;

public class FriendsViewModel extends VKViewModel {

    private MutableLiveData<List<Profile>> friendsMutLive;

    public LiveData<List<Profile>> getFriends() {
        if (friendsMutLive == null) {
            friendsMutLive = new MutableLiveData<>();
            loadFriends();
        }
        return friendsMutLive;
    }

    private void loadFriends() {
        MyVkApiRequest.requestFriends(new OnResponseListener<List<Profile>>() {
            @Override
            public void onResponse(List<Profile> response) {
                friendsMutLive.setValue(response);
            }
        });
    }
}
