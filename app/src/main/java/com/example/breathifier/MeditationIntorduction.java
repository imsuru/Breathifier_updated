package com.example.breathifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MeditationIntorduction extends AppCompatActivity {
    ImageView im;
    private Timer time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_intorduction);
        im=(ImageView)findViewById(R.id.imageMed);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
               im.setVisibility(View.VISIBLE);
            }
        }, 1000);
        time=new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(MeditationIntorduction.this,ActualMeditation.class);
                startActivity(intent);
                finish();
            }
        },2500);
    }
}