package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button playButton;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.play_button);
        seekBar = findViewById(R.id.seek_bar_id);
        mediaPlayer = new MediaPlayer();

        // Handle IOStream exception.
        try {
            mediaPlayer.setDataSource("https://buildappswithpaulo.com/music/watch_me.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Invokes when media finish playing.
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int duration = mp.getDuration();
                Toast.makeText(MainActivity.this, duration / 1000 + " seconds",
                        Toast.LENGTH_LONG).show();
            }
        });

        // setOnPreparedListener parameter thrown inside this variable.
        MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
            // Preparing file.
            @Override
            public void onPrepared(final MediaPlayer mp) {
                // Getting duration from Media Player to XML
                seekBar.setMax(mp.getDuration());
                // Setting play button.
                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mp.isPlaying()) {
                            mp.pause();
                            playButton.setText(R.string.play_text);
                        } else {
                            mp.start();
                            playButton.setText(R.string.pause_text);
                        }
                    }
                });
            }
        };
        // Invokes this method to prepared media file.
        mediaPlayer.setOnPreparedListener(preparedListener);
        // Prepares the player for playback, asynchronously (at the same time).
        mediaPlayer.prepareAsync();

        // Playing from resource package.
//        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.titanium);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

//    private void isPaused() {
//        if (mediaPlayer != null) {
//            mediaPlayer.pause();
//            playButton.setText(R.string.play_text);
//        }
//    }
//
//    private void isPlay() {
//        if (mediaPlayer != null) {
//            mediaPlayer.start();
//            playButton.setText(R.string.pause_text);
//        }
//    }

    // Clean program.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
        mediaPlayer.release();
    }
}
