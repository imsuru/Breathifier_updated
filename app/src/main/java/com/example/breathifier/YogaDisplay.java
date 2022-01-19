package com.example.breathifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class YogaDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_display);
        getSupportActionBar().hide();
        Thread thread=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2000);
                    Intent intent=new Intent(YogaDisplay.this,YogaIntro.class);
                    startActivity(intent);
                }
                catch(Exception e){

                }
                super.run();
            }
        };
        thread.start();
    }
}