package com.example.calvin.workout;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.MediaController.MediaPlayerControl;

import com.example.calvin.workout.MusicService.MusicBinder;
import com.example.calvin.workout.models.SongModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Calvin on 8/3/2017.
 */

public class MusicActivity extends AppCompatActivity implements MediaPlayerControl {

    private ArrayList<SongModel> songList;
    private ListView songView;
    private MusicController controller;
    private MusicService musService;
    private Intent intent;
    private boolean musicBound = false;
    private boolean paused= false;
    private boolean playbackPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                return;
            }
        }

        songView = (ListView)findViewById(R.id.song_list);

        songList = new ArrayList<SongModel>();

        getSongList();

        Collections.sort(songList, new Comparator<SongModel>(){
            public int compare(SongModel a, SongModel b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        SongAdapter adapter = new SongAdapter(this, songList);
        songView.setAdapter(adapter);

        setController();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(intent == null){
            intent = new Intent(this, MusicService.class);
            bindService(intent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(intent);
        }
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder = (MusicBinder) service;
            //get service
            musService = binder.getService();
            //pass list
            musService.setSongList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public void getSongList() {
        ContentResolver musicResolver = getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(uri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songList.add(new SongModel(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());
        }
    }


    public void songPicked(View view){
        musService.setSong(Integer.parseInt(view.getTag().toString()));
        musService.playSong();

        if(playbackPaused) {
            setController();
            playbackPaused = false;
        }

        controller.show(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.shuffle_music) {
            musService.setShuffle();
        }
        else if (item.getItemId() == R.id.stop_music) {
            stopService(intent);
            musService = null;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        stopService(intent);
        musService = null;
        super.onDestroy();
    }

    private void setController(){
        //set the controller up
        controller = new MusicController(this);

        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });

        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.song_list));
        controller.setEnabled(true);
    }

    @Override
    public void start() {
        musService.go();
    }

    @Override
    public void pause() {
        playbackPaused=true;
        musService.pausePlayer();
    }

    @Override
    public int getDuration() {
        if (musService != null && musicBound && musService.isPlaying()) {
            return musService.getDuration();
        }
        else {
            return 0;
        }
    }

    @Override
    public int getCurrentPosition() {
        if (musService != null && musicBound && musService.isPlaying()) {
            return musService.getPosition();
        }
        else {
            return 0;
        }
    }

    @Override
    public void seekTo(int pos) {
        musService.seek(pos);
    }

    @Override
    public boolean isPlaying() {
        if (musService != null && musicBound) {
            return musService.isPlaying();
        }
        else {
            return false;
        }
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    //play next
    private void playNext(){
        musService.playNext();

        if(playbackPaused){
            setController();
            playbackPaused = false;
        }

        controller.show(0);
    }

    //play previous
    private void playPrev(){
        musService.playPrev();

        if(playbackPaused) {
            setController();
            playbackPaused = false;
        }

        controller.show(0);
    }

    @Override
    protected void onPause(){
        super.onPause();
        paused = true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(paused){
            setController();
            paused = false;
        }
    }

    @Override
    protected void onStop() {
        controller.hide();
        super.onStop();
    }
}
