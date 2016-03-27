package group8.matchtracker.database.tables;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import group8.matchtracker.database.DatabaseHelper;

public abstract class DBTable {
    protected Context mContext;
    protected SQLiteDatabase mDatabase;
    protected String mTableName;
    protected String[] mAllColumns;
    protected DatabaseHelper mDbHelper;

    public DBTable(Context context, SQLiteDatabase database, String tableName, String[] columns, DatabaseHelper dbHelper) {
        this.mContext = context;
        this.mDatabase = database;
        this.mTableName = tableName;
        this.mAllColumns = columns;
        this.mDbHelper = dbHelper;
    }

    public void deleteAll() {
        mDatabase.execSQL("delete from " + mTableName);
    }

    public boolean hasData(){
        Cursor cursor = mDatabase.query(mTableName, mAllColumns, null, null, null, null, null);
        return cursor.getCount() > 0;
    }
}