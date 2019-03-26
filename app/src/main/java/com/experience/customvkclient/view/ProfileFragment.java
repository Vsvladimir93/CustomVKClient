package com.experience.customvkclient.view;

import android.graphics.Bitmap;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.experience.customvkclient.R;
import com.experience.customvkclient.view.adapter.OnItemClickListener;
import com.experience.customvkclient.view.adapter.PhotoRecyclerViewAdapter;
import com.experience.customvkclient.model.Profile;
import com.experience.customvkclient.model.repository.net.OnResponseListener;
import com.experience.customvkclient.model.repository.net.PhotoRequest;
import com.experience.customvkclient.viewmodel.ProfileViewModel;
import java.util.List;

public class ProfileFragment extends Fragment implements OnItemClickListener {

    private final static String LOG_TAG = "ProfileFragment";

    @BindView(R.id.profile_info_txv)
    TextView profileInfoTxv;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.profile_avatar)
    ImageView profileAvatar;
    @BindView(R.id.profile_recycler_photo)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
                fillProfile(profile);
                profileViewModel.loadPhoto(profile.getMainPhotoUrl(), new OnResponseListener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        profileAvatar.setImageBitmap(response);

                    }
                });
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void fillProfile(Profile profile) {
        profileInfoTxv.setText(profile.getUserName());
        photoRecyclerInit(profile.getPhotosUrl());
    }

    private void photoRecyclerInit(List<String> photos){
        PhotoRecyclerViewAdapter photoRecyclerViewAdapter = new PhotoRecyclerViewAdapter(photos);
        photoRecyclerViewAdapter.setOnItemClickListener(ProfileFragment.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(photoRecyclerViewAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        NavController navController = NavHostFragment.findNavController(ProfileFragment.this);
        navController.navigate(R.id.action_profileFragment_to_largePhotoFragment);
    }
}
