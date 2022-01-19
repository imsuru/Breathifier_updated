package com.example.breathifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class StressCalculator extends AppCompatActivity {
    String[] questions = {"1. In the last month, how often have you been upset because of something that \n" +
            " happened unexpectedly?", "2. In the last month, how often have you felt that you were unable to control the\n" +
            " important things in your life?", "3. In the last month, how often have you felt nervous and stressed?", "4. In the last month, how often have you felt confident about your ability to handle \n" +
            " your personal problems?", "5. In the last month, how often have you felt that things were going your way?", "6. In the last month, how often have you found that you could not cope with\n" +
            " all the things that you had to do?", "7. In the last month, how often have you been able to control irritations in\n" +
            " your life?", "8. In the last month, how often have you felt that you were on top of things?", "9. In the last month, how often have you been angered because of things that\n" +
            " happened that were outside of your control?", "10. In the last month, how often have you felt difficulties were piling up so high that\n" +
            " you could not overcome them?"};

    TextView textView;
    RadioButton radioButton;
    RadioGroup radioGroup;
    public int i =1;
    int sum, total;
    Button btnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_calculator);
        textView = (TextView) findViewById(R.id.sctv5);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnext = (Button) findViewById(R.id.scbtnext);

        btnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i < questions.length) {
                    textView.setText(questions[i]);
                    if(radioGroup.getCheckedRadioButtonId()==-1){
                        Toast.makeText(StressCalculator.this, "Please Select The Given Option", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        checkedButton(radioGroup);
                        total += sum;
                        i++;
                        radioGroup.clearCheck();
                    }

                } else {
                    checkedButton(radioGroup);
                    total += sum;
                    Intent intent = new Intent(StressCalculator.this, stressDisplay.class);
                    intent.putExtra("score", total);
                    startActivity(intent);
                }
            }
        });
    }

     public void checkedButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String text = radioButton.getText().toString();
        int n = Integer.parseInt(text);
        sum = n;

    }
}



