package com.example.breathifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationIntroduction extends AppCompatActivity {
    private Button btReg, btLogin;
    private Button log;
    private FirebaseAuth auth;
    private EditText ed1, ed2;
    private FirebaseUser firebaseUser;
    private TextView textPrev,clickForgot,skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_introduction);
        getSupportActionBar().hide();
        textPrev=(TextView)findViewById(R.id.tvClick2);
        btLogin = (Button) findViewById(R.id.logingin);
        ed1 = (EditText) findViewById(R.id.emem);
        ed2 = (EditText) findViewById(R.id.psps);
        btLogin = (Button) findViewById(R.id.logingin);
        clickForgot=(TextView)findViewById(R.id.clickForgot);
        skip=(TextView)findViewById(R.id.skip);

        auth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        textPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationIntroduction.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });
        clickForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverPassword();
            }

        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String em2 = ed1.getText().toString();
                    String ep2 = ed2.getText().toString();
                    if (TextUtils.isEmpty(em2) || TextUtils.isEmpty(ep2)) {
                        Toast.makeText(RegistrationIntroduction.this, "Please Enter Necessary Details", Toast.LENGTH_SHORT).show();
                    } else {
                        login(em2, ep2);
                    }
                }
           
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedData("Guest");
            }
        });

    }
    void savedData(String email){
        SharedPreferences sharedPreferences=getSharedPreferences("loginData", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("LoginCounter",true);
        editor.putString("UserEmail",email);
        editor.apply();
        Intent intent = new Intent(RegistrationIntroduction.this, Introduction.class);
        startActivity(intent);
        finish();

    }
    private void login(String em2, String ep2) {
        auth.signInWithEmailAndPassword(em2, ep2).addOnCompleteListener(RegistrationIntroduction.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    verify(em2);

                } else {
                    Toast.makeText(RegistrationIntroduction.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void verify(String em2) {
        FirebaseUser firebaseUser1=auth.getCurrentUser();
        Boolean val=firebaseUser1.isEmailVerified();
        if(val){
            savedData(em2);//shared preference save data
            Toast.makeText(RegistrationIntroduction.this, "Login SuccessFull", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(RegistrationIntroduction.this, Introduction.class);
//            startActivity(intent);
//            finish();
        }
        else{
            Toast.makeText(RegistrationIntroduction.this, "Please Verify Your Email Id", Toast.LENGTH_SHORT).show();
            auth.signOut();
        }
    }
    private void recoverPassword() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Recovery Password");
        LinearLayout linearLayout=new LinearLayout(this);
        EditText ed=new EditText(this);
        ed.setHint("Enter Your Email");
        ed.setMinEms(16);
        linearLayout.addView(ed);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=ed.getText().toString();
                recoverMail(email);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void recoverMail(String email) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegistrationIntroduction.this, "Email Sent", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegistrationIntroduction.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}