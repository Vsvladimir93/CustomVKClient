package com.experience.customvkclient;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKUsersArray;

public class MainActivity extends AppCompatActivity {

    String[] permis = {Manifest.permission.INTERNET};
    TextView txView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VKSdk.login(this, permis);

        txView = findViewById(R.id.text_view);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.d("LOG_TAG", "Success");

                VKRequest.VKRequestListener listener = new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        Log.d("LOG_TAG", response.toString());
                        super.onComplete(response);
                    }

                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                    }
                };

                VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.USER_IDS, "1,63153121"));
                request.executeWithListener(listener);

            }

            @Override
            public void onError(VKError error) {
                Log.d("LOG_TAG", "Failure");
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
