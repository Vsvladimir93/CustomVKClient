package com.experience.customvkclient.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.experience.customvkclient.model.Profile;
import com.experience.customvkclient.model.repository.net.MyVkApiRequest;
import com.experience.customvkclient.model.repository.net.OnResponseListener;
import java.util.List;

public class FriendsViewModel extends ViewModel {

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
