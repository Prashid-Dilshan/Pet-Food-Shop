package com.example.petfoodshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pet_food_shop.db";
    private static final int DATABASE_VERSION = 1;

    // Table for users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Table for pet food items
    private static final String TABLE_FOOD = "food";
    private static final String COLUMN_FOOD_ID = "food_id";
    private static final String COLUMN_FOOD_NAME = "name";
    private static final String COLUMN_FOOD_PRICE = "price";
    private static final String COLUMN_FOOD_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, "
                + COLUMN_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(createUsersTable);

        // Create Food Table
        String createFoodTable = "CREATE TABLE " + TABLE_FOOD + " ("
                + COLUMN_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FOOD_NAME + " TEXT NOT NULL, "
                + COLUMN_FOOD_PRICE + " REAL NOT NULL, "
                + COLUMN_FOOD_IMAGE + " BLOB NOT NULL)";
        db.execSQL(createFoodTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
    }

    // Adding a new user with hashed password
    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, hashPassword(password));

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1; // Returns true if the insertion was successful
    }

    // Checking if username and password exist
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String hashedPassword = hashPassword(password);
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_USER_ID},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, hashedPassword},
                null, null, null);

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return userExists;
    }

    // Adding a new food item
    public boolean addFood(String name, double price, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_NAME, name);
        values.put(COLUMN_FOOD_PRICE, price);
        values.put(COLUMN_FOOD_IMAGE, image);

        long result = db.insert(TABLE_FOOD, null, values);
        db.close();
        return result != -1; // Returns true if the insertion was successful
    }

    // Getting all food items
    public Cursor getAllFood() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FOOD, null);
    }

    // Getting a food item by ID
    public Cursor getFoodById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_FOOD, null, COLUMN_FOOD_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
    }

    // Updating a food item by ID
    public boolean updateFood(int id, String name, double price, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_NAME, name);
        values.put(COLUMN_FOOD_PRICE, price);
        values.put(COLUMN_FOOD_IMAGE, image);

        int result = db.update(TABLE_FOOD, values, COLUMN_FOOD_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0; // Returns true if at least one row was updated
    }

    // Deleting a food item by ID
    public boolean deleteFood(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_FOOD, COLUMN_FOOD_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0; // Returns true if at least one row was deleted
    }

    // Deleting all food items
    public boolean deleteAllFoods() {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_FOOD, null, null);
        db.close();
        return result > 0; // Returns true if at least one row was deleted
    }

    // Utility method to hash passwords
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
