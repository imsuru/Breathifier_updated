package com.example.breathifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PanicActivity extends AppCompatActivity {
    Button btStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic);
        btStart=(Button)findViewById(R.id.Start);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("PanicNumber",MODE_PRIVATE);
                String no=sharedPreferences.getString("num",String.valueOf(MODE_PRIVATE));
                String message="I am suffering from panic attack please contact me immediately";
                if (TextUtils.isEmpty(message) || TextUtils.isEmpty(no)) {
                    Toast.makeText(PanicActivity.this, "Please Enter The Necessary Fields", Toast.LENGTH_SHORT).show();
                } else {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(no, null, message, null, null);

                    Toast.makeText(PanicActivity.this, "Message Sent SuccessFully", Toast.LENGTH_SHORT).show();
                }

                Intent intent=new Intent(PanicActivity.this,PanicAttack1.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.panic,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.contactDetails){
            Intent intent =new Intent(PanicActivity.this,PanicDetails.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}