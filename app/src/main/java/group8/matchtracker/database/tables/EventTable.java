package group8.matchtracker.database.tables;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import group8.matchtracker.data.Event;
import group8.matchtracker.database.DatabaseHelper;


public class EventTable extends DBTable {
    public final String TAG = getClass().getSimpleName();

    public EventTable(Context context, SQLiteDatabase database, String tableName, String[] columns, DatabaseHelper dbHelper) {
        super(context, database, tableName, columns, dbHelper);
        //mDatabase.execSQL("DROP TABLE IF EXISTS " + mTableName);
        //mDatabase.execSQL(DatabaseHelper.SQL_CREATE_TABLE_EVENTS);
    }

    public Event create(String name, int start, int end, String location, String organizer) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.EVENT_NAME, name);
        values.put(DatabaseHelper.EVENT_START, start);
        values.put(DatabaseHelper.EVENT_END, end);
        values.put(DatabaseHelper.EVENT_LOCATION, location);
        values.put(DatabaseHelper.EVENT_ORGANIZER, organizer);

        long insertId =  mDatabase.insert(mTableName, null, values);

        return new Event(insertId, name, start, end, location, organizer);
    }

    public Event read(long id) {
        Event event = new Event();
        Cursor cursor = mDatabase.query(mTableName, mAllColumns, DatabaseHelper.EVENT_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            event.setId(id);
            event.setName(cursor.getString(cursor.getColumnIndex("name")));
            event.setStartTime(cursor.getInt(cursor.getColumnIndex("start")));
            event.setEndTime(cursor.getInt(cursor.getColumnIndex("end")));
            event.setLocation(cursor.getString(cursor.getColumnIndex("location")));
            event.setOrganizer(cursor.getString(cursor.getColumnIndex("organizer")));

            cursor.close();
        }

        return event;
    }

    public Event read(String name) {
        Event event = new Event();
        Cursor cursor = mDatabase.query(mTableName, mAllColumns,
                DatabaseHelper.EVENT_NAME + " = ?", new String[]{name}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            event.setId(cursor.getInt(cursor.getColumnIndex("id")));
            event.setName(name);
            event.setStartTime(cursor.getInt(cursor.getColumnIndex("start")));
            event.setEndTime(cursor.getInt(cursor.getColumnIndex("end")));
            event.setLocation(cursor.getString(cursor.getColumnIndex("location")));
            event.setOrganizer(cursor.getString(cursor.getColumnIndex("organizer")));

            cursor.close();
        }

        return event;
    }

    public ArrayList<Event> readAll() {
        ArrayList<Event> listEvents = new ArrayList<>();
        Cursor cursor = mDatabase.query(mTableName, mAllColumns, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Event event = new Event();

                event.setId(cursor.getInt(cursor.getColumnIndex("id")));
                event.setName(cursor.getString(cursor.getColumnIndex("name")));
                event.setStartTime(cursor.getInt(cursor.getColumnIndex("start")));
                event.setEndTime(cursor.getInt(cursor.getColumnIndex("end")));
                event.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                event.setOrganizer(cursor.getString(cursor.getColumnIndex("organizer")));

                listEvents.add(event);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listEvents;
    }
}
