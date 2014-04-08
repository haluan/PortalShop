package com.portalshop.xmlparser;

import java.util.ArrayList;
import com.portalshop.model.Bookmark;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "bookmarked";

	// Contacts table name
	private static final String TABLE = "bookmark";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAMA = "nama";
	private static final String KEY_OULET = "oulet";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE + "("
				+ KEY_ID + " TEXT," + KEY_NAMA + " TEXT,"
				+ KEY_OULET + " TEXT" + ")";
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
	public void addContact(Bookmark contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, contact.get_id_barang());
		values.put(KEY_NAMA, contact.get_nama_barang()); // Contact Name
		values.put(KEY_OULET, contact.getOutlet()); // Contact Phone

		// Inserting Row
		db.insert(TABLE, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	Bookmark getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE, new String[] { KEY_ID,
				KEY_NAMA, KEY_OULET}, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Bookmark contact = new Bookmark(cursor.getString(0),
				cursor.getString(1), cursor.getString(2));
		// return contact
		return contact;
	}
	
	// Getting All Contacts
	public ArrayList<Bookmark> getAllContacts() {
		ArrayList<Bookmark> contactList = new ArrayList<Bookmark>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Bookmark contact = new Bookmark();
				contact.set_id_barang(cursor.getString(0));
				contact.set_nama_barang(cursor.getString(1));
				contact.setOutlet(cursor.getString(2));
				// Adding contact to list
				contactList.add(contact);
				Log.d("bookmark", cursor.getString(1));
			} while (cursor.moveToNext());
		}
		db.close();
		// return contact list
		return contactList;
	}

	// Updating single contact
	public int updateContact(Bookmark contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAMA, contact.get_nama_barang());
		values.put(KEY_OULET, contact.getOutlet());

		// updating row
		return db.update(TABLE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.get_id_barang()) });
	}

	// Deleting single contact
	public void deleteContact(Bookmark contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.get_id_barang()) });
		db.close();
	}
	public void deleteAllBokkmark(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE, null,null);
		db.close();
	}

	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
