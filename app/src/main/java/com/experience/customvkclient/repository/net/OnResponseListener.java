package com.experience.customvkclient.repository.net;

public interface OnResponseListener<T> {

    void onResponse(T response);

}
