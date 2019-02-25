package com.experience.customvkclient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.experience.customvkclient.R;
import com.experience.customvkclient.model.Photo;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoRecyclerViewAdapter extends RecyclerView.Adapter<PhotoRecyclerViewAdapter.PhotoViewHolder> {

    private List<Photo> photos;
    private OnItemClickListener onItemClickListener;

    public PhotoRecyclerViewAdapter(List<Photo> photos){
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_photos, parent, false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(photos.get(position).getUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;

        PhotoViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.item_photo_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener != null) onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
