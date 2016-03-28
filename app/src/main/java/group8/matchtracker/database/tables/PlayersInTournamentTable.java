package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import group8.matchtracker.database.DatabaseHelper;


public class PlayersInTournamentTable extends DBTable {
    public PlayersInTournamentTable(Context context, SQLiteDatabase database, String tableName, String[] columns, DatabaseHelper dbHelper) {
        super(context, database, tableName, columns, dbHelper);
    }

    public void create(long tid, long pid) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PIT_TOURNAMENT_ID, tid);
        values.put(DatabaseHelper.PIT_PLAYER_ID, pid);

        mDatabase.insert(mTableName, null, values);
    }
}
