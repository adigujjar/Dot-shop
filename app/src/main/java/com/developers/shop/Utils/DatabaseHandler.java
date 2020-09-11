package com.developers.shop.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "cartdata";

    private static final String TABLE = "CartData";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_Quantity = "quantity";
    private static final String KEY_P = "price";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_Quantity + " TEXT," + KEY_P + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    // Adding new contct
    public void addContact(Content content) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, content.getName());
        values.put(KEY_Quantity, content.get_quantity());
        values.put(KEY_P, content.getPrice());

        db.insert(TABLE, null, values);
        db.close();
    }

    // Getting All Contacts
    public List<Content> getAllContacts() {
        List<Content> contentList = new ArrayList<Content>();
        String selectQuery = "SELECT  * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Content content = new Content();
                content.setID(Integer.parseInt(cursor.getString(0)));
                content.setName(cursor.getString(1));
                content.set_quantity(cursor.getString(2));
                content.setPrice(cursor.getString(3));
                contentList.add(content);
            } while (cursor.moveToNext());
        }
        return contentList;
    }

    // Deleting single contact
    public void deleteContact(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, KEY_NAME + " = ?", new String[]{name});
        db.close();
    }

    // Deleting All contact
    public void deleteAllContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE,"1",null);
        db.close();
    }
}