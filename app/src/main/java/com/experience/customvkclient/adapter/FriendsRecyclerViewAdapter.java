package com.experience.customvkclient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.experience.customvkclient.R;
import com.experience.customvkclient.model.Profile;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        Profile profile = friends.get(position);
        Glide.with(holder.itemView).load(profile.getMainPhoto().getUrl()).into(holder.icon);
        holder.name.setText(profile.getUserName());
        holder.onlineStatus.setText(profile.getOnline());
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class FrViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView icon;
        TextView name;
        TextView onlineStatus;

        FrViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_image_view);
            name = itemView.findViewById(R.id.item_text_view_name);
            onlineStatus = itemView.findViewById(R.id.item_text_view_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

}
