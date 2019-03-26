package com.experience.customvkclient.viewmodel;

import android.graphics.Bitmap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.experience.customvkclient.model.Profile;
import com.experience.customvkclient.model.repository.net.MyVkApiRequest;
import com.experience.customvkclient.model.repository.net.OnResponseListener;
import com.experience.customvkclient.model.repository.net.PhotoRequest;
import java.util.List;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<Profile> profileMutLive;
    private MutableLiveData<Bitmap> photo;
    private static Integer id;

    public LiveData<Profile> getProfile() {
        if (profileMutLive == null) {
            profileMutLive = new MutableLiveData<>();
            loadProfile();
        }
        return profileMutLive;
    }

    /*public LiveData<Bitmap> getPhoto(){
        if(photo == null){
            photo = new MutableLiveData<>();
            loadPhoto(getProfile().getValue().getMainPhotoUrl());
        }
        return photo;
    }*/

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

    public void loadPhoto(String url, OnResponseListener<Bitmap> onResponseListener) {
        new PhotoRequest().makeRequest(url, onResponseListener);
    }

    public void setId(int friendId) {
        id = friendId;
    }

    private void clearId() {
        id = null;
    }
}
