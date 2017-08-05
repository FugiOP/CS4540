package com.example.fugibeast.tabdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.fugibeast.tabdemo.models.SongModel;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Calvin on 8/4/2017.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer player;
    private ArrayList<SongModel> listOfSongs;
    private int position;
    private boolean shuffle = false;
    private Random random;
    private String songTitle="";
    private static final int NOTIFY_ID = 1;

    private final IBinder musicBind = new MusicBinder();

    public void onCreate() {
        super.onCreate();

        random = new Random();

        position = 0;

        player = new MediaPlayer();

        initMusicPlayer();
    }

    public void setShuffle(){
        if(shuffle) {
            shuffle = false;
        }
        else {
            shuffle = true;
        }
    }

    public void initMusicPlayer() {
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public void setSongList(ArrayList<SongModel> songs){
        listOfSongs = songs;
    }

    public void setSong(int index){
        position = index;
    }

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    public void playSong(){
        player.reset();

        //get song
        SongModel playSong = listOfSongs.get(position);
        //get id
        long currentSong = playSong.getId();
        //set uri
        Uri trackUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currentSong);

        songTitle = playSong.getTitle();

        try {
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }

        player.prepareAsync();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(player.getCurrentPosition() > 0) {
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //start playback
        mp.start();

        Intent notIntent = new Intent(this, MainActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0, notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.play)
                .setTicker(songTitle)
                .setOngoing(true)
                .setContentTitle("Playing")
                .setContentText(songTitle);
        Notification not = builder.build();

        startForeground(NOTIFY_ID, not);
    }

    public int getPosition(){
        return player.getCurrentPosition();
    }

    public int getDuration(){
        return player.getDuration();
    }

    public boolean isPlaying(){
        return player.isPlaying();
    }

    public void pausePlayer(){
        player.pause();
    }

    public void seek(int posn){
        player.seekTo(posn);
    }

    public void go(){
        player.start();
    }

    public void playPrev(){
        position--;
        if(position < 0) {
            position = listOfSongs.size()- 1;
        }

        playSong();
    }

    public void playNext() {
        if(shuffle){
            int newSong = position;
            while(newSong == position){
                newSong = random.nextInt(listOfSongs.size());
            }
            position = newSong;
        }
        else {
            position++;
            if(position > 0) {
                listOfSongs.size();
            }
            position = 0;
        }
        playSong();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }
}
