package com.experience.customvkclient.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.experience.customvkclient.R;
import com.experience.customvkclient.model.Profile;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendsRecyclerViewAdapter extends RecyclerView.Adapter<FriendsRecyclerViewAdapter.FrViewHolder> {

    private List<Profile> friends;
    private OnItemClickListener onItemClickListener;

    public FriendsRecyclerViewAdapter(List<Profile> friends) {
        this.friends = friends;
    }

    @NonNull
    @Override
    public FrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_friends, parent, false);
        return new FrViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FrViewHolder holder, int position) {
        holder.fillViewHolder(friends.get(position));
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class FrViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_image_view)
        ImageView icon;
        @BindView(R.id.item_text_view_name)
        TextView name;
        @BindView(R.id.item_text_view_status)
        TextView onlineStatus;

        FrViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, getAdapterPosition());
        }

        private void fillViewHolder(Profile profile) {
            name.setText(String.format("%s %s", profile.getFirstName(), profile.getLastName()));
            onlineStatus.setText(profile.getOnline().equals("1") ? "Online" : "Offline");
            Glide.with(icon).load(profile.getPhotoMax()).apply(RequestOptions.circleCropTransform()).into(icon);
        }
    }

}
