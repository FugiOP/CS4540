package com.example.fugibeast.tabdemo;



import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FugiBeast on 7/28/2017.
 */

public class Tab3 extends Fragment implements YouTubeThumbnailView.OnInitializedListener,YouTubeThumbnailLoader.OnThumbnailLoadedListener,YouTubePlayer.OnInitializedListener{
    private static final String API_KEY = "API_KEY_HERE";
    private static String VIDEO_ID = "EGy39OMyHzw";
    private static String PLAYLIST_ID = "PL_mRAdDigwpP3w_xRZ9SIP23xray0oTpo";

    public View view;

    YouTubePlayerSupportFragment playerFragment;
    YouTubePlayer Player;
    YouTubeThumbnailView thumbnailView;
    YouTubeThumbnailLoader thumbnailLoader;
    RecyclerView VideoList;
    RecyclerView.Adapter adapter;
    List<Drawable> thumbnailViews;
    List<String> VideoId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        LayoutInflater lf = getActivity().getLayoutInflater();
        view = lf.inflate(R.layout.fragment_3,container,false);
        Context context = getContext();
//
//        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.replace(R.id.videoFrame, youTubePlayerFragment).commit();
//
//        youTubePlayerFragment.initialize(API_KEY, new OnInitializedListener() {
//
//            @Override
//            public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
//                if (!wasRestored) {
//                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
//                    player.loadVideo(VIDEO_ID);
//                    player.play();
//                }
//            }
//
//            @Override
//            public void onInitializationFailure(Provider provider, YouTubeInitializationResult error) {
//                // YouTube error
//                String errorMessage = error.toString();
//                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
//                Log.d("errorMessage:", errorMessage);
//            }
//        });
//
////        rv = (RecyclerView) view.findViewById(R.id.VideoList);
////        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        thumbnailViews = new ArrayList<>();
        VideoList = (RecyclerView) view.findViewById(R.id.VideoList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
        VideoList.setLayoutManager(layoutManager);
        adapter=new VideoListAdapter();
        VideoList.setAdapter(adapter);
        VideoId = new ArrayList<>();
        thumbnailView = new YouTubeThumbnailView(context);
        thumbnailView.initialize(API_KEY, this);
        playerFragment = (YouTubePlayerSupportFragment) getFragmentManager().findFragmentById(R.id.VideoFragment);
        playerFragment.initialize(API_KEY, this);


        return view;
    }
    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
        thumbnailLoader = youTubeThumbnailLoader;
        youTubeThumbnailLoader.setOnThumbnailLoadedListener(Tab3.this);
        thumbnailLoader.setPlaylist(PLAYLIST_ID);
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

    }


    public void add() {
        adapter.notifyDataSetChanged();
        if (thumbnailLoader.hasNext())
            thumbnailLoader.next();
    }

    @Override
    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
        thumbnailViews.add(youTubeThumbnailView.getDrawable());
        VideoId.add(s);
        add();
    }

    @Override
    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Player=youTubePlayer;
        Player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {
                VideoList.setVisibility(b?View.GONE:View.VISIBLE);
            }
        });
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyView> {

        public class MyView extends RecyclerView.ViewHolder {

            ImageView imageView;

            public MyView(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.thumbnailView);
            }

        }

        @Override
        public VideoListAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
            return new MyView(itemView);
        }

        @Override
        public void onBindViewHolder(VideoListAdapter.MyView holder, final int position) {
            holder.imageView.setImageDrawable(thumbnailViews.get(position));
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Player.cueVideo(VideoId.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return thumbnailViews.size();
        }
    }
}
