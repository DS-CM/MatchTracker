package group8.matchtracker.database.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DBTable {
    protected Context mContext;
    protected SQLiteDatabase mDatabase;
    protected String mTableName;
    protected String[] mAllColumns;

    public DBTable(Context context, SQLiteDatabase database, String tableName, String[] columns) {
        this.mContext = context;
        this.mDatabase = database;
        this.mTableName = tableName;
        this.mAllColumns = columns;
    }

    public void deleteAll() {
        mDatabase.execSQL("delete from " + mTableName);
    }
}