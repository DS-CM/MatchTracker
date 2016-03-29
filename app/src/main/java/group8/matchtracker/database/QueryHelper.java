package group8.matchtracker.database;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import group8.matchtracker.data.Match;

public class QueryHelper {
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;

    public QueryHelper(SQLiteDatabase database, DatabaseHelper dbHelper){
        mDatabase = database;
        mDbHelper = dbHelper;
    }

    public ArrayList<Match> readMatchesOfPlayer(long pid){
        ArrayList<Match> matches = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_PLAYERS_IN_MATCH,
                DatabaseHelper.TABLE_PLAYERS_IN_MATCH_COLUMNS,
                DatabaseHelper.PIM_PLAYER_ID + " = ?",
                new String[]{String.valueOf(pid)}, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
            while(!(cursor.isAfterLast())){
                matches.add(mDbHelper.mMatchTable.read(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.MIT_MATCH_ID))));
                cursor.moveToNext();
            }
            cursor.close();
        }

        return matches;
    }

}
