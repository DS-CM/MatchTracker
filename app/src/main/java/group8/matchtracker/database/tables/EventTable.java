package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import group8.matchtracker.data.Tournament;
import group8.matchtracker.database.DatabaseHelper;


public class EventTable extends DBTable {

    private String[] mAllColumns = {mDbHelper.EVENT_ID, mDbHelper.EVENT_NAME};

    public EventTable(Context context, DatabaseHelper dbHelper){
        super(context, dbHelper);

    }

    public Tournament createEvent(int id, String name){
        ContentValues values = new ContentValues();
        values.put(mDbHelper.EVENT_ID, id);
        values.put(mDbHelper.EVENT_NAME, name);

        long insertId = mDatabase.insert(mDbHelper.TABLE_EVENT, null, values);
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_EVENT, mAllColumns, mDbHelper.EVENT_ID
                +" = "+insertId, null, null, null, null);
        cursor.moveToFirst();
        Tournament newTournament = new Tournament(cursor);
        cursor.close();

        return newTournament;
    }

    public ArrayList<Tournament> getAllEvents(){
        ArrayList<Tournament> listTournaments = new ArrayList<>();
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_EVENT, mAllColumns, null,null,null,null,null);

        if(cursor != null){
            while(!cursor.isAfterLast()){
                listTournaments.add(new Tournament(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listTournaments;
    }
}
