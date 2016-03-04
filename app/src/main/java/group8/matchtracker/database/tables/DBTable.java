package group8.matchtracker.database.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.SQLException;
import android.util.Log;

import group8.matchtracker.database.DatabaseHelper;

public abstract class DBTable {

    protected SQLiteDatabase mDatabase;
    protected DatabaseHelper mDbHelper;
    protected Context mContext;

    public DBTable(Context context, DatabaseHelper dbHelper) {
        this.mDbHelper = dbHelper;
        this.mContext = context;

        try {
            open();
        } catch (SQLException e) {
            Log.e("DBTable", "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }
}