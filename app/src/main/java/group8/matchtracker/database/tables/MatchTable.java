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

    public MatchTable(Context context, SQLiteDatabase database, String tableName, String[] columns, DatabaseHelper dbHelper) {
        super(context, database, tableName, columns, dbHelper);
    }

    public Match create(int challongeId, int round, String identifier, int[] result, String type, String location, String time, int[] pids) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MATCH_CHALLONGE_ID, challongeId);
        values.put(DatabaseHelper.MATCH_ROUND, round);
        values.put(DatabaseHelper.MATCH_IDENTIFIER, identifier);
        values.put(DatabaseHelper.MATCH_RESULT_1, result[0]);
        values.put(DatabaseHelper.MATCH_RESULT_2, result[1]);
        values.put(DatabaseHelper.MATCH_TYPE, type);
        values.put(DatabaseHelper.MATCH_LOCATION, location);
        values.put(DatabaseHelper.MATCH_TIME, time);
        values.put(DatabaseHelper.MATCH_PLAYER_1, pids[0]);
        values.put(DatabaseHelper.MATCH_PLAYER_2, pids[1]);


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
            int[] pids = new int[]{cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_PLAYER_1)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_PLAYER_2))};
            long eventId = 0;

            ArrayList<Player> players = new ArrayList<>();
            for(int i=0; i<pids.length; i++) {
                Cursor pCursor = mDatabase.query(DatabaseHelper.TABLE_PLAYER, DatabaseHelper.TABLE_PLAYER_COLUMNS,
                        DatabaseHelper.PLAYER_CHALLONGE_ID + " = ?", new String[]{String.valueOf(pids[i])}, null, null, null);
                if(pCursor != null){
                    pCursor.moveToFirst();
                    players.add(new Player(pCursor.getLong(pCursor.getColumnIndex(DatabaseHelper.PLAYER_ID)),
                            pCursor.getInt(pCursor.getColumnIndex(DatabaseHelper.PLAYER_CHALLONGE_ID)),
                            pCursor.getString(pCursor.getColumnIndex(DatabaseHelper.PLAYER_NAME)),
                            pCursor.getString(pCursor.getColumnIndex(DatabaseHelper.PLAYER_IGN))));
                }
                pCursor.close();
            }

            match = new Match(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.MATCH_ID)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_CHALLONGE_ID)),
                    eventId,
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_ROUND)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_IDENTIFIER)),
                    result,
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_TYPE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_LOCATION)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_TIME)),
                    players);
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
                int[] pids = new int[]{cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_PLAYER_1)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_PLAYER_2))};
                long id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_ID));
                int challongeId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_CHALLONGE_ID));
                int round = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_ROUND));
                String identifier = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_IDENTIFIER));
                result[0] = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_RESULT_1));
                result[1] = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MATCH_RESULT_2));
                String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_TYPE));
                String location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_LOCATION));
                String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MATCH_TIME));

                ArrayList<Player> players = new ArrayList<>();
                for(int i=0; i<pids.length; i++) {
                    Cursor pCursor = mDatabase.query(DatabaseHelper.TABLE_PLAYER, DatabaseHelper.TABLE_PLAYER_COLUMNS,
                            DatabaseHelper.PLAYER_CHALLONGE_ID + " = ?", new String[]{String.valueOf(pids[i])}, null, null, null);
                    if(pCursor != null){
                        pCursor.moveToFirst();
                        if(pCursor.getCount() != 0) {
                            players.add(new Player(pCursor.getLong(pCursor.getColumnIndex(DatabaseHelper.PLAYER_ID)),
                                    pCursor.getInt(pCursor.getColumnIndex(DatabaseHelper.PLAYER_CHALLONGE_ID)),
                                    pCursor.getString(pCursor.getColumnIndex(DatabaseHelper.PLAYER_NAME)),
                                    pCursor.getString(pCursor.getColumnIndex(DatabaseHelper.PLAYER_IGN))));
                        }
                    }
                    pCursor.close();
                }
                if(players.size() == 0){
                    players.add(new Player(150, 123456, "dummyPlayer1", "dummyPlayer1"));
                    players.add(new Player(151, 123457, "dummyPlayer2", "dummyPlayer2"));
                }
                listMatches.add(new Match(id, challongeId, eventId, round, identifier, result, type, location, time, players));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listMatches;
    }
}
