package com.experience.customvkclient.viewmodel;

import androidx.lifecycle.ViewModel;

public class VKViewModel extends ViewModel {

    protected static Integer id;
    protected static String imageUrl;

    public void setId(int friendId) {
        id = friendId;
    }

    void clearId() {
        id = null;
    }

    public void saveImageUrl(String imgUrl){
        imageUrl = imgUrl;
    }

    void clearImageUrl(){
        imageUrl = null;
    }
}
