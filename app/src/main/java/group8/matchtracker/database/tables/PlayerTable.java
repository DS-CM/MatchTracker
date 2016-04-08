package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import group8.matchtracker.data.Player;
import group8.matchtracker.database.DatabaseHelper;


public class PlayerTable extends DBTable {

    public PlayerTable(Context context, SQLiteDatabase database, String tableName, String[] columns, DatabaseHelper dbHelper){
        super(context, database, tableName, columns, dbHelper);
    }

    public Player create(int challongeId, String name, String ign){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PLAYER_CHALLONGE_ID, challongeId);
        values.put(DatabaseHelper.PLAYER_NAME, name);
        values.put(DatabaseHelper.PLAYER_IGN, ign);

        long insertId = mDatabase.insert(mTableName, null, values);

        return new Player(insertId, challongeId, name, ign);
    }

    public Player read(long id){
        Player player = null;
        Cursor cursor = mDatabase.query(mTableName, mAllColumns, DatabaseHelper.PLAYER_ID
                + " = ?", new String[]{String.valueOf(id)},null,null,null);

        if(cursor != null){
            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                int challongeId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PLAYER_CHALLONGE_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAYER_NAME));
                String ign = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAYER_IGN));
                player = new Player(id, challongeId, name, ign);
            }

            cursor.close();
        }
        return player;
    }

    public Player readPlayerByChallongeId(int challongeId){
        Player player = new Player();
        Cursor cursor = mDatabase.query(mTableName, mAllColumns, DatabaseHelper.PLAYER_CHALLONGE_ID
                + " = ?", new String[]{String.valueOf(challongeId)},null,null,null);

        if(cursor != null){
            cursor.moveToFirst();

            long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.PLAYER_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAYER_NAME));
            String ign = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAYER_IGN));
            player = new Player(id, challongeId, name, ign);

            cursor.close();
        }
        return player;
    }

    public Player read(String ign){
        Cursor cursor = mDatabase.query(mTableName, mAllColumns, DatabaseHelper.PLAYER_IGN
                + " = ?", new String[]{ign},null,null,null);
        Player currentPlayer = null;

        if(cursor != null){
            cursor.moveToFirst();

            long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.PLAYER_ID));
            int challongeId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PLAYER_CHALLONGE_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAYER_NAME));
            currentPlayer = new Player(id, challongeId, name, ign);

            cursor.close();
        }
        return currentPlayer;
    }

    //needs tested
    public ArrayList<Player> readPlayers(long... ids){
        ArrayList<Player> players = new ArrayList<>();

        for(int i=0; i < ids.length; i++){
            Cursor cursor = mDatabase.query(mTableName, mAllColumns, DatabaseHelper.PLAYER_ID + " = ?",
                    new String[]{String.valueOf(ids[i])}, null, null, null);
            if(cursor != null) {
                cursor.moveToFirst();
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.PLAYER_ID));
                int challongeId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PLAYER_CHALLONGE_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAYER_NAME));
                String ign = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAYER_IGN));
                players.add(new Player(id, challongeId, name, ign));

                cursor.close();
            }
        }

        return players;
    }

    public ArrayList<Player> readAll(){
        ArrayList<Player> listPlayers = new ArrayList<>();
        Cursor cursor = mDatabase.query(mTableName, mAllColumns, null,null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.PLAYER_ID));
                int challongeId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PLAYER_CHALLONGE_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAYER_NAME));
                String ign = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAYER_IGN));
                listPlayers.add(new Player(id, challongeId, name, ign));

                cursor.moveToNext();
            }
            cursor.close();
        }
        return listPlayers;
    }

    public boolean isInTable(String ign){
        Cursor cursor = mDatabase.query(mTableName, mAllColumns, DatabaseHelper.PLAYER_IGN
                + " = ?", new String[]{ign},null,null,null);

        return cursor.getCount() > 0;
    }
}
