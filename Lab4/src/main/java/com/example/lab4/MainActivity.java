package com.example.lab4;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class MainActivity extends AppCompatActivity {

    private ExoPlayer player;
    private PlayerView playerView;
    private EditText etUrl;

    private ActivityResultLauncher<String> pickAudioLauncher;
    private ActivityResultLauncher<String> pickVideoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.playerView);
        etUrl = findViewById(R.id.etUrl);

        Button btnPlayUrl = findViewById(R.id.btnPlayUrl);
        Button btnPickAudio = findViewById(R.id.btnPickAudio);
        Button btnPickVideo = findViewById(R.id.btnPickVideo);

        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnPause = findViewById(R.id.btnPause);
        Button btnStop = findViewById(R.id.btnStop);

        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        pickAudioLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) playMedia(uri);
                }
        );

        pickVideoLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) playMedia(uri);
                }
        );

        btnPickAudio.setOnClickListener(v -> pickAudioLauncher.launch("audio/*"));
        btnPickVideo.setOnClickListener(v -> pickVideoLauncher.launch("video/*"));

        btnPlayUrl.setOnClickListener(v -> {
            String url = etUrl.getText().toString().trim();
            if (!url.isEmpty()) {
                playMedia(Uri.parse(url));
            } else {
                Toast.makeText(this, "Введіть URL", Toast.LENGTH_SHORT).show();
            }
        });

        btnPlay.setOnClickListener(v -> player.play());
        btnPause.setOnClickListener(v -> player.pause());
        btnStop.setOnClickListener(v -> {
            player.stop();
            player.clearMediaItems();
        });
    }

    private void playMedia(Uri uri) {
        MediaItem mediaItem = MediaItem.fromUri(uri);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}