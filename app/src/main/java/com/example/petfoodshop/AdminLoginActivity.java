package com.example.petfoodshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // Initialize UI elements
        etUsername = findViewById(R.id.admin_username);
        etPassword = findViewById(R.id.admin_password);
        btnLogin = findViewById(R.id.admin_login_button);

        // Set OnClickListener for the admin login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminLogin();
            }
        });
    }

    private void adminLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Username is required");
            etUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        // Check admin login credentials (you can replace this with a proper check)
        if ("Admin".equals(username) && "Admin".equals(password)) {
            Toast.makeText(this, "Admin Login successful", Toast.LENGTH_SHORT).show();

            // Navigate to the Admin Dashboard
            Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid admin username or password", Toast.LENGTH_SHORT).show();
        }
    }
}
