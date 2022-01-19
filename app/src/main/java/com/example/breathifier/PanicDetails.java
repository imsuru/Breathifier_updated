package com.example.breathifier;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class PanicDetails extends AppCompatActivity {

    private Button btselCon;
    private EditText Number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic_details);

        btselCon=(Button)findViewById(R.id.selectCon);
        Number=(EditText)findViewById(R.id.number);

        btselCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String num=Number.getText().toString();
                if(TextUtils.isEmpty(num)){
                    Toast.makeText(PanicDetails.this, "Fill The Details Correctly", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PanicDetails.this, "Number Saved SuccessFully", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("PanicNumber", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("num", num);
                    editor.apply();
                }
            }
        });

    }
}