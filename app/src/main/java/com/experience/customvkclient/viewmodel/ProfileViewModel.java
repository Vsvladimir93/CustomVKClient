package com.experience.customvkclient.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.experience.customvkclient.model.Profile;
import com.experience.customvkclient.repository.net.MyVkApiRequest;
import com.experience.customvkclient.repository.net.OnResponseListener;

public class ProfileViewModel extends VKViewModel {

    private MutableLiveData<Profile> profileMutLive;

    public LiveData<Profile> getProfile() {
        if (profileMutLive == null) {
            profileMutLive = new MutableLiveData<>();
            loadProfile();
        }
        return profileMutLive;
    }

    /**
     * This method provide opportunity open friends Profile like default yours.
     *   id != null mean that switch-over between FriendsListFragment and ProfileFragment
     * occured when you click on friend profile in FriendsListF. then FriendsViewModel
     * assign id of friend profile to static int id in common parent
     * class VkViewModel {@link VKViewModel};
     *  So when id != null mean it's friend's profile will load
     */
    private void loadProfile() {
        if (id == null) {
            MyVkApiRequest.requestVkProfile(new OnResponseListener<Profile>() {
                @Override
                public void onResponse(Profile response) {
                    profileMutLive.setValue(response);
                }
            });
        } else {
            MyVkApiRequest.requestVkProfileById(new OnResponseListener<Profile>() {
                @Override
                public void onResponse(Profile response) {
                    profileMutLive.setValue(response);
                    clearId();
                }
            }, id);
        }
    }

}
