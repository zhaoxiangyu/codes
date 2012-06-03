package org.sharp.android.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
	private DatabaseHelper mDatabaseHelper;
	private SQLiteDatabase mDB;
	private String mDBFilePath;

	public SQLiteDatabase getDB() {
		return this.mDB;
	}

	public DatabaseManager(Context context) {
		this.mDatabaseHelper = new DatabaseHelper(context);
	}

	public DatabaseManager(Context context, String dbFilePath) {
		this.mDatabaseHelper = new DatabaseHelper(context);
		this.mDBFilePath = dbFilePath;
	}

	public SQLiteDatabase open() {
		if(mDBFilePath == null){
			this.mDB = this.mDatabaseHelper.getWritableDatabase();
		}else{
			try{
				this.mDB = SQLiteDatabase.openDatabase(mDBFilePath, 
						null, SQLiteDatabase.OPEN_READWRITE);
			}catch (Exception e) {
				this.mDB = SQLiteDatabase.openOrCreateDatabase(mDBFilePath, null);
				this.mDatabaseHelper.onCreate(this.mDB);
			}
		}
		return this.mDB;
	}

	public void close() {
		if (this.mDB != null) {
			this.mDB.close();
			this.mDB = null;
		}
	}
}
