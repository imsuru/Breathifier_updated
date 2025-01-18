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
    String[] questions = {
            "1. In the last month, how often have you been upset because of something that happened unexpectedly?",
            "2. In the last month, how often have you felt that you were unable to control the important things in your life?",
            "3. In the last month, how often have you felt nervous and stressed?",
            "4. In the last month, how often have you felt confident about your ability to handle your personal problems?",
            "5. In the last month, how often have you felt that things were going your way?",
            "6. In the last month, how often have you found that you could not cope with all the things that you had to do?",
            "7. In the last month, how often have you been able to control irritations in your life?",
            "8. In the last month, how often have you felt that you were on top of things?",
            "9. In the last month, how often have you been angered because of things that happened outside of your control?",
            "10. In the last month, how often have you felt difficulties were piling up so high that you could not overcome them?",
            "11. In the last month, how often have you gone through suicidal thoughts?"
    };

    String doctorDetails = "Dr. John Doe\nPsychologist\nPhone: +1 234 567 890\nEmail: johndoe@example.com";

    TextView textView;
    RadioGroup radioGroup;
    Button btnext;

    int i = 0; // Current question index
    int total = 0; // Total stress score

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_calculator);

        // Initialize UI components
        textView = findViewById(R.id.sctv5);
        radioGroup = findViewById(R.id.radioGroup);
        btnext = findViewById(R.id.scbtnext);

        // Start with the first question
        updateQuestion();

        // Set the button click listener
        btnext.setOnClickListener(v -> {
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                // Highlight the question if no option is selected
                Toast.makeText(StressCalculator.this, "Please select an option!", Toast.LENGTH_SHORT).show();
            } else {
                calculateScore();
                if (i == questions.length - 1) {
                    handleLastQuestion();
                } else {
                    i++;
                    updateQuestion();
                }
            }
        });
    }

    private void calculateScore() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedButton = findViewById(radioId);
        try {
            int score = Integer.parseInt(selectedButton.getText().toString());
            total += score;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input detected!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateQuestion() {
        textView.setText(questions[i]);
        radioGroup.clearCheck(); // Clear previous selection
    }

    private void handleLastQuestion() {
        Intent intent = new Intent(StressCalculator.this, stressDisplay.class);

        if (radioGroup.getCheckedRadioButtonId() != -1) {
            int radioId = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedButton = findViewById(radioId);
            int lastScore = Integer.parseInt(selectedButton.getText().toString());
            if (lastScore > 0) {
                // Danger case
                intent.putExtra("score", total);
                intent.putExtra("danger", true);
                intent.putExtra("doctorDetails", doctorDetails);
            } else {
                // Categorize stress level
                String stressLevel = (total <= 10) ? "Low" : (total <= 20) ? "Moderate" : "High";
                intent.putExtra("score", total);
                intent.putExtra("danger", false);
                intent.putExtra("stressLevel", stressLevel);
            }
            startActivity(intent);
        }
    }
}
