package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import group8.matchtracker.data.Match;
import group8.matchtracker.data.Player;
import group8.matchtracker.database.DatabaseHelper;


public class MatchTable extends DBTable {

    private String[] mAllColumns = {DatabaseHelper.MATCH_ID, DatabaseHelper.MATCH_CHALLONGE_ID, DatabaseHelper.MATCH_ROUND,
            DatabaseHelper.MATCH_IDENTIFIER, DatabaseHelper.MATCH_RESULT_1, DatabaseHelper.MATCH_RESULT_2,
            DatabaseHelper.MATCH_TYPE, DatabaseHelper.MATCH_LOCATION, DatabaseHelper.MATCH_TIME};

    public MatchTable(Context context, SQLiteDatabase database) {
        super(context, database);
    }

    public Match createMatch(int challongeId, int round, String identifier, int[] result, String type, String location, String time) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MATCH_CHALLONGE_ID, challongeId);
        values.put(DatabaseHelper.MATCH_ROUND, round);
        values.put(DatabaseHelper.MATCH_IDENTIFIER, identifier);
        values.put(DatabaseHelper.MATCH_RESULT_1, result[0]);
        values.put(DatabaseHelper.MATCH_RESULT_2, result[1]);
        values.put(DatabaseHelper.MATCH_TYPE, type);
        values.put(DatabaseHelper.MATCH_LOCATION, location);
        values.put(DatabaseHelper.MATCH_TIME, time);

        long insertId = mDatabase.insert(DatabaseHelper.TABLE_MATCH, null, values);

        // TODO - Fill players and eventID
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(0, 123, "David", "3ygun"));
        players.add(new Player(1, 321, "Chris", "cmj"));
        long eventId = 0;

        return new Match(insertId, challongeId, eventId, round, identifier, result, type, location, time, players);
    }

    public ArrayList<Match> getAllMatches() {
        ArrayList<Match> listMatches = new ArrayList<>();
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_MATCH, mAllColumns, null, null, null, null, null);

        // TODO - Fill players and eventID
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(0, 123, "David", "3ygun"));
        players.add(new Player(1, 321, "Chris", "cmj"));
        long eventId = 0;

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int[] result = new int[2];

                long id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_ID));
                int challongeId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_CHALLONGE_ID));
                int round = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_ROUND));
                String identifier = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_IDENTIFIER));
                result[0] = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_RESULT_1));
                result[1] = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_RESULT_2));
                String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_TYPE));
                String location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_LOCATION));
                String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_TIME));

                listMatches.add(new Match(id, challongeId, eventId, round, identifier, result, type, location, time, players));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listMatches;
    }

    public void clearTable(){
        mDatabase.execSQL("delete from " + DatabaseHelper.TABLE_MATCH);
    }
}
