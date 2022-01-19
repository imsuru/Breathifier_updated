package com.example.breathifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class YogaIntro extends AppCompatActivity {
    Button bt1,bt2,bt3,bt4,bt5;

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(YogaIntro.this,Introduction.class);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_intro);
        bt1=(Button)findViewById(R.id.yoga1);
        bt2=(Button)findViewById(R.id.yoga2);
        bt3=(Button)findViewById(R.id.yoga3);
        bt4=(Button)findViewById(R.id.yoga4);
        bt5=(Button)findViewById(R.id.yoga5);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(YogaIntro.this,yoga1.class);
                startActivity(intent);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(YogaIntro.this,yoga2.class);
                startActivity(intent);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(YogaIntro.this,yoga3.class);
                startActivity(intent);
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(YogaIntro.this,yoga4.class);
                startActivity(intent);
            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(YogaIntro.this,yoga5.class);
                startActivity(intent);
            }
        });

    }
}