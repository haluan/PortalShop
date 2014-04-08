package com.portalshop.xmlparser;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryHandler extends SQLiteOpenHelper{
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "historys";

	// Contacts table name
	private static final String TABLE = "history";

	// Contacts Table Columns names
	private static final String KEY_HISTORY = "his";

	public HistoryHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE + "("
				+ KEY_HISTORY + " TEXT " + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public void addHistory(String his) {
		SQLiteDatabase db = this.getWritableDatabase();
		String temp=getHistory(his);
		if(temp.isEmpty()){
			ContentValues values = new ContentValues();
			values.put(KEY_HISTORY, his);

			// Inserting Row
			db.insert(TABLE, null, values);
			db.close(); // Closing database connection
		}
		
	}

	// Getting single contact
	String getHistory(String his) {
		SQLiteDatabase db = this.getReadableDatabase();
		String history="";
		Cursor cursor = db.query(TABLE, new String[] { KEY_HISTORY}, KEY_HISTORY + "=?",
				new String[] { his }, null, null, null, null);
		if (cursor != null&&cursor.getCount()>0){
			cursor.moveToFirst();
			history= cursor.getString(0);
		}
		// return contact
		return history;
	}
	
	// Getting All Contacts
	public ArrayList<String> getHIstoryLike(String his) {
		ArrayList<String> history = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE+" WHERE "+KEY_HISTORY+" LIKE '%"+his+"%'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				
				// Adding contact to list
				history.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		db.close();
		// return contact list
		return history;
	}
}
