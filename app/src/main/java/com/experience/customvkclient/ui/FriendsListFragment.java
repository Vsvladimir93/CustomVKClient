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
import android.widget.TextView;

import com.experience.customvkclient.R;
import com.experience.customvkclient.adapter.FriendsRecyclerViewAdapter;
import com.experience.customvkclient.adapter.OnItemClickListener;
import com.experience.customvkclient.model.Profile;
import com.experience.customvkclient.util.MyVkApiRequest;

import java.util.ArrayList;
import java.util.List;

public class FriendsListFragment extends Fragment {

    @BindView(R.id.friends_recycler_view)
    RecyclerView recyclerView;

    private final static String LOG_TAG = "FriendsListFragment";
    private List<Profile> friends;
    private NavController navController;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

        MyVkApiRequest.requestFriends(response -> {
            friends = new ArrayList<>(response);

            recyclerView.setLayoutManager(linearLayoutManager);

            FriendsRecyclerViewAdapter friendsRecyclerViewAdapter = new FriendsRecyclerViewAdapter(friends);
            friendsRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("FriendProfile", friends.get(position));
                    navController = NavHostFragment.findNavController(FriendsListFragment.this);
                    navController.navigate(R.id.action_friendsListFragment_to_profileFragment, bundle);
                }
            });
            recyclerView.setAdapter(friendsRecyclerViewAdapter);
        });


    }
}
