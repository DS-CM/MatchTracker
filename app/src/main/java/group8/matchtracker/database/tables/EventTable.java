package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import group8.matchtracker.data.Event;
import group8.matchtracker.database.DatabaseHelper;


public class EventTable extends DBTable {

    private String[] mAllColumns = {mDbHelper.EVENT_ID, mDbHelper.EVENT_NAME};

    public EventTable(Context context, DatabaseHelper dbHelper){
        super(context, dbHelper);

    }

    public Event createEvent(int id, String name){
        ContentValues values = new ContentValues();
        values.put(mDbHelper.EVENT_ID, id);
        values.put(mDbHelper.EVENT_NAME, name);

        long insertId = mDatabase.insert(mDbHelper.TABLE_EVENT, null, values);
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_EVENT, mAllColumns, mDbHelper.EVENT_ID
                +" = "+insertId, null, null, null, null);
        cursor.moveToFirst();
        Event newEvent = new Event(cursor);
        cursor.close();

        return newEvent;
    }

    public ArrayList<Event> getAllEvents(){
        ArrayList<Event> listEvents = new ArrayList<>();
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_EVENT, mAllColumns, null,null,null,null,null);

        if(cursor != null){
            while(!cursor.isAfterLast()){
                listEvents.add(new Event(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listEvents;
    }
}
