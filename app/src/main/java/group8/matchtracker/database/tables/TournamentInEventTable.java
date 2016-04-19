package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public List<Long> readTIDs(long eid) {
        List<Long> tids = new ArrayList<Long>();

        Cursor cursor = mDatabase.query(mTableName, mAllColumns, DatabaseHelper.TIE_EVENT_ID
                + " = ?", new String[]{String.valueOf(eid)},null,null,null);

        if(cursor != null){
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                long tid = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TIE_TOURNAMENT_ID));
                tids.add(tid);
                cursor.moveToNext();
            }

            cursor.close();
        }

        return tids;
    }
}
