package com.experience.customvkclient.view.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.experience.customvkclient.R;
import com.experience.customvkclient.model.repository.net.OnResponseListener;
import com.experience.customvkclient.model.repository.net.PhotoRequest;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoRecyclerViewAdapter extends RecyclerView.Adapter<PhotoRecyclerViewAdapter.PhotoViewHolder> {

    private List<String> photos;
    private OnItemClickListener onItemClickListener;


    public PhotoRecyclerViewAdapter(List<String> photos) {
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
        new PhotoRequest().makeRequest(photos.get(position), new OnResponseListener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.imageView.setImageBitmap(response);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_photo_image_view)
        ImageView imageView;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
