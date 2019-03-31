package com.experience.customvkclient.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.experience.customvkclient.model.Photo;

public class LargePhotoViewModel extends VKViewModel {

    private MutableLiveData<Photo> photo;

    public LiveData<Photo> getPhoto(){
        if(photo == null){
            photo = new MutableLiveData<>();
            loadPhoto();
        }
        return photo;
    }

    private void loadPhoto(){
        Photo ph = new Photo();
        ph.setPhoto604(imageUrl);
        photo.setValue(ph);
    }

}
