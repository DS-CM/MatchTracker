package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.lang.reflect.Array;
import java.util.ArrayList;

import group8.matchtracker.data.Match;
import group8.matchtracker.data.Player;
import group8.matchtracker.database.DatabaseHelper;


public class MatchTable extends DBTable {

    private String[] mAllColumns = {mDbHelper.MATCH_ID, mDbHelper.MATCH_ROUND,
            mDbHelper.MATCH_IDENTIFIER, mDbHelper.MATCH_RESULT_1, mDbHelper.MATCH_RESULT_2,
            mDbHelper.MATCH_TYPE, mDbHelper.MATCH_LOCATION, mDbHelper.MATCH_TIME};

    public MatchTable(Context context, DatabaseHelper dbHelper) {
        super(context, dbHelper);

    }

    public Match createMatch(int round, String identifier, int[] result, String type, String location, String time) {
        ContentValues values = new ContentValues();
        values.put(mDbHelper.MATCH_ROUND, round);
        values.put(mDbHelper.MATCH_IDENTIFIER, identifier);
        values.put(mDbHelper.MATCH_RESULT_1, result[0]);
        values.put(mDbHelper.MATCH_RESULT_2, result[1]);
        values.put(mDbHelper.MATCH_TYPE, type);
        values.put(mDbHelper.MATCH_LOCATION, location);
        values.put(mDbHelper.MATCH_TIME, time);

        long insertId = mDatabase.insert(mDbHelper.TABLE_MATCH, null, values);

        // TODO - Fill players and eventID
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(0, "David", "3ygun"));
        players.add(new Player(1, "Chris", "cmj"));

        return new Match(insertId, 0, round, identifier, result, type, location, time, players);
    }

    public ArrayList<Match> getAllMatches() {
        ArrayList<Match> listMatches = new ArrayList<>();
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_MATCH, mAllColumns, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                listMatches.add(new Match(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listMatches;
    }
}
