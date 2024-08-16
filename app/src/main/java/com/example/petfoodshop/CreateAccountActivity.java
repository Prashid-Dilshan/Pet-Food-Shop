package com.example.petfoodshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText etFullName, etMobileNumber, etUsername, etPassword, etPetName, etPetAge;
    private Button btnCreateAccount;
    private Utils dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initialize UI elements
        etFullName = findViewById(R.id.full_name);
        etMobileNumber = findViewById(R.id.mobile_number);
        etUsername = findViewById(R.id.new_username);
        etPassword = findViewById(R.id.new_password);
        etPetName = findViewById(R.id.pet_name);
        etPetAge = findViewById(R.id.pet_age);
        btnCreateAccount = findViewById(R.id.create_account_button);

        // Initialize Utils (database helper)
        dbHelper = new Utils(this);

        // Set OnClickListener for the create account button
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String fullName = etFullName.getText().toString().trim();
        String mobileNumber = etMobileNumber.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String petName = etPetName.getText().toString().trim();
        String petAge = etPetAge.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(fullName)) {
            etFullName.setError("Full Name is required");
            etFullName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mobileNumber)) {
            etMobileNumber.setError("Mobile Number is required");
            etMobileNumber.requestFocus();
            return;
        }

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

        if (TextUtils.isEmpty(petName)) {
            etPetName.setError("Pet Name is required");
            etPetName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(petAge)) {
            etPetAge.setError("Pet Age is required");
            etPetAge.requestFocus();
            return;
        }

        // Add user to database
        boolean isUserAdded = dbHelper.addUser(username, password);
        if (isUserAdded) {
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();

            // Redirect to the login page
            Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
        }
    }
}