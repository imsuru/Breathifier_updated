package com.example.breathifier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PanicAttack1 extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic_attack1);
        getSupportActionBar().hide();
        tv=(TextView)findViewById(R.id.tvBreath);
        tv.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setVisibility(View.INVISIBLE);
            }
        },10000);
    }
}