package com.example.breast_app;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView myPlayerView;
    public static final String appKey = "\n" + "AIzaSyCzSx_q-vJ2QymYVo53fIUpNtUFIFHmgFY\n";
    public String[] videoLinks= {"k_crw1oowyc", "qrLVoKbYe7s", "W8yw-Pzp5do", "9nqoNscszAQ"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);

        myPlayerView = findViewById(R.id.video_view_youtube);
        myPlayerView.initialize(appKey,this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(videoLinks[getIntent().getIntExtra("position",0)]);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this,"this video is not available", Toast.LENGTH_LONG).show();
    }

}
