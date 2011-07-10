package com.me.kzkamiya.android.flashviewer1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class StarLogDbManager {

	private static final String TAG = StarLogDbManager.class.getSimpleName();

	public static final String DB_NAME = "Flash_View_1.db";
	public static final int SCHEMA_VERSION = 1;

	public static final String TABLE_NAME = "starlog";
	public static final String ID = "_id";
	public static final String LOG_TEXT = "content";
	public static final String TIMESTAMP = "timestamp";

	public static final String SQL_CREATE_TABLE = "CREATE TABLE starlog (_id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT NOT NULL, timestamp INTEGER NOT NULL)";
	public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS starlog";

	private Context mContext;
	private DbHelper mDbHelper;
	private SQLiteDatabase mDb;

	private final class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DB_NAME, null, SCHEMA_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(SQL_DROP_TABLE);
			onCreate(db);
		}

	}

	public StarLogDbManager(Context context) {
		mContext = context;
	}

	public void open() {
		mDbHelper = new DbHelper(mContext);
		mDb = mDbHelper.getWritableDatabase();
	}

	public void close() {
		mDbHelper.close();
	}

	public Cursor fetchAll() {
		return mDb.query(TABLE_NAME, new String[] { ID, LOG_TEXT, TIMESTAMP },
				null, null, null, null, null);
	}

	public long createLogEntry(String text, Long timestamp) {
		ContentValues values = new ContentValues();
		values.put(LOG_TEXT, text);
		values.put(TIMESTAMP, timestamp);
		return mDb.insert(TABLE_NAME, null, values);
	}
	
	public Cursor fetchOne(long rowId) {
		Cursor cursor = mDb.query(TABLE_NAME, new String[] {ID, LOG_TEXT, TIMESTAMP}, ID + " = " + rowId, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	public boolean updateLogEntry(long id, String text, long timestamp) {
		ContentValues values = new ContentValues();
		values.put(ID, id);
		values.put(LOG_TEXT, text);
		values.put(TIMESTAMP, timestamp);
		return mDb.update(TABLE_NAME, values, ID + " = " + id, null) > 0;
	}
	
	public boolean deleteLogEntry(long id) {
		return mDb.delete(TABLE_NAME, ID + " = " + id, null) > 0;
	}
}
