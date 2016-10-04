package com.mlabs.bbm.firstandroidapp_morningclass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by androidstudio on 17/09/16.
 */
public class UsersDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private Context context;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_FNAME, MySQLiteHelper.COLUMN_LNAME, MySQLiteHelper.COLUMN_UNAME,
                                     MySQLiteHelper.COLUMN_EMAIL, MySQLiteHelper.COLUMN_PASSWORD, MySQLiteHelper.COLUMN_DATE};

    public UsersDataSource(Context context) {

        this.context = context;
        dbHelper = new MySQLiteHelper(context);
    }

    public void createDatabase(){
        dbHelper.onUpgrade(dbHelper.getReadableDatabase(), 1, 2);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createUser(String fname, String lname, String uname, String email, String password, String date) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_FNAME, fname);
        values.put(MySQLiteHelper.COLUMN_LNAME, lname);
        values.put(MySQLiteHelper.COLUMN_UNAME, uname);
        values.put(MySQLiteHelper.COLUMN_EMAIL, email);
        values.put(MySQLiteHelper.COLUMN_PASSWORD, password);
        values.put(MySQLiteHelper.COLUMN_DATE, date);
        long insertId = database.insert(MySQLiteHelper.TABLE_USER, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER, allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        cursor.close();
    }

    public User getUser(String loginName){

        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER, allColumns, "emails = ? OR unames = ?", new String[]{String.valueOf(loginName), String.valueOf(loginName)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();

        User user = cursorToUser(cursor);

        return user;

    }

    public boolean ifUsernameIsAvailable(String username){
        boolean usernameIsAvailable = false;

        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER, allColumns, "unames = ?", new String[]{String.valueOf(username)}, null, null, null, null);

        if(!cursor.moveToFirst())
            usernameIsAvailable = true;

        return usernameIsAvailable;
    }

    public boolean ifEmailIsAvailable(String email){
        boolean emailIsAvailable = false;

        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER, allColumns, "emails = ?", new String[]{String.valueOf(email)}, null, null, null, null);

        if(!cursor.moveToFirst())
            emailIsAvailable = true;

        return emailIsAvailable;
    }

    public User cursorToUser(Cursor cursor) {
        User user = new User();
        try {
            user.setId(cursor.getLong(0));
            user.setFname(cursor.getString(1));
            user.setLname(cursor.getString(2));
            user.setUname(cursor.getString(3));
            user.setEmail(cursor.getString(4));
            user.setPassword(cursor.getString(5));
            user.setDate(cursor.getString(6));
        }catch (CursorIndexOutOfBoundsException c){}
        return user;
    }
}
