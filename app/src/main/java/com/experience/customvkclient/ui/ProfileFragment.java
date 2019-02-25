package com.experience.customvkclient.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.experience.customvkclient.R;
import com.experience.customvkclient.adapter.OnItemClickListener;
import com.experience.customvkclient.adapter.PhotoRecyclerViewAdapter;
import com.experience.customvkclient.model.Photo;
import com.experience.customvkclient.model.Profile;
import com.experience.customvkclient.util.MyVkApiRequest;
import com.experience.customvkclient.util.OnResponseListener;

import java.util.List;

public class ProfileFragment extends Fragment{

    private final static String LOG_TAG = "ProfileFragment";

    @BindView(R.id.profile_info_txv)
    TextView profileInfoTxv;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.profile_avatar)
    ImageView profileAvatar;
    @BindView(R.id.profile_recycler_photo)
    RecyclerView recyclerView;

    private PhotoRecyclerViewAdapter photoRecyclerViewAdapter;
    private NavController navController;
    private Profile profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        navController = NavHostFragment.findNavController(this);



        profile = getArguments() != null ? getArguments().getParcelable("FriendProfile") : null;
        //Log.d(LOG_TAG, profile.getUserName());

        if(profile == null) {
            profile = new Profile();
            progressBar.setIndeterminate(true);
            MyVkApiRequest.requestVkProfile(response -> {
                fillProfile(response);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            });
        } else {
            MyVkApiRequest.requestPhotosById(new OnResponseListener<List<Photo>>() {
                @Override
                public void onResponse(List<Photo> response) {
                    profile.setPhotos(response);
                    fillProfile(profile);
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            }, profile.getId());

        }

    }

    private void fillProfile(Profile profile){
        profileInfoTxv.setText(profile.getUserName());
        Glide.with(profileAvatar).load(profile.getMainPhoto().getUrl()).into(profileAvatar);

        photoRecyclerViewAdapter = new PhotoRecyclerViewAdapter(profile.getPhotos());
        photoRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view1, int position) {
                Log.d(LOG_TAG, profile.getPhotos().get(position).getUrl() + " URL " + position + " position");
                Bundle bundle = new Bundle();
                bundle.putString("url", profile.getPhotos().get(position).getUrl());
                navController = NavHostFragment.findNavController(ProfileFragment.this);
                navController.navigate(R.id.action_profileFragment_to_largePhotoFragment, bundle);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(photoRecyclerViewAdapter);
    }

}
