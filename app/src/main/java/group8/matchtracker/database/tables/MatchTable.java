package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import group8.matchtracker.data.Match;
import group8.matchtracker.database.DatabaseHelper;


public class MatchTable extends DBTable {

    public MatchTable(Context context, SQLiteDatabase database, String tableName, String[] columns, DatabaseHelper dbHelper) {
        super(context, database, tableName, columns, dbHelper);
    }

    public Match create(int challongeId, int round, String identifier, int[] result, String type, String location, String time) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MATCH_CHALLONGE_ID, challongeId);
        values.put(DatabaseHelper.MATCH_ROUND, round);
        values.put(DatabaseHelper.MATCH_IDENTIFIER, identifier);
        values.put(DatabaseHelper.MATCH_RESULT_1, result[0]);
        values.put(DatabaseHelper.MATCH_RESULT_2, result[1]);
        values.put(DatabaseHelper.MATCH_TYPE, type);
        values.put(DatabaseHelper.MATCH_LOCATION, location);
        values.put(DatabaseHelper.MATCH_TIME, time);

        long insertId = mDatabase.insert(mTableName, null, values);

        // TODO - Fill players and eventID
        long eventId = 0;

        return new Match(insertId, challongeId, eventId, round, identifier, result, type, location, time);
    }

    public Match read(long id){
        Match match = new Match();
        Cursor cursor = mDatabase.query(mTableName, mAllColumns, DatabaseHelper.MATCH_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
            int[] result = new int[]{cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_RESULT_1)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_RESULT_2))};

            long eventId = 0;

            match = new Match(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.MATCH_ID)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_CHALLONGE_ID)),
                    eventId,
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_ROUND)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_IDENTIFIER)),
                    result,
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_TYPE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_LOCATION)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_TIME)));
        }
        cursor.close();
        return match;
    }

    public ArrayList<Match> readAll() {
        ArrayList<Match> listMatches = new ArrayList<>();
        Cursor cursor = mDatabase.query(mTableName, mAllColumns, null, null, null, null, null);

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

                listMatches.add(new Match(id, challongeId, eventId, round, identifier, result, type, location, time));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listMatches;
    }
}
