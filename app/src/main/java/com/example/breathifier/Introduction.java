package com.example.breathifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Introduction extends AppCompatActivity {
    Button bt1,bt3,bt4,bt5,bt2;

    FirebaseAuth auth;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0:
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Introduction.this, PanicActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Dont Have Required Permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        bt1 = (Button) findViewById(R.id.inbt1);
        bt2= (Button) findViewById(R.id.inbt2);
        bt3 = (Button) findViewById(R.id.inbt3);
        bt4 = (Button) findViewById(R.id.inbt4);
        bt5=(Button)findViewById(R.id.btPanicIntro);

       // logout = (Button) findViewById(R.id.btLogout);
        auth = FirebaseAuth.getInstance();



        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Introduction.this, StressCalculator.class);
                startActivity(intent);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Introduction.this,YogaDisplay.class);
                startActivity(intent);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Introduction.this, BreakFatIntro.class);
                startActivity(intent);
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Introduction.this, MeditationIntorduction.class);
                startActivity(intent);
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck= ContextCompat.checkSelfPermission(Introduction.this, Manifest.permission.SEND_SMS);
                if(permissionCheck== PackageManager.PERMISSION_GRANTED){
                    Intent intent=new Intent(Introduction.this,PanicActivity.class);
                    startActivity(intent);
                }
                else{
                    ActivityCompat.requestPermissions(Introduction.this,new String[]{Manifest.permission.SEND_SMS},0);
                }

            }
        });

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences sharedPreferences=getSharedPreferences("loginData",MODE_PRIVATE);
//                sharedPreferences.edit().clear().commit();
//               auth.signOut();
//
//               Intent intent=new Intent(Introduction.this,RegistrationIntroduction.class);
//               startActivity(intent);
//               finish();
//
//            }
//        });

    }
    //menu overflow

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.logout){
            SharedPreferences sharedPreferences=getSharedPreferences("loginData",MODE_PRIVATE);
            sharedPreferences.edit().clear().commit();
            auth.signOut();

            Intent intent=new Intent(Introduction.this,RegistrationIntroduction.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.aboutUs){
            Toast.makeText(this, "You have selected about us", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    private void checkUserStatus() {
        SharedPreferences sharedPreferences=getSharedPreferences("loginData",MODE_PRIVATE);
        Boolean counter=sharedPreferences.getBoolean("LoginCounter",Boolean.valueOf(String.valueOf(MODE_PRIVATE)));
        String ema=sharedPreferences.getString("UserEmail",String.valueOf(MODE_PRIVATE));
        if(counter){
            //login as user
            Toast.makeText(this, "Logged In As User", Toast.LENGTH_SHORT).show();
        }
        else{
            //not login
            Intent intent=new Intent(Introduction.this,RegistrationIntroduction.class);
            startActivity(intent);
            finish();
        }
    }
}