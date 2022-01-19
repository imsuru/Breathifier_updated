package com.example.breathifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class ActualMeditation extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    Button btMeditate;
    SeekBar seekBar;

    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.alarmsetter,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.alarm){
            Intent intent =new Intent(ActualMeditation.this,AlarmNotificationSetter.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_meditation);
        getSupportActionBar().hide();
        mediaPlayer=MediaPlayer.create(this,R.raw.piano);
        btMeditate=(Button)findViewById(R.id.button2);
        seekBar=(SeekBar)findViewById(R.id.seekBar);


        btMeditate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btMeditate.setText("Play");
                }
                else{
                    mediaPlayer.start();
                    btMeditate.setText("Pause");
                }
            }
        });
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        int h = Math.round(progress);
        //In the above line we are converting the float value into int because
// media player required int value and seekbar library gives progress in float
        if (mediaPlayer != null && fromUser) {
            mediaPlayer.seekTo(h);
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
}