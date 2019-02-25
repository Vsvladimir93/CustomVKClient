package com.experience.customvkclient.util;

import com.experience.customvkclient.model.Profile;

public interface OnResponseListener<T> {

    void onResponse(T response);

}
