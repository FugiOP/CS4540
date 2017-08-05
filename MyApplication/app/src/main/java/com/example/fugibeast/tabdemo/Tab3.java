package com.example.fugibeast.tabdemo;

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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.MediaController;

import com.example.fugibeast.tabdemo.models.SongModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by FugiBeast on 7/28/2017.
 */

public class Tab3 extends Fragment implements MediaController.MediaPlayerControl{

    private ArrayList<SongModel> songList;
    private ListView songView;
    private MusicController controller;
    private MusicService musService;
    private Intent intent;
    private boolean musicBound = false;
    private boolean paused= false;
    private boolean playbackPaused = false;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        view = lf.inflate(R.layout.fragment_3,container,false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                return null;
            }
        }

        songView = (ListView)view.findViewById(R.id.song_list);

        songList = new ArrayList<>();

        getSongList();

        Collections.sort(songList, new Comparator<SongModel>(){
            public int compare(SongModel a, SongModel b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        SongAdapter adapter = new SongAdapter(getContext(), songList);
        songView.setAdapter(adapter);

        setController();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(intent == null){
            intent = new Intent(getActivity(), MusicService.class);
            musService.bindService(intent, musicConnection, Context.BIND_AUTO_CREATE);
            musService.startService(intent);
        }
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
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
        ContentResolver musicResolver = getContext().getContentResolver();
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
            musService.stopService(intent);
            musService = null;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        musService.stopService(intent);
        musService = null;
        super.onDestroy();
    }

    private void setController(){
        //set the controller up
        controller = new MusicController(getContext());

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
        controller.setAnchorView(view.findViewById(R.id.song_list));
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
    public void onPause(){
        super.onPause();
        paused = true;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(paused){
            setController();
            paused = false;
        }
    }

    @Override
    public void onStop() {
        controller.hide();
        super.onStop();
    }
}
