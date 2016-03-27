package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;

import group8.matchtracker.database.DatabaseHelper;


public class TournamentInEventTable extends DBTable {

    private final String[] mAllColumns = {DatabaseHelper.TIE_EVENT_ID, DatabaseHelper.TIE_TOURNAMENT_ID};

    public TournamentInEventTable(Context context, DatabaseHelper dbHelper) {
        super(context, dbHelper);
    }

    public void createTIE(long eid, long tid) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TIE_EVENT_ID, eid);
        values.put(DatabaseHelper.TIE_TOURNAMENT_ID, tid);

        mDatabase.insert(DatabaseHelper.TABLE_TOURNAMENT_IN_EVENT, null, values);
    }

    public void deleteAll() {
        mDatabase.execSQL("delete from " + DatabaseHelper.TABLE_TOURNAMENT_IN_EVENT);
    }
}
