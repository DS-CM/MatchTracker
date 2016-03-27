package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import group8.matchtracker.database.DatabaseHelper;


public class TournamentInEventTable extends DBTable {

    public TournamentInEventTable(Context context, SQLiteDatabase database, String tableName, String[] columns, DatabaseHelper dbHelper) {
        super(context, database, tableName, columns, dbHelper);
    }

    public void create(long eid, long tid) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TIE_EVENT_ID, eid);
        values.put(DatabaseHelper.TIE_TOURNAMENT_ID, tid);

        mDatabase.insert(mTableName, null, values);
    }
}
