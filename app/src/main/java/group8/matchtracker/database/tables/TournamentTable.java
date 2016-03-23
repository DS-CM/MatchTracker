package group8.matchtracker.database.tables;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import group8.matchtracker.data.Tournament;
import group8.matchtracker.database.DatabaseHelper;


public class TournamentTable extends DBTable {
    public final String TAG = getClass().getSimpleName();
    public int entries = 0;

    private String[] mAllColumns = {mDbHelper.TOURNAMENT_ID, mDbHelper.TOURNAMENT_NAME,
            mDbHelper.TOURNAMENT_START, mDbHelper.TOURNAMENT_END, mDbHelper.TOURNAMENT_LOCATION,
            mDbHelper.TOURNAMENT_ORGANIZER};

    public TournamentTable(Context context, DatabaseHelper dbHelper) {
        super(context, dbHelper);

    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Tournament createTournament(String name, int start, int end, String location, String organizer) {
        ContentValues values = new ContentValues();
        values.put(mDbHelper.TOURNAMENT_NAME, name);
        values.put(mDbHelper.TOURNAMENT_START, start);
        values.put(mDbHelper.TOURNAMENT_END, end);
        values.put(mDbHelper.TOURNAMENT_LOCATION, location);
        values.put(mDbHelper.TOURNAMENT_ORGANIZER, organizer);

        int insertId = (int) mDatabase.insert(mDbHelper.TABLE_TOURNAMENT, null, values);

        entries++;

        return new Tournament(insertId, name, start, end, location, organizer);
    }

    public Tournament getTournament(int id){
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_TOURNAMENT,mAllColumns,mDbHelper.TOURNAMENT_ID+" = ?",
                new String[]{String.valueOf(id)},null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Log.d("CursorSize", String.valueOf(cursor.getCount()));

        return new Tournament(cursor);
    }

    public ArrayList<Tournament> getAllTournaments() {
        ArrayList<Tournament> listTournaments = new ArrayList<>();
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_TOURNAMENT, mAllColumns, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                listTournaments.add(new Tournament(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listTournaments;
    }

    public Tournament getTournamentByName(String name) {
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_TOURNAMENT, mAllColumns,
                mDbHelper.TOURNAMENT_NAME + " = ?", new String[]{name}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new Tournament(cursor);
    }

    public void removeAllTournaments(){
        mDatabase.execSQL("delete from " + mDbHelper.TABLE_TOURNAMENT);
        entries = 0;
    }

}
