package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import group8.matchtracker.database.DatabaseHelper;


public class MatchesInTournamentTable extends DBTable {

    public MatchesInTournamentTable(Context context, SQLiteDatabase database, String tableName, String[] columns) {
        super(context, database, tableName, columns);
    }

    public void createMIT(long tid, long mid) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MIT_TOURNAMENT_ID, tid);
        values.put(DatabaseHelper.MIT_MATCH_ID, mid);

        mDatabase.insert(DatabaseHelper.TABLE_MATCHES_IN_TOURNAMENT, null, values);
    }
}
