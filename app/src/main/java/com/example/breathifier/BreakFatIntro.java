package com.example.breathifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BreakFatIntro extends AppCompatActivity {
    Button bt3,bt2,bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_fat_intro);
        bt3=(Button)findViewById(R.id.dietbt3);
        bt1=(Button)findViewById(R.id.dietbt1);
        bt2=(Button)findViewById(R.id.dietbt2);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BreakFatIntro.this,ImportanceOfDiet.class);
                startActivity(intent);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BreakFatIntro.this,ToEat.class);
                startActivity(intent);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BreakFatIntro.this,ToNotEat.class);
                startActivity(intent);
            }
        });

    }
}