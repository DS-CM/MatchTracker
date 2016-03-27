package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;

import group8.matchtracker.database.DatabaseHelper;


public class MatchesInTournamentTable extends DBTable {

    private final String[] mAllColumns = {DatabaseHelper.MIT_TOURNAMENT_ID, DatabaseHelper.MIT_MATCH_ID};

    public MatchesInTournamentTable(Context context, DatabaseHelper dbHelper) {
        super(context, dbHelper);
    }

    public void createMIT(long tid, long mid) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MIT_TOURNAMENT_ID, tid);
        values.put(DatabaseHelper.MIT_MATCH_ID, mid);

        mDatabase.insert(DatabaseHelper.TABLE_MATCHES_IN_TOURNAMENT, null, values);
    }
}
