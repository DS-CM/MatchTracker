package group8.matchtracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import group8.matchtracker.database.tables.EventTable;
import group8.matchtracker.database.tables.MatchTable;
import group8.matchtracker.database.tables.PlayerTable;
import group8.matchtracker.database.tables.TournamentTable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = getClass().getSimpleName();

    private static final String DATABASE_NAME = "MatchTracker.db";
    private static final int DATABASE_VERSION = 5;
    public static final String TABLE_TOURNAMENT = "tournaments";
    public static final String TABLE_EVENT = "events";
    public static final String TABLE_MATCH = "matches";
    public static final String TABLE_PLAYER = "players";
    public static final String TABLE_PLAYERS_IN_EVENT = "players_in_event";
    public static final String TABLE_PLAYERS_IN_MATCH = "players_in_match";
    public static final String TABLE_MATCHES_IN_EVENT = "matches_in_event";
    public static final String TABLE_EVENTS_IN_TOURNAMENT = "events_in_tournaments";
    public TournamentTable mTournamentTable;
    public PlayerTable mPlayerTable;
    public EventTable mEventTable;
    public MatchTable mMatchTable;
    private Context context;
    private SQLiteDatabase db;

    // Tournament
    public static final String TOURNAMENT_ID = "id";
    public static final String TOURNAMENT_NAME = "name";
    public static final String TOURNAMENT_START = "start";
    public static final String TOURNAMENT_END = "end";
    public static final String TOURNAMENT_LOCATION = "location";
    public static final String TOURNAMENT_ORGANIZER = "organizer";
    public static final String TOURNAMENT_URL = "url";

    // Event
    public static final String EVENT_ID = "id";
    public static final String EVENT_NAME = "name";

    // Match
    public static final String MATCH_ID = "id";
    public static final String MATCH_ROUND = "round";
    public static final String MATCH_IDENTIFIER = "identifier";
    public static final String MATCH_RESULT_1 = "result1";
    public static final String MATCH_RESULT_2 = "result2";
    public static final String MATCH_TYPE = "type";
    public static final String MATCH_LOCATION = "location";
    public static final String MATCH_TIME = "time";

    // Player
    public static final String PLAYER_ID = "id";
    public static final String PLAYER_NAME = "name";
    public static final String PLAYER_IGN = "ign";

    // p_i_e


    // p_i_m


    // m_i_e


    // e_i_t

    public static final String SQL_CREATE_TABLE_TOURNAMENTS = "CREATE TABLE "+TABLE_TOURNAMENT+" ("
            +TOURNAMENT_ID+" INTEGER PRIMARY KEY ASC, "
            +TOURNAMENT_NAME+" STRING, "
            +TOURNAMENT_START+" INTEGER, "
            +TOURNAMENT_END+" INTEGER, "
            +TOURNAMENT_LOCATION+" STRING, "
            +TOURNAMENT_ORGANIZER+" STRING, "
            +TOURNAMENT_URL+" STRING)";

    private static final String SQL_CREATE_TABLE_PLAYERS = "CREATE TABLE "+TABLE_PLAYER+" ("
            +PLAYER_ID+" INTEGER PRIMARY KEY ASC NOT NULL, "
            +PLAYER_NAME+" STRING, "
            +PLAYER_IGN+" STRING)";

    public static final String SQL_CREATE_TABLE_MATCHES = "CREATE TABLE "+TABLE_MATCH+" ("
            +MATCH_ID+" INTEGER PRIMARY KEY ASC, "
            +MATCH_ROUND+" INTEGER, "
            +MATCH_IDENTIFIER+" STRING, "
            +MATCH_RESULT_1+" INTEGER, "
            +MATCH_RESULT_2+" INTEGER, "
            +MATCH_TYPE+" STRING, "
            +MATCH_LOCATION+" STRING, "
            +MATCH_TIME+ " STRING)";

    private static final String SQL_CREATE_TABLE_EVENTS = "CREATE TABLE "+TABLE_EVENT+" ("
            +EVENT_ID+" INTEGER PRIMARY KEY ASC, "
            +EVENT_NAME+" STRING)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mTournamentTable = new TournamentTable(context, this);
        mPlayerTable = new PlayerTable(context, this);
        mEventTable = new EventTable(context, this);
        mMatchTable = new MatchTable(context, this);
        /*this.context = context;
        MatchTrackerOpenHelper openHelper = new MatchTrackerOpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);*/
    }

    public void deleteAll(){
        this.db.delete(TABLE_TOURNAMENT, null, null);
        this.db.delete(TABLE_EVENT, null, null);
        this.db.delete(TABLE_MATCH, null, null);
        this.db.delete(TABLE_PLAYER, null, null);
/*        this.db.delete(TABLE_PLAYERS_IN_EVENT, null, null);
        this.db.delete(TABLE_PLAYERS_IN_MATCH, null, null);
        this.db.delete(TABLE_MATCHES_IN_EVENT, null, null);
        this.db.delete(TABLE_EVENTS_IN_TOURNAMENT, null, null);*/
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TOURNAMENTS);
        db.execSQL(SQL_CREATE_TABLE_PLAYERS);
        db.execSQL(SQL_CREATE_TABLE_MATCHES);
        db.execSQL(SQL_CREATE_TABLE_EVENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,"Upgrading the database from version " + oldVersion + " to "+ newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TOURNAMENT);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MATCH);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLAYER);

        db.execSQL(SQL_CREATE_TABLE_TOURNAMENTS);
        db.execSQL(SQL_CREATE_TABLE_PLAYERS);
        db.execSQL(SQL_CREATE_TABLE_MATCHES);
        db.execSQL(SQL_CREATE_TABLE_EVENTS);
    }

/*    private static class MatchTrackerOpenHelper extends SQLiteOpenHelper{
        MatchTrackerOpenHelper(Context context){
            super(context,DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE "+TABLE_TOURNAMENT+"("+TOURNAMENT_ID+" INTEGER PRIMARY KEY, "+TOURNAMENT_NAME+" TEXT, "+TOURNAMENT_START+" LONG, "+TOURNAMENT_END+" LONG, "+TOURNAMENT_LOCATION+" TEXT, "+TOURNAMENT_ORGANIZER+" TEXT)");
            db.execSQL("CREATE TABLE "+TABLE_EVENT+"(eid INTEGER PRIMARY KEY, name TEXT)");
            db.execSQL("CREATE TABLE "+TABLE_MATCH+"(mid INTEGER PRIMARY KEY, round TEXT, identifier TEXT, result TEXT)");
            db.execSQL("CREATE TABLE "+TABLE_PLAYER+"(pid INTEGER PRIMARY KEY, name TEXT, ign TEXT)");
            db.execSQL("CREATE TABLE "+TABLE_PLAYERS_IN_EVENT+"(pid INTEGER FOREIGN KEY, eid INTEGER FOREIGN KEY)");
            db.execSQL("CREATE TABLE "+TABLE_PLAYERS_IN_MATCH+"(pid INTEGER FOREIGN KEY, mid INTEGER FOREIGN KEY)");
            db.execSQL("CREATE TABLE "+TABLE_EVENTS_IN_TOURNAMENT+"(eid INTEGER FOREIGN KEY, tid INTEGER FOREIGN KEY)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w("Example", "Upgrading database; this drops & recreates tables.");
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_TOURNAMENT);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENT);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_MATCH);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLAYER);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLAYERS_IN_EVENT);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLAYERS_IN_MATCH);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENTS_IN_TOURNAMENT);
        }
    }*/
}