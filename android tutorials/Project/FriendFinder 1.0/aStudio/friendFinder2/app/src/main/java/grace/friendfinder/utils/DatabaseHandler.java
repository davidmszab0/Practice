package grace.friendfinder.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.HashMap;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author David M. Szabo
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "android_api";
    // Login table name
    private final String TABLE_LOGIN = "login";

    // Login Table Columns names
    private final String KEY_ID = "id";
    private final String KEY_NAME = "name";
    private final String KEY_GENDER = "gender";
    private final String KEY_EMAIL = "email";
    private final String KEY_CREATED_AT = "created_at";
    private final String KEY_USER_ID = "user_id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_GENDER + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_CREATED_AT + " TEXT,"
                + KEY_USER_ID + " INTEGER" +")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String gender, String email, String created_at, Integer user_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if (isNotBlank(name)) {
            values.put(KEY_NAME, name); // Name
        }
        if (isNotBlank(gender)) {
            values.put(KEY_GENDER, gender); // Gender
        }
        if (isNotBlank(email)) {
            values.put(KEY_EMAIL, email); // Email
        }
        if (isNotBlank(created_at)) {
            values.put(KEY_CREATED_AT, created_at); // CreatedAt
        }
        if (user_id != null) {
            values.put(KEY_USER_ID, user_id); // user_id
        }

//        values.put(KEY_NAME, name); // Name
//        values.put(KEY_GENDER, gender); // Gender
//        values.put(KEY_EMAIL, email); // Email
//        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }

    /**
     *
     * @param name
     * @param gender
     * @param email
     * @param created_at
     */
    public void updateUser(String name, String gender, String email, String created_at, Integer user_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if (isNotBlank(name)) {
            values.put(KEY_NAME, name); // Name
        }
        if (isNotBlank(gender)) {
            values.put(KEY_GENDER, gender); // Gender
        }
        if (isNotBlank(email)) {
            values.put(KEY_EMAIL, email); // Email
        }
        if (isNotBlank(created_at)) {
            values.put(KEY_CREATED_AT, created_at); // CreatedAt
        }
        if (user_id != null) {
            values.put(KEY_USER_ID, user_id); // user_id
        }

        String restrict = KEY_ID + "=" + 1;
        db.update(TABLE_LOGIN, values, restrict, null);
        db.close(); // Closing database connection
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("name", cursor.getString(1));
            user.put("gender", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
            user.put("user_id", cursor.getString(5));
        }
        cursor.close();
        db.close();

        return user;
    }

    /**
     * Getting user login status
     * return true if rows are there in table
     * -> Function get Login status
     * */
    public boolean getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();

        if (rowCount > 0){
            return true;
        }
        db.close();
        cursor.close();

        // return row count
        return false;
    }

    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }
}
