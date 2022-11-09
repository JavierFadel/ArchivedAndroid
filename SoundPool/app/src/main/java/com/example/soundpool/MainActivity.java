package com.example.soundpool;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SoundPool soundPool;
    private Button buttonOne, buttonTwo, buttonThree, buttonFour;
    private int soundOne, soundTwo, soundThree, soundFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set audio attributes.
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .build();

        // Use SoundPool.Builder() for newer version.
        soundPool = new SoundPool.Builder()
                .setMaxStreams(4) // Sets the maximum of number of simultaneous streams that can be played simultaneously.
                .setAudioAttributes(audioAttributes)
                .build();

        // Loading sound media into memory.
        soundOne = soundPool.load(MainActivity.this, R.raw.bruh, 1);
        soundTwo = soundPool.load(MainActivity.this, R.raw.minecraft, 1);
        soundThree = soundPool.load(MainActivity.this, R.raw.oof, 1);
        soundFour = soundPool.load(MainActivity.this, R.raw.yaa, 1);

        buttonOne = findViewById(R.id.button_one);
        buttonTwo = findViewById(R.id.button_two);
        buttonThree = findViewById(R.id.button_three);
        buttonFour = findViewById(R.id.button_four);

        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_one:
                soundPool.play(soundOne, 1, 1, 0, 0, 1);
                break;
            case R.id.button_two:
                soundPool.play(soundTwo, 1, 1, 0, 0, 1);
                break;
            case R.id.button_three:
                soundPool.play(soundThree, 1, 1, 0, 0, 1);
                break;
            case R.id.button_four:
                soundPool.play(soundFour, 1, 1, 0, 0, 1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
