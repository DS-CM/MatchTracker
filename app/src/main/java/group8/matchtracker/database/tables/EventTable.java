package group8.matchtracker.database.tables;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;

import group8.matchtracker.data.Event;
import group8.matchtracker.database.DatabaseHelper;


public class EventTable extends DBTable {
    public final String TAG = getClass().getSimpleName();

    private String[] mAllColumns = {mDbHelper.EVENT_ID, mDbHelper.EVENT_NAME,
            mDbHelper.EVENT_START, mDbHelper.EVENT_END, mDbHelper.EVENT_LOCATION,
            mDbHelper.EVENT_ORGANIZER};

    public EventTable(Context context, DatabaseHelper dbHelper) {
        super(context, dbHelper);
        //mDatabase.execSQL("DROP TABLE IF EXISTS " + dbHelper.TABLE_EVENT);
        //mDatabase.execSQL(mDbHelper.SQL_CREATE_TABLE_EVENTS);
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Event createEvent(String name, int start, int end, String location, String organizer) {
        ContentValues values = new ContentValues();
        values.put(mDbHelper.EVENT_NAME, name);
        values.put(mDbHelper.EVENT_START, start);
        values.put(mDbHelper.EVENT_END, end);
        values.put(mDbHelper.EVENT_LOCATION, location);
        values.put(mDbHelper.EVENT_ORGANIZER, organizer);

        long insertId =  mDatabase.insert(mDbHelper.TABLE_EVENT, null, values);

        return new Event(insertId, name, start, end, location, organizer);
    }

    public Event getEvent(long id) {
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_EVENT, mAllColumns, mDbHelper.EVENT_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            return new Event(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("start")),
                    cursor.getInt(cursor.getColumnIndex("end")),
                    cursor.getString(cursor.getColumnIndex("location")),
                    cursor.getString(cursor.getColumnIndex("organizer")));
        }
        return new Event();
    }

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> listEvents = new ArrayList<>();
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_EVENT, mAllColumns, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                listEvents.add(new Event(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getInt(cursor.getColumnIndex("start")),
                        cursor.getInt(cursor.getColumnIndex("end")),
                        cursor.getString(cursor.getColumnIndex("location")),
                        cursor.getString(cursor.getColumnIndex("organizer"))));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listEvents;
    }

    public Event getEventByName(String name) {
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_EVENT, mAllColumns,
                mDbHelper.EVENT_NAME + " = ?", new String[]{name}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new Event(cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getInt(cursor.getColumnIndex("start")),
                cursor.getInt(cursor.getColumnIndex("end")),
                cursor.getString(cursor.getColumnIndex("location")),
                cursor.getString(cursor.getColumnIndex("organizer")));
    }

    public void removeAllEvents() {
        mDatabase.execSQL("delete from " + mDbHelper.TABLE_EVENT);
    }

}
