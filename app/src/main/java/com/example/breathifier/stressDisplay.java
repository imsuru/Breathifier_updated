package com.example.breathifier;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
        ScrollView scrollView = findViewById(R.id.scrollView);


        // Retrieve extras from intent
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            tvRes.setText("Error retrieving data");
            return;
        }

        boolean danger = extras.getBoolean("danger", false);
        String stressLevel = extras.getString("stressLevel", null);

        // Ensure stress level text is always visible
        tvRes.setVisibility(View.VISIBLE);

        if (danger) {
            // Show doctor details carousel when danger is detected
            tvRes.setText("You are going through suicidal thoughts, consider doctor consultation.");
            doctorContainer.setVisibility(View.VISIBLE);
            btnPrevious.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            displayDoctorDetails();
            hideRecommendations();
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));

        } else if ("High".equalsIgnoreCase(stressLevel)) {
            tvRes.setText("Your stress level is High. We recommend you to consult a doctor.");
            doctorContainer.setVisibility(View.VISIBLE);
            btnPrevious.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            displayDoctorDetails();
            recommendationText.setText("We also recommend Yoga, Meditation, and a Healthy Diet.");
            showRecommendations();
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));

        } else if ("Moderate".equalsIgnoreCase(stressLevel)) {
            tvRes.setText("Your stress level is Moderate.");
            recommendationText.setText("We highly recommend Yoga, Meditation, and a Healthy Diet.");
            hideDoctorDetails();
            showRecommendations();
        } else if ("Low".equalsIgnoreCase(stressLevel)) {
            tvRes.setText("Your stress level is Low.");
            recommendationText.setText("Although your stress level is low, we recommend Yoga and Meditation (Optional).");
            hideDoctorDetails();
            showYogaAndMeditationRecommendationsOnly();
        } else {
            tvRes.setText("Error determining stress level.");
            hideDoctorDetails();
            hideRecommendations();
        }

        // Ensure buttons are visible before setting click listeners
        yogaButton.setVisibility(View.VISIBLE);
        meditationButton.setVisibility(View.VISIBLE);
        dietButton.setVisibility(View.VISIBLE);

        // Button Click Handlers for ViewFlipper
        btnPrevious.setOnClickListener(v -> {
            viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
            viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
            viewFlipper.showPrevious();
        });

        btnNext.setOnClickListener(v -> {
            viewFlipper.setInAnimation(this, android.R.anim.slide_out_right);
            viewFlipper.setOutAnimation(this, android.R.anim.slide_in_left);
            viewFlipper.showNext();
        });

        // Ensure buttons redirect correctly
        yogaButton.setOnClickListener(v -> {
            Intent yogaIntent = new Intent(this, YogaIntro.class);
            startActivity(yogaIntent);
        });

        meditationButton.setOnClickListener(v -> {
            Intent meditationIntent = new Intent(this, ActualMeditation.class);
            startActivity(meditationIntent);
        });

        dietButton.setOnClickListener(v -> {
            Intent dietIntent = new Intent(this, BreakFatIntro.class);
            startActivity(dietIntent);
        });
    }

    private void displayDoctorDetails() {
        String[][] doctors = {
                {"Dr. Ajay Balki", "📞 022 6948 9850", "🏥 Manak Healthcare Care Hospital\nNear Rajiv Gandhi Bridge, Sector 8, Nerul West, Nerul, Navi Mumbai, Maharashtra 400706"},
                {"Dr. Ravindra Chavhan", "📞 9820091517", "🏥 Lifecare Hospital\nF1 Building, Sungrace, Near Shabri Hotel, Juhu Nagar, Sector 10, Vashi, Navi Mumbai, Maharashtra 400703"},
                {"Dr. Yogita Shinde", "📞 8928157119", "🏥 Amale Hospital\nPlot No 115, Behind Shabari Restaurant, Sector-1, New Panvel East, Panvel, Navi Mumbai, Maharashtra 410206"}
        };

        for (String[] doctor : doctors) {
            String name = doctor[0];
            String phone = doctor[1];
            String address = doctor[2];

            String phoneLink = "<a href='tel:" + phone.replace("📞 ", "") + "'>" + phone + "</a>";
            String mapQuery = Uri.encode(address.replace("🏥 ", ""));
            String addressLink = "<a href='https://www.google.com/maps/search/?api=1&query=" + mapQuery + "'>" + address + "</a>";

            TextView doctorView = new TextView(this);
            doctorView.setText(Html.fromHtml("<b>" + name + "</b><br><br>" + phoneLink + "<br><br>" + addressLink));
            doctorView.setTextSize(18);
            doctorView.setPadding(40, 30, 40, 30);
            doctorView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            doctorView.setLineSpacing(1.2f, 1.2f);
            doctorView.setMovementMethod(LinkMovementMethod.getInstance());

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

    private void showYogaAndMeditationRecommendationsOnly() {
        yogaButton.setVisibility(View.VISIBLE);
        meditationButton.setVisibility(View.VISIBLE);
        dietButton.setVisibility(View.GONE);
    }

    private void hideRecommendations() {
        recommendationText.setText("");
        yogaButton.setVisibility(View.GONE);
        meditationButton.setVisibility(View.GONE);
        dietButton.setVisibility(View.GONE);
    }
}
