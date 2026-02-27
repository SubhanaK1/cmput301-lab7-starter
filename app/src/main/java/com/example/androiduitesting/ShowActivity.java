package com.example.androiduitesting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShowActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_NAME = "extra_city_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show);

        // If your activity_show.xml has a root view with id "main", this keeps your template working
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String cityName = getIntent().getStringExtra(EXTRA_CITY_NAME);
        if (cityName == null) cityName = "";

        TextView cityText = findViewById(R.id.city_name_text);
        Button backBtn = findViewById(R.id.back_button);

        cityText.setText(cityName);

        // Back to MainActivity
        backBtn.setOnClickListener(v -> finish());
    }
}