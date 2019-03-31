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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.experience.customvkclient.R;
import com.experience.customvkclient.view.adapter.FriendsRecyclerViewAdapter;
import com.experience.customvkclient.view.adapter.OnItemClickListener;
import com.experience.customvkclient.model.Profile;
import com.experience.customvkclient.viewmodel.FriendsViewModel;
import com.experience.customvkclient.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class FriendsListFragment extends Fragment implements OnItemClickListener {

    @BindView(R.id.friends_recycler_view)
    RecyclerView recyclerView;

    private final static String LOG_TAG = "FriendsListFragment";
    private FriendsViewModel friendsVM;
    private List<Profile> friends = new ArrayList<>();
    private FriendsRecyclerViewAdapter friendsRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        recyclerInit(friends);
        friendsVM = ViewModelProviders.of(this).get(FriendsViewModel.class);
        friendsVM.getFriends().observe(this, new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                friends.addAll(profiles);
                friendsRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void recyclerInit(List<Profile> friends){
        friendsRecyclerViewAdapter = new FriendsRecyclerViewAdapter(friends);
        friendsRecyclerViewAdapter.setOnItemClickListener(FriendsListFragment.this);
        recyclerView.setAdapter(friendsRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onItemClick(View view, int position) {
        friendsVM.setId(friends.get(position).getId());
        NavController navController = NavHostFragment.findNavController(FriendsListFragment.this);
        navController.navigate(R.id.action_friendsListFragment_to_profileFragment);
    }
}
