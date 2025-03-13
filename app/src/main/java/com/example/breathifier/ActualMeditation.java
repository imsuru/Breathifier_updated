package com.example.breathifier;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;

public class ActualMeditation extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    Button activeButton;
    SeekBar activeSeekBar;
    Handler handler = new Handler();
    Runnable progressUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_meditation);

        Button playOm = findViewById(R.id.playOm);
        Button playTrack1 = findViewById(R.id.playTrack1);
        Button playTrack2 = findViewById(R.id.playTrack2);

        SeekBar seekBarOm = findViewById(R.id.progressOm);
        SeekBar seekBarTrack1 = findViewById(R.id.progressTrack1);
        SeekBar seekBarTrack2 = findViewById(R.id.progressTrack2);
        // Set listeners for buttons
        playOm.setOnClickListener(v -> toggleTrack(playOm, "om", R.raw.om, seekBarOm));
        playTrack1.setOnClickListener(v -> toggleTrack(playTrack1, "Track 1", R.raw.track1, seekBarTrack1));
        playTrack2.setOnClickListener(v -> toggleTrack(playTrack2, "Track 2", R.raw.track2, seekBarTrack2));

        // Enable manual adjustments for each SeekBar
        setSeekBarListener(seekBarOm);
        setSeekBarListener(seekBarTrack1);
        setSeekBarListener(seekBarTrack2);
    }

    private void toggleTrack(Button button, String trackName, int trackId, SeekBar targetSeekBar) {
        // If the current track is selected
        if (mediaPlayer != null && button.equals(activeButton)) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                activeButton.setText("Resume " + trackName);
            } else {
                mediaPlayer.start();
                activeButton.setText("Pause " + trackName);
            }
            return;
        }

        // If a new track is selected, stop and reset the current track
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            resetSeekBarAndButton(activeSeekBar, activeButton); // Reset the previous track's SeekBar and button text
        }

        // Reset all SeekBars and buttons
        resetAllSeekBarsAndButtons();

        // Play the selected track
        mediaPlayer = MediaPlayer.create(this, trackId);
        activeButton = button;
        activeSeekBar = targetSeekBar;

        mediaPlayer.start();
        button.setText("Pause " + trackName);

        // Update the SeekBar progress
        activeSeekBar.setMax(mediaPlayer.getDuration());
        progressUpdater = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    activeSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 100);
                }
            }
        };
        handler.post(progressUpdater);

        // Reset button text when the track finishes
        mediaPlayer.setOnCompletionListener(mp -> {
            activeButton.setText("Play " + trackName);
            resetSeekBar(activeSeekBar);
        });
    }

    private void setSeekBarListener(SeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress); // Change track position based on user input
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    mediaPlayer.pause(); // Pause playback while dragging
                    if (activeButton != null) {
                        String trackName = activeButton.getText().toString().replace("Pause ", "").replace("Resume ", "").replace("Play ", "");
                        activeButton.setText("Resume " + trackName);
                    }
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    mediaPlayer.start(); // Resume playback after dragging
                    if (activeButton != null) {
                        String trackName = activeButton.getText().toString().replace("Pause ", "").replace("Resume ", "").replace("Play ", "");
                        activeButton.setText("Pause " + trackName);
                    }
                }
            }
        });
    }

    private void resetSeekBarAndButton(SeekBar seekBar, Button button) {
        if (seekBar != null) {
            seekBar.setProgress(0);
        }
        if (button != null) {
            String trackName = button.getText().toString().replace("Pause ", "").replace("Resume ", "").replace("Play ", "");
            button.setText("Play " + trackName);
        }
    }

    private void resetAllSeekBarsAndButtons() {
        resetSeekBarAndButton(findViewById(R.id.progressOm), findViewById(R.id.playOm));
        resetSeekBarAndButton(findViewById(R.id.progressTrack1), findViewById(R.id.playTrack1));
        resetSeekBarAndButton(findViewById(R.id.progressTrack2), findViewById(R.id.playTrack2));
    }

    private void resetSeekBar(SeekBar seekBar) {
        if (seekBar != null) {
            seekBar.setProgress(0);
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        handler.removeCallbacks(progressUpdater);
        super.onDestroy();
    }
}
