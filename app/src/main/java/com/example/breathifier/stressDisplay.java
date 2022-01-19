package com.example.breathifier;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class stressDisplay extends AppCompatActivity {
    TextView tvSco,tvRes;


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(stressDisplay.this,Introduction.class);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_display);

        tvSco=(TextView)findViewById(R.id.tvOp);
        tvRes=(TextView)findViewById(R.id.tvdis);
        Bundle extras=getIntent().getExtras();
        int value=extras.getInt("score");
        String ans = String.valueOf(value);
        tvSco.setText("Your Score is = "+ans);
        if (value <= 13) {
            tvRes.setText(" You are suffering from low stress");

        } else if (value >= 14 && value <= 26) {
            tvRes.setText(" You are suffering from moderate stress");

        } else {
            tvRes.setText( " You are suffering from High Stress");
        }
        
    }
    }
