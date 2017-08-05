package com.example.calvin.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.calvin.workout.models.SongModel;

import java.util.ArrayList;

/**
 * Created by Calvin on 8/3/2017.
 */

public class SongAdapter extends BaseAdapter {

    private ArrayList<SongModel> songs;
    private LayoutInflater inf;

    public SongAdapter(Context context, ArrayList<SongModel> songs) {
        this.songs = songs;
        this.inf=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View child, ViewGroup parent) {
        //map to song layout
        LinearLayout songLay = (LinearLayout)inf.inflate(R.layout.song_item, parent, false);
        //get title and artist views
        TextView songView = (TextView)songLay.findViewById(R.id.song_title);
        TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);
        //get song using position
        SongModel currentSong = songs.get(position);
        //get title and artist strings
        songView.setText(currentSong.getTitle());
        artistView.setText(currentSong.getArtist());
        //set position as tag
        songLay.setTag(position);
        return songLay;
    }

}