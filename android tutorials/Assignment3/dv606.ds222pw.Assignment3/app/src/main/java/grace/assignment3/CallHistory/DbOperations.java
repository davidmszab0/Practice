package grace.assignment3.CallHistory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for database-actions
 * Source: Julia Bergmayr, https://github.com/bergmali/android/tree/master/ass3
 * This class is not my, David Szabo's work!
 */

public class DbOperations {

	// Database fields
	private SQLiteDatabase database;
    private DbHelper dbHelper;
	private String[] allColumns = { dbHelper.COLUMN_ID, dbHelper.COLUMN_DATE,
			dbHelper.COLUMN_NUMBER };

    public DbOperations(Context context) {
        System.out.println("DbOperations constructor.");
        dbHelper = new DbHelper(context);
    }

	/**
	 * opens database connection
	 *
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * Close database connection
	 */
	public void close() {
        dbHelper.close();
	}

    /**
	 * Add a new Call to database
	 *
	 * @param date
	 * @param number
	 * @return new Call object
	 */
	public CallInfo addCall(String date, String number) {
		ContentValues values = new ContentValues();
		values.put(dbHelper.COLUMN_DATE, date); // Date of call
		values.put(dbHelper.COLUMN_NUMBER, number); // Phone Number
		long insertId = database.insert(dbHelper.TABLE_NAME, null, values);
		Cursor cursor = database.query(dbHelper.TABLE_NAME, allColumns, dbHelper.COLUMN_ID + " = "
				+ insertId, null, null, null, null);
		cursor.moveToFirst();
		CallInfo c = cursorToCall(cursor);
		cursor.close();
		return c;
	}

	/**
	 * Delete Call entry
	 */
	public void deleteCall(CallInfo c) {
		long id = c.getId();
		System.out.println("DbOperation - Call deleted with id: " + id);
		database.delete(dbHelper.TABLE_NAME, dbHelper.COLUMN_ID + " = " + id, null);
	}

	/**
	 * Update call
	 *
	 * @param id
	 * : Id of call to be updated
	 * @param date
	 * : new date
	 * @param number
	 * : number of call to be updated
	 * @return
	 */
	public boolean updateCall(long id, String date, String number) {
		ContentValues args = new ContentValues();
		args.put(dbHelper.COLUMN_DATE, date);
		args.put(dbHelper.COLUMN_NUMBER, number);

		String restrict = dbHelper.COLUMN_ID + "=" + id;
		return database.update(dbHelper.TABLE_NAME, args, restrict, null) > 0;
	}

	/**
	 * Cursor to Call Here the new Call-object is created
	 *
	 * @param cursor
	 * @return
	 */
	private CallInfo cursorToCall(Cursor cursor) {
		CallInfo c = new CallInfo();
		c.setId(cursor.getInt(0));
		c.setDate(cursor.getString(1));
		c.setNumber(cursor.getString(2));
		return c;
	}

	/**
	 * Returns call with specific id
	 *
	 * @param id
	 * @return
	 */
	public CallInfo getCall(int id) {
		System.out.println("DbOperation - getCall");
		String restrict = dbHelper.COLUMN_ID + "=" + id;
		Cursor cursor = database.query(true, dbHelper.TABLE_NAME, allColumns, restrict, null, null,
				null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			CallInfo c = cursorToCall(cursor);
			return c;
		}
		// Make sure to close the cursor
		cursor.close();
		return null;
	}

	/**
	 * Returns all Calls currently saved in the database
	 *
	 * @return
	 */
	public List<CallInfo> getAllCalls() {
		System.out.println("DbOperation - getAllCalls");
		List<CallInfo> callList = new ArrayList<CallInfo>();
		Cursor cursor = database.query(dbHelper.TABLE_NAME, allColumns, null, null, null, null,
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			CallInfo c = cursorToCall(cursor);
			callList.add(c);
			cursor.moveToNext();
		}
		cursor.close();
		return callList;
	}

}
