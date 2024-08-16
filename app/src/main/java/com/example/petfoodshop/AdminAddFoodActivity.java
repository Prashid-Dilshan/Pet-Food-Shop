package com.example.petfoodshop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AdminAddFoodActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextFoodName, editTextFoodPrice;
    private ImageView imageViewFood;
    private Button buttonChooseImage, buttonAddFood, buttonDeleteAll;

    private Bitmap selectedImageBitmap;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_food);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Initialize UI components
        editTextFoodName = findViewById(R.id.editTextFoodName);
        editTextFoodPrice = findViewById(R.id.editTextFoodPrice);
        imageViewFood = findViewById(R.id.imageViewFood);
        buttonChooseImage = findViewById(R.id.buttonChooseImage);
        buttonAddFood = findViewById(R.id.buttonAddFood);
        buttonDeleteAll = findViewById(R.id.buttonDeleteAll);

        // Set button listeners
        buttonChooseImage.setOnClickListener(v -> openImageChooser());
        buttonAddFood.setOnClickListener(v -> addFoodToDatabase());
        buttonDeleteAll.setOnClickListener(v -> deleteAllFoods());
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageViewFood.setImageBitmap(selectedImageBitmap);
            } catch (IOException e) {
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private void addFoodToDatabase() {
        String name = editTextFoodName.getText().toString().trim();
        String priceStr = editTextFoodPrice.getText().toString().trim();

        if (name.isEmpty() || priceStr.isEmpty() || selectedImageBitmap == null) {
            Toast.makeText(this, "Please fill all fields and choose an image", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert bitmap to byte array
        byte[] imageBytes = convertBitmapToByteArray(selectedImageBitmap);

        // Add food to database
        boolean inserted = databaseHelper.addFood(name, price, imageBytes);
        if (inserted) {
            Toast.makeText(this, "Food added successfully!", Toast.LENGTH_SHORT).show();
            clearInputs();
        } else {
            Toast.makeText(this, "Failed to add food", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void clearInputs() {
        editTextFoodName.setText("");
        editTextFoodPrice.setText("");
        imageViewFood.setImageResource(0);
    }

    private void deleteAllFoods() {
        boolean deleted = databaseHelper.deleteAllFoods();
        if (deleted) {
            Toast.makeText(this, "All foods deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete foods", Toast.LENGTH_SHORT).show();
        }
    }
}
