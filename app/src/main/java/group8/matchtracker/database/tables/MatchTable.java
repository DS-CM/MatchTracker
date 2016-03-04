package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.lang.reflect.Array;
import java.util.ArrayList;

import group8.matchtracker.data.Match;
import group8.matchtracker.database.DatabaseHelper;


public class MatchTable  extends DBTable {

    private String[] mAllColumns = {mDbHelper.MATCH_ID, mDbHelper.MATCH_ROUND,
            mDbHelper.MATCH_IDENTIFIER, mDbHelper.MATCH_RESULT};

    public MatchTable(Context context, DatabaseHelper dbHelper){
        super(context, dbHelper);

    }

    public Match createMatch(int id, int round, String identifier, String result){
        ContentValues values = new ContentValues();
        values.put(mDbHelper.MATCH_ID, id);
        values.put(mDbHelper.MATCH_ROUND, round);
        values.put(mDbHelper.MATCH_IDENTIFIER, identifier);
        values.put(mDbHelper.MATCH_RESULT, result);

        long insertId = mDatabase.insert(mDbHelper.TABLE_MATCH, null, values);
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_MATCH, mAllColumns, mDbHelper.MATCH_ID
                +" = "+ insertId, null,null,null,null);
        cursor.moveToFirst();
        Match newMatch = new Match(cursor);

        return newMatch;
    }

    public ArrayList<Match> getAllMatches(){
        ArrayList<Match> listMatches = new ArrayList<>();
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_MATCH, mAllColumns, null,null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                listMatches.add(new Match(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listMatches;
    }
}
