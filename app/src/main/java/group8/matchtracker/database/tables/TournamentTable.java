package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import group8.matchtracker.data.Tournament;
import group8.matchtracker.database.DatabaseHelper;


public class TournamentTable extends DBTable {

    private String[] mAllColumns = {mDbHelper.TOURNAMENT_ID, mDbHelper.TOURNAMENT_NAME, mDbHelper.TOURNAMENT_URL};

    public TournamentTable(Context context, DatabaseHelper dbHelper){
        super(context, dbHelper);

    }

    public Tournament createTournament(String name, String url){
        ContentValues values = new ContentValues();
        values.put(mDbHelper.TOURNAMENT_NAME, name);
        values.put(mDbHelper.TOURNAMENT_URL, url);

        long insertId = mDatabase.insert(mDbHelper.TABLE_TOURNAMENT, null, values);

        return new Tournament(insertId, name, url);
    }

    public ArrayList<Tournament> getAllTournaments(){
        ArrayList<Tournament> listTournaments = new ArrayList<>();
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_TOURNAMENT, mAllColumns, null, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                listTournaments.add(new Tournament(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listTournaments;
    }

    public Tournament getTournament(long tid) {
        Tournament t = null;
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_TOURNAMENT, mAllColumns, null,null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
            t = new Tournament(cursor);
            cursor.close();
        }

        return t;
    }

    public void deleteAll() {
        mDatabase.execSQL("delete from " + mDbHelper.TABLE_TOURNAMENT);
    }
}
