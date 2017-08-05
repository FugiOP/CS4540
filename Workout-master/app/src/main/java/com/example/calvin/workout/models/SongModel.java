package com.example.calvin.workout.models;

/**
 * Created by Calvin on 8/4/2017.
 */

public class SongModel {
    private long id;
    private String title;
    private String artist;

    public SongModel(long id, String songTitle, String songArtist) {
        this.id = id;
        this.title = songTitle;
        this.artist = songArtist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
