package com.experience.customvkclient.view.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.experience.customvkclient.R;
import com.experience.customvkclient.model.Profile;
import com.experience.customvkclient.model.repository.net.OnResponseListener;
import com.experience.customvkclient.model.repository.net.PhotoRequest;
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
            /*icon = itemView.findViewById(R.id.item_image_view);
            name = itemView.findViewById(R.id.item_text_view_name);
            onlineStatus = itemView.findViewById(R.id.item_text_view_status);*/
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, getAdapterPosition());
        }

        private void fillViewHolder(Profile profile){
            name.setText(profile.getUserName());
            onlineStatus.setText(profile.getOnline());

            //TODO simplyfy to getBitmap
            new PhotoRequest().makeRequest(profile.getMainPhotoUrl(), new OnResponseListener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    icon.setImageBitmap(response);
                }
            });
        }
    }

}
