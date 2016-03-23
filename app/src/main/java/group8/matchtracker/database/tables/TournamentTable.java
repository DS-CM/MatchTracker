package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import group8.matchtracker.data.Tournament;
import group8.matchtracker.database.DatabaseHelper;


public class TournamentTable extends DBTable {

    private String[] mAllColumns = {mDbHelper.TOURNAMENT_ID, mDbHelper.TOURNAMENT_NAME};

    public TournamentTable(Context context, DatabaseHelper dbHelper){
        super(context, dbHelper);

    }

    public Tournament createTournament(int id, String name){
        ContentValues values = new ContentValues();
        values.put(mDbHelper.TOURNAMENT_ID, id);
        values.put(mDbHelper.TOURNAMENT_NAME, name);

        long insertId = mDatabase.insert(mDbHelper.TABLE_TOURNAMENT, null, values);
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_TOURNAMENT, mAllColumns, mDbHelper.TOURNAMENT_ID
                +" = "+insertId, null, null, null, null);
        cursor.moveToFirst();
        Tournament newTournament = new Tournament(cursor);
        cursor.close();

        return newTournament;
    }

    public ArrayList<Tournament> getAllTournaments(){
        ArrayList<Tournament> listTournaments = new ArrayList<>();
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_TOURNAMENT, mAllColumns, null,null,null,null,null);

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
