package com.example.petfoodshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Find the Profile button by its ID
        Button profileButton = findViewById(R.id.profile);
        // Find the Pet Food button by its ID
        Button petFoodButton = findViewById(R.id.welcomePetfoodBtn);
        // Find the Article button by its ID
        Button articleButton = findViewById(R.id.welcomeArticle);

        // Set an OnClickListener for the Profile button
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start ProfileDetailsActivity
                Intent intent = new Intent(HomePageActivity.this, ProfileDetailsActivity.class);
                startActivity(intent);
            }
        });

        // Set an OnClickListener for the Pet Food button
        petFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start PetFoodActivity
                Intent intent = new Intent(HomePageActivity.this, PetFoodActivity.class);
                startActivity(intent);
            }
        });

        // Set an OnClickListener for the Article button
        articleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start CustomerArticle
                Intent intent = new Intent(HomePageActivity.this, CustomerArticle.class);
                startActivity(intent);
            }
        });
    }
}
