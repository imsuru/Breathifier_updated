package com.example.breathifier;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;

public class stressDisplay extends AppCompatActivity {
    TextView tvRes, recommendationText;
    ViewFlipper viewFlipper;
    Button btnPrevious, btnNext, yogaButton, meditationButton, dietButton;
    LinearLayout doctorContainer;

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
        viewFlipper = findViewById(R.id.viewFlipper);
        doctorContainer = findViewById(R.id.doctorContainer);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        recommendationText = findViewById(R.id.recommendationText);
        yogaButton = findViewById(R.id.yogaButton);
        meditationButton = findViewById(R.id.meditationButton);
        dietButton = findViewById(R.id.dietButton);

        // Retrieve extras from intent
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            tvRes.setText("Error retrieving data");
            return;
        }

        boolean danger = extras.getBoolean("danger", false);
        String stressLevel = extras.getString("stressLevel", null);

        if (danger) {
            // Show doctor details carousel when danger is detected
            tvRes.setText("You are going through suicidal thoughts, consider doctor consultation.");
            doctorContainer.setVisibility(View.VISIBLE);
            btnPrevious.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            displayDoctorDetails();
            hideRecommendations();
        } else if ("High".equalsIgnoreCase(stressLevel)) {
            tvRes.setText("Your stress level is High. We recommend you to consult a doctor.");
            doctorContainer.setVisibility(View.VISIBLE);
            btnPrevious.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            displayDoctorDetails();
            recommendationText.setText("We also recommend Yoga, Meditation, and a Healthy Diet.");
            showRecommendations();
        } else {
            // Other cases (Moderate, Low, etc.)
            hideDoctorDetails();
            showRecommendations();
        }

        // Left Arrow Button: Move to Previous Doctor (Fix animation)
        btnPrevious.setOnClickListener(v -> {
            viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
            viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
            viewFlipper.showPrevious();
        });

        // Right Arrow Button: Move to Next Doctor (Fix animation)
        btnNext.setOnClickListener(v -> {
            viewFlipper.setInAnimation(this, android.R.anim.slide_out_right);
            viewFlipper.setOutAnimation(this, android.R.anim.slide_in_left);
            viewFlipper.showNext();
        });
    }

    private void displayDoctorDetails() {
        // Doctor details array
        String[][] doctors = {
                {"Dr. Ajay Balki", "📞 022 6948 9850", "🏥 Manak Healthcare Care Hospital\nNear Rajiv Gandhi Bridge, Sector 8, Nerul West, Nerul, Navi Mumbai, Maharashtra 400706"},
                {"Dr. Ravindra Chavhan", "📞 9820091517", "🏥 Lifecare Hospital\nF1 Building, Sungrace, Near Shabri Hotel, Juhu Nagar, Sector 10, Vashi, Navi Mumbai, Maharashtra 400703"},
                {"Dr. Yogita Shinde", "📞 8928157119", "🏥 Amale Hospital\nPlot No 115, Behind Shabari Restaurant, Sector-1, New Panvel East, Panvel, Navi Mumbai, Maharashtra 410206"}
        };

        for (String[] doctor : doctors) {
            String name = doctor[0];
            String phone = doctor[1];
            String address = doctor[2];

            // Make phone number clickable
            String phoneLink = "<a href='tel:" + phone.replace("📞 ", "") + "'>" + phone + "</a>";

            // Make address clickable for Google Maps
            String mapQuery = Uri.encode(address.replace("🏥 ", ""));
            String addressLink = "<a href='https://www.google.com/maps/search/?api=1&query=" + mapQuery + "'>" + address + "</a>";

            // Create a formatted TextView for better readability
            TextView doctorView = new TextView(this);
            doctorView.setText(Html.fromHtml("<b>" + name + "</b><br><br>" + phoneLink + "<br><br>" + addressLink));
            doctorView.setTextSize(18);
            doctorView.setPadding(40, 30, 40, 30);
            doctorView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            doctorView.setLineSpacing(1.2f, 1.2f);
            doctorView.setMovementMethod(LinkMovementMethod.getInstance());

            // Add to ViewFlipper
            viewFlipper.addView(doctorView);
        }
    }


    private void hideDoctorDetails() {
        doctorContainer.setVisibility(View.GONE);
        btnPrevious.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
    }

    private void showRecommendations() {
        yogaButton.setVisibility(View.VISIBLE);
        meditationButton.setVisibility(View.VISIBLE);
        dietButton.setVisibility(View.VISIBLE);
    }

    private void hideRecommendations() {
        recommendationText.setText("");
        yogaButton.setVisibility(View.GONE);
        meditationButton.setVisibility(View.GONE);
        dietButton.setVisibility(View.GONE);
    }
}
