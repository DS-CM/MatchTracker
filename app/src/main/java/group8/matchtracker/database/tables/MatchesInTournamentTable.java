package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;

import group8.matchtracker.data.Match;
import group8.matchtracker.database.DatabaseHelper;


public class MatchesInTournamentTable extends DBTable {

    public MatchesInTournamentTable(Context context, SQLiteDatabase database, String tableName, String[] columns, DatabaseHelper dbHelper) {
        super(context, database, tableName, columns, dbHelper);
    }

    public void create(long tid, long mid) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MIT_TOURNAMENT_ID, tid);
        values.put(DatabaseHelper.MIT_MATCH_ID, mid);

        mDatabase.insert(mTableName, null, values);
    }

    public ArrayList<Match> repopulateData(){
        ArrayList<Match> matches = new ArrayList<>();
        Cursor cursor = mDatabase.query(mTableName, mAllColumns, null, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
            long id;
            while (!(cursor.isAfterLast())) {
                id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.MIT_MATCH_ID));
                matches.add(mDbHelper.mMatchTable.read(id));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return matches;
    }
}
