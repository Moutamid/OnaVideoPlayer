package com.moutamid.demovideoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.moutamid.demovideoplayer.databinding.ActivityVideoPlayerBinding;

public class VideoPlayerActivity extends AppCompatActivity {

    private ActivityVideoPlayerBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        b = ActivityVideoPlayerBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        /*Uri uri = Uri.parse(getIntent().getStringExtra("path"));
        b.videoView.setVideoURI(uri);*/

        b.videoView.setMediaController(new MediaController(this));
        b.videoView.setVideoPath(getIntent().getStringExtra("path"));
        b.videoView.start();

    }
}