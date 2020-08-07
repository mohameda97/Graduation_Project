package com.example.breast_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class self_mid extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_mid);

        videoView = findViewById(R.id.video_view);
        MediaController controller = new MediaController(this);
        controller.setAnchorView(videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.examination;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.setMediaController(controller);
        videoView.start();
    }

    public void back(View view) {
        onBackPressed();
    }

    public void goAnswer(View view) {
        startActivity(new Intent(getApplicationContext(), self_2.class));
    }
}
