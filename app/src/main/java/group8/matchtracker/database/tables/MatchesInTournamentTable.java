package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import group8.matchtracker.database.DatabaseHelper;


public class MatchesInTournamentTable extends DBTable {

    private final String[] mAllColumns = {DatabaseHelper.MIT_TOURNAMENT_ID, DatabaseHelper.MIT_MATCH_ID};

    public MatchesInTournamentTable(Context context, SQLiteDatabase database) {
        super(context, database);
    }

    public void createMIT(long tid, long mid) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MIT_TOURNAMENT_ID, tid);
        values.put(DatabaseHelper.MIT_MATCH_ID, mid);

        mDatabase.insert(DatabaseHelper.TABLE_MATCHES_IN_TOURNAMENT, null, values);
    }

    public void deleteAll() {
        mDatabase.execSQL("delete from " + DatabaseHelper.TABLE_MATCHES_IN_TOURNAMENT);
    }
}
