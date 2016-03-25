package group8.matchtracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import group8.matchtracker.database.tables.TournamentTable;
import group8.matchtracker.database.tables.EventTable;
import group8.matchtracker.database.tables.MatchTable;
import group8.matchtracker.database.tables.PlayerTable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = getClass().getSimpleName();

    private static final String DATABASE_NAME = "MatchTracker.db";
    private static final int DATABASE_VERSION = 5;
    public static final String TABLE_EVENT = "events";
    public static final String TABLE_TOURNAMENT = "tournaments";
    public static final String TABLE_MATCH = "matches";
    public static final String TABLE_PLAYER = "players";
    public static final String TABLE_PLAYERS_IN_TOURNAMENT = "players_in_tournament";
    public static final String TABLE_PLAYERS_IN_MATCH = "players_in_match";
    public static final String TABLE_MATCHES_IN_TOURNAMENT = "matches_in_tournament";
    public static final String TABLE_TOURNAMENT_IN_EVENT = "tournaments_in_event";
    public EventTable mEventTable;
    public PlayerTable mPlayerTable;
    public TournamentTable mTournamentTable;
    public MatchTable mMatchTable;
    private Context context;
    private SQLiteDatabase db;

    // Event
    public static final String EVENT_ID = "id";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_START = "start";
    public static final String EVENT_END = "end";
    public static final String EVENT_LOCATION = "location";
    public static final String EVENT_ORGANIZER = "organizer";
    public static final String EVENT_URL = "url";

    // Tournament
    public static final String TOURNAMENT_ID = "id";
    public static final String TOURNAMENT_NAME = "name";

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

    // p_i_t


    // p_i_m


    // m_i_t


    // t_i_e


    public static final String SQL_CREATE_TABLE_EVENTS = "CREATE TABLE "+ TABLE_EVENT +" ("
            + EVENT_ID +" INTEGER PRIMARY KEY ASC, "
            + EVENT_NAME +" STRING, "
            + EVENT_START +" INTEGER, "
            + EVENT_END +" INTEGER, "
            + EVENT_LOCATION +" STRING, "
            + EVENT_ORGANIZER +" STRING, "
            + EVENT_URL +" STRING)";

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

    private static final String SQL_CREATE_TABLE_TOURNAMENTS = "CREATE TABLE "+ TABLE_TOURNAMENT +" ("
            + TOURNAMENT_ID +" INTEGER PRIMARY KEY ASC, "
            + TOURNAMENT_NAME +" STRING)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mEventTable = new EventTable(context, this);
        mPlayerTable = new PlayerTable(context, this);
        mTournamentTable = new TournamentTable(context, this);
        mMatchTable = new MatchTable(context, this);
        /*this.context = context;
        MatchTrackerOpenHelper openHelper = new MatchTrackerOpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);*/
    }

    public void deleteAll(){
        this.db.delete(TABLE_EVENT, null, null);
        this.db.delete(TABLE_TOURNAMENT, null, null);
        this.db.delete(TABLE_MATCH, null, null);
        this.db.delete(TABLE_PLAYER, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_EVENTS);
        db.execSQL(SQL_CREATE_TABLE_PLAYERS);
        db.execSQL(SQL_CREATE_TABLE_MATCHES);
        db.execSQL(SQL_CREATE_TABLE_TOURNAMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,"Upgrading the database from version " + oldVersion + " to "+ newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TOURNAMENT);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MATCH);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLAYER);

        db.execSQL(SQL_CREATE_TABLE_EVENTS);
        db.execSQL(SQL_CREATE_TABLE_PLAYERS);
        db.execSQL(SQL_CREATE_TABLE_MATCHES);
        db.execSQL(SQL_CREATE_TABLE_TOURNAMENTS);
    }
}