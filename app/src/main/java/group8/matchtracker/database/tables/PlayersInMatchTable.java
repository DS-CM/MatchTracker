package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import group8.matchtracker.database.DatabaseHelper;


public class PlayersInMatchTable extends DBTable {
    public PlayersInMatchTable(Context context, SQLiteDatabase database, String tableName, String[] columns, DatabaseHelper dbHelper) {
        super(context, database, tableName, columns, dbHelper);
    }

    public void create(long mid, long pid) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PIM_MATCH_ID, mid);
        values.put(DatabaseHelper.PIM_PLAYER_ID, pid);

        mDatabase.insert(mTableName, null, values);
    }
}
