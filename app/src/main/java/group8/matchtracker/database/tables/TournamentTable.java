package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import group8.matchtracker.data.Tournament;
import group8.matchtracker.database.DatabaseHelper;


public class TournamentTable extends DBTable {

    private String[] mAllColumns = {DatabaseHelper.TOURNAMENT_ID, DatabaseHelper.TOURNAMENT_CHALLONGE_ID, DatabaseHelper.TOURNAMENT_NAME, DatabaseHelper.TOURNAMENT_URL};

    public TournamentTable(Context context, DatabaseHelper dbHelper){
        super(context, dbHelper);
    }

    public Tournament createTournament(int challongeId, String name, String url){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TOURNAMENT_CHALLONGE_ID, challongeId);
        values.put(DatabaseHelper.TOURNAMENT_NAME, name);
        values.put(DatabaseHelper.TOURNAMENT_URL, url);

        long insertId = mDatabase.insert(DatabaseHelper.TABLE_TOURNAMENT, null, values);

        return new Tournament(insertId, challongeId, name, url);
    }

    public ArrayList<Tournament> getAllTournaments(){
        ArrayList<Tournament> listTournaments = new ArrayList<>();
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_TOURNAMENT, mAllColumns, null, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TOURNAMENT_ID));
                int challongeId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TOURNAMENT_CHALLONGE_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURNAMENT_NAME));
                String url = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURNAMENT_URL));
                listTournaments.add(new Tournament(id, challongeId, name, url));

                cursor.moveToNext();
            }
            cursor.close();
        }
        return listTournaments;
    }

    public Tournament getTournament(long id) {
        Tournament tournament = null;
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_TOURNAMENT, mAllColumns, DatabaseHelper.TOURNAMENT_ID
                + " = ?", new String[]{String.valueOf(id)},null,null,null);

        if(cursor != null){
            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                int challongeId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TOURNAMENT_CHALLONGE_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURNAMENT_NAME));
                String url = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURNAMENT_URL));
                tournament = new Tournament(id, challongeId, name, url);
            }
            cursor.close();
        }

        return tournament;
    }

    public void deleteAll() {
        mDatabase.execSQL("delete from " + DatabaseHelper.TABLE_TOURNAMENT);
    }
}
