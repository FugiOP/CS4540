package com.example.fugibeast.tabdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by FugiBeast on 7/30/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ItemHolder>{
    ArrayList<Video> videos;
    ItemClickListener listener;

    public VideoAdapter(ArrayList<Video> videos, ItemClickListener listener){
        this.videos = videos;
        this.listener = listener;
    }

    public interface ItemClickListener {
        //Added cursor to parameters
        void onItemClick(int clickedItemIndex);
    }
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.playlist_item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ItemHolder(View view){
            super(view);

        }
        public void bind(int pos){
            Video video = videos.get(pos);
        }
        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }
    }
}
