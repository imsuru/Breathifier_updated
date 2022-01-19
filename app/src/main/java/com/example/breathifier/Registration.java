package com.example.breathifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registration extends AppCompatActivity {
    private EditText edEmail, edPass,regName,regPhone;
    private Button cre;
    private FirebaseAuth auth;
    private TextView LoginAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
        edEmail = (EditText) findViewById(R.id.createEmail);
        edPass = (EditText) findViewById(R.id.createPass);
        regName=(EditText)findViewById(R.id.Regname);
        regPhone=(EditText)findViewById(R.id.RegPhone);
        LoginAct=(TextView)findViewById(R.id.tvClick1);



        cre = (Button) findViewById(R.id.cre);
        auth = FirebaseAuth.getInstance();

        LoginAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Registration.this,RegistrationIntroduction.class);
                startActivity(intent);
                finish();
            }
        });

        cre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regNav=regName.getText().toString();
                String regPad=regPhone.getText().toString();
                //for authentication
                String emc = edEmail.getText().toString();
                String passc = edPass.getText().toString();

                if (TextUtils.isEmpty(emc) || TextUtils.isEmpty(passc) || TextUtils.isEmpty(regNav) || TextUtils.isEmpty(regPad)){
                    Toast.makeText(Registration.this, "Please fill the necessary details ", Toast.LENGTH_SHORT).show();

                } else {
                    create(emc,passc,regNav,regPad);
                }
            }

        });
    }
  void create(String emc, String passc,String regNav,String regPad) {
      auth.createUserWithEmailAndPassword(emc,passc).addOnCompleteListener(Registration.this,new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                FirebaseUser user=auth.getCurrentUser();
                String uid=user.getUid();//special id
                HashMap<Object,String>hashMap =new HashMap<>();
                hashMap.put("name",regNav);
                hashMap.put("phone",regPad);
                hashMap.put("email",emc);
                hashMap.put("uid",uid);

                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference("User");

                databaseReference.child(uid).setValue(hashMap);

                verification();

            }
            else{
                Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
          }


      });
    }
    private void verification() {
        FirebaseUser user=auth.getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                            Toast.makeText(Registration.this, "Email Sent Please Verify", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registration.this, RegistrationIntroduction.class);
                            startActivity(intent);
                            finish();
                    }
                    else{
                        Toast.makeText(Registration.this, "Please Check You Mail and Verify ", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }
 }

