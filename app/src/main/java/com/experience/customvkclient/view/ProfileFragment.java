package com.experience.customvkclient.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.experience.customvkclient.R;
import com.experience.customvkclient.model.Photo;
import com.experience.customvkclient.view.adapter.OnItemClickListener;
import com.experience.customvkclient.view.adapter.PhotoRecyclerViewAdapter;
import com.experience.customvkclient.model.Profile;
import com.experience.customvkclient.viewmodel.ProfileViewModel;

import java.util.List;

public class ProfileFragment extends Fragment implements OnItemClickListener, View.OnClickListener {

    private final static String LOG_TAG = "ProfileFragment";

    @BindView(R.id.profile_name_txv)
    TextView profileNameTxv;
    @BindView(R.id.profile_bdate)
    TextView profileBdate;
    @BindView(R.id.profile_location)
    TextView profileLocation;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.profile_avatar)
    ImageView profileAvatar;
    @BindView(R.id.profile_recycler_photo)
    RecyclerView recyclerView;

    private Profile currentProfile = new Profile();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ProfileViewModel profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.getProfile().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                currentProfile = profile;
                fillProfile(profile);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    //TODO Profile.Credentials - might be null
    private void fillProfile(Profile profile) {
        profileNameTxv.setText(String.format("%s %s", profile.getFirstName(), profile.getLastName()));
        profileBdate.setText(profile.getBdate());
        profileLocation.setText(checkForm(profile.getCountry().getTitle(), profile.getCity().getTitle()));
        profileAvatar.setOnClickListener(this);
        profileAvatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(profileAvatar)
                .load(profile.getPhotosUrl().get(0).getPhoto604())
                .into(profileAvatar);
        photoRecyclerInit(profile.getPhotosUrl());
    }

    private String checkForm(String country, String city) {
        String result = "";
        if (!TextUtils.isEmpty(country)) {
            result = country;
            if(!TextUtils.isEmpty(city)){
                result = result.concat(String.format(", %s", city));
            }
        } else if(!TextUtils.isEmpty(city)){
            result = city;
        }
        return result;
    }

    private void photoRecyclerInit(List<Photo> photos) {
        PhotoRecyclerViewAdapter photoRecyclerViewAdapter = new PhotoRecyclerViewAdapter(photos);
        photoRecyclerViewAdapter.setOnItemClickListener(ProfileFragment.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(photoRecyclerViewAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("url", currentProfile.getPhotosUrl().get(position).getPhoto604());
        NavController navController = NavHostFragment.findNavController(ProfileFragment.this);
        navController.navigate(R.id.action_profileFragment_to_largePhotoFragment, bundle);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("url", currentProfile.getPhotosUrl().get(0).getPhoto604());
        NavController navController = NavHostFragment.findNavController(ProfileFragment.this);
        navController.navigate(R.id.action_profileFragment_to_largePhotoFragment, bundle);
    }
}
