package com.moutamid.demovideoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.khizar1556.mkvideoplayer.MKPlayer;
import com.khizar1556.mkvideoplayer.MKPlayerActivity;
import com.moutamid.demovideoplayer.databinding.ActivityVideoPlayerBinding;

public class VideoPlayerActivity extends AppCompatActivity {

    private ActivityVideoPlayerBinding b;
    MKPlayer mkplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        b = ActivityVideoPlayerBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        mkplayer = new MKPlayer(this);
        mkplayer.play(getIntent().getStringExtra("path"));

        mkplayer.setPlayerCallbacks(new MKPlayer.playerCallbacks() {
            @Override
            public void onNextClick() {
                mkplayer.forward(0.1f);
            }

            @Override
            public void onPreviousClick() {
                mkplayer.forward(-0.1f);
            }
        });

        mkplayer.setShowNavIcon(false);

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mkplayer.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        mkplayer.onBackPressed();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        mkplayer.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mkplayer.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mkplayer.onDestroy();
        super.onDestroy();
    }
}