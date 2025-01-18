package com.example.breathifier;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class stressDisplay extends AppCompatActivity {
    TextView tvRes, tvDoctorDetails, recommendationText;
    Button yogaButton, meditationButton, dietButton;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(stressDisplay.this, Introduction.class);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_display);

        // Initialize views
        tvRes = findViewById(R.id.tvdis);
        tvDoctorDetails = findViewById(R.id.tvDoctorDetails);
        recommendationText = findViewById(R.id.recommendationText);
        yogaButton = findViewById(R.id.yogaButton);
        meditationButton = findViewById(R.id.meditationButton);
        dietButton = findViewById(R.id.dietButton);

        // Retrieve extras from the intent
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            tvRes.setText("Error retrieving data");
            return;
        }

        int score = extras.getInt("score", 0);
        boolean danger = extras.getBoolean("danger", false);
        String doctorDetails = extras.getString("doctorDetails", null);
        String stressLevel = extras.getString("stressLevel", null);

        if (danger) {
            // Danger level: Display danger message and doctor details
            tvRes.setText("You are going through suicidal thoughts, consider doctor consultation.");
            displayDoctorDetails(doctorDetails);
            hideRecommendations();
        } else if ("High".equalsIgnoreCase(stressLevel)) {
            // High stress level: Display high stress message and recommendations
            tvRes.setText("Your stress level is High. We recommend you to consult a doctor.");
            displayDoctorDetails(doctorDetails);
            recommendationText.setText("We also recommend Yoga, Meditation, and a Healthy Diet.");
            showRecommendations();
        } else if ("Moderate".equalsIgnoreCase(stressLevel)) {
            // Moderate stress level: Recommendations only
            tvRes.setText("Your stress level is Moderate.");
            recommendationText.setText("We highly recommend Yoga, Meditation, and a Healthy Diet.");
            hideDoctorDetails();
            showRecommendations();
        } else if ("Low".equalsIgnoreCase(stressLevel)) {
            // Low stress level: Optional recommendations
            tvRes.setText("Your stress level is Low.");
            recommendationText.setText("Although your stress level is low we recommend you yoga and meditation (Optional).");
            hideDoctorDetails();
            showYogaAndMeditationRecommendationsOnly();
        } else {
            // Error or unknown case
            tvRes.setText("Error determining stress level.");
            hideDoctorDetails();
            hideRecommendations();
        }

        // Yoga button click handler
        yogaButton.setOnClickListener(v -> {
            Intent yogaIntent = new Intent(this, YogaIntro.class); // Replace with your Yoga activity class
            startActivity(yogaIntent);
        });

        // Meditation button click handler
        meditationButton.setOnClickListener(v -> {
            Intent meditationIntent = new Intent(this, ActualMeditation.class); // Replace with your Meditation activity class
            startActivity(meditationIntent);
        });

        // Diet button click handler
        dietButton.setOnClickListener(v -> {
            Intent dietIntent = new Intent(this, BreakFatIntro.class); // Replace with your Healthy Diet activity class
            startActivity(dietIntent);
        });
    }

    private void displayDoctorDetails(String doctorDetails) {
        if (doctorDetails != null && !doctorDetails.isEmpty()) {
            tvDoctorDetails.setText("Doctor Details:\n" + doctorDetails);
            tvDoctorDetails.setVisibility(TextView.VISIBLE);
        } else {
            tvDoctorDetails.setVisibility(TextView.GONE);
        }
    }

    private void hideDoctorDetails() {
        tvDoctorDetails.setVisibility(TextView.GONE);
    }

    private void showRecommendations() {
        yogaButton.setVisibility(Button.VISIBLE);
        meditationButton.setVisibility(Button.VISIBLE);
        dietButton.setVisibility(Button.VISIBLE);
    }

    private void showYogaAndMeditationRecommendationsOnly() {
        yogaButton.setVisibility(Button.VISIBLE);
        meditationButton.setVisibility(Button.VISIBLE);
        dietButton.setVisibility(Button.GONE);
    }

    private void hideRecommendations() {
        recommendationText.setText("");
        yogaButton.setVisibility(Button.GONE);
        meditationButton.setVisibility(Button.GONE);
        dietButton.setVisibility(Button.GONE);
    }
}
