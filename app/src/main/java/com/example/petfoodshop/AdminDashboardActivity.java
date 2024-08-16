package com.example.petfoodshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize the Add Food button
        Button addFoodButton = findViewById(R.id.addFoodButton);

        // Set OnClickListener to navigate to AdminAddfoodActivity
        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, AdminAddFoodActivity.class);
                startActivity(intent);
            }
        });
    }
}
