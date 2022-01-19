package com.example.breathifier;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AlarmNotificationSetter extends AppCompatActivity {
   private Button bt1,bt2,bt3;
   TextView tvDis;
   private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private MaterialTimePicker materialTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_notification_setter);
        bt1=(Button)findViewById(R.id.timePicker);
        bt2=(Button)findViewById(R.id.setAlarm);
        bt3=(Button)findViewById(R.id.cancelAlarm);
        tvDis=(TextView)findViewById(R.id.textView6);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker();
            }

        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();
            }

        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }

        });

    }
    private void timePicker(){
         materialTimePicker=new MaterialTimePicker.Builder()
                 .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Select Time of Meditation")
                //.setHour(12)
               // .setMinute(0)
                .build();
        materialTimePicker.show(getSupportFragmentManager(),"ChanelId");

        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(materialTimePicker.getHour()>12){
//                    tvDis.setText(String.format("%02d",(materialTimePicker.getHour()-12))+" : "+String.format("%02d",materialTimePicker.getMinute())+" Pm");
//
//                }
//                else if(materialTimePicker.getHour()==12)
//                else {
//                    tvDis.setText(materialTimePicker.getHour()+" : "+ materialTimePicker.getMinute()+" Am");
//                }
                tvDis.setText(materialTimePicker.getHour()+" : "+ materialTimePicker.getMinute());

                calendar= Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,materialTimePicker.getHour());
                calendar.set(Calendar.MINUTE,materialTimePicker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
            }
        });



    }
    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="Wakeeeee up guysssss";
            String description="Chanel for alarm manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel=new NotificationChannel("ChanelId",name,importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void setAlarm() {
        alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent =new Intent(this ,AlarmReceiver.class);
        pendingIntent= PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),alarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_SHORT).show();
    }
    private void cancelAlarm() {
        Intent intent =new Intent(this ,AlarmReceiver.class);
        pendingIntent= PendingIntent.getBroadcast(this,0,intent,0);

        if(alarmManager==null){
            alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);

        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }

}