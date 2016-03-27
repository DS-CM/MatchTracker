package group8.matchtracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import group8.matchtracker.database.tables.MatchesInTournamentTable;
import group8.matchtracker.database.tables.TournamentInEventTable;
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
    private Context context;
    private SQLiteDatabase db;

    // Public grabbers
    public final EventTable mEventTable;
    public final PlayerTable mPlayerTable;
    public final TournamentTable mTournamentTable;
    public final MatchTable mMatchTable;
    public final MatchesInTournamentTable mMatchesInTournamentTable;
    public final TournamentInEventTable mTournamentInEventTable;

    // Event
    public static final String EVENT_ID = "id";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_START = "start";
    public static final String EVENT_END = "end";
    public static final String EVENT_LOCATION = "location";
    public static final String EVENT_ORGANIZER = "organizer";

    // Tournament
    public static final String TOURNAMENT_ID = "id";
    public static final String TOURNAMENT_CHALLONGE_ID = "challongeId";
    public static final String TOURNAMENT_NAME = "name";
    public static final String TOURNAMENT_URL = "url";

    // Match
    public static final String MATCH_ID = "id";
    public static final String MATCH_CHALLONGE_ID = "challongeId";
    public static final String MATCH_ROUND = "round";
    public static final String MATCH_IDENTIFIER = "identifier";
    public static final String MATCH_RESULT_1 = "result1";
    public static final String MATCH_RESULT_2 = "result2";
    public static final String MATCH_TYPE = "type";
    public static final String MATCH_LOCATION = "location";
    public static final String MATCH_TIME = "time";

    // Player
    public static final String PLAYER_ID = "id";
    public static final String PLAYER_CHALLONGE_ID = "challongeId";
    public static final String PLAYER_NAME = "name";
    public static final String PLAYER_IGN = "ign";

    // p_i_t


    // p_i_m


    // m_i_t
    public static final String MIT_TOURNAMENT_ID = "tid";
    public static final String MIT_MATCH_ID = "mid";

    // t_i_e
    public static final String TIE_EVENT_ID = "eid";
    public static final String TIE_TOURNAMENT_ID = "tid";


    // Create Strings
    public static final String SQL_CREATE_TABLE_EVENTS = "CREATE TABLE " + TABLE_EVENT + " ("
            + EVENT_ID + " INTEGER PRIMARY KEY ASC, "
            + EVENT_NAME + " STRING, "
            + EVENT_START + " INTEGER, "
            + EVENT_END + " INTEGER, "
            + EVENT_LOCATION + " STRING, "
            + EVENT_ORGANIZER + " STRING)";

    private static final String SQL_CREATE_TABLE_TOURNAMENTS = "CREATE TABLE " + TABLE_TOURNAMENT + " ("
            + TOURNAMENT_ID + " INTEGER PRIMARY KEY ASC, "
            + TOURNAMENT_CHALLONGE_ID + " INTEGER NOT NULL, "
            + TOURNAMENT_NAME + " STRING, "
            + TOURNAMENT_URL + " STRING)";

    public static final String SQL_CREATE_TABLE_MATCHES = "CREATE TABLE " + TABLE_MATCH + " ("
            + MATCH_ID + " INTEGER PRIMARY KEY ASC, "
            + MATCH_CHALLONGE_ID + " INTEGER NOT NULL, "
            + MATCH_ROUND + " INTEGER, "
            + MATCH_IDENTIFIER + " STRING, "
            + MATCH_RESULT_1 + " INTEGER, "
            + MATCH_RESULT_2 + " INTEGER, "
            + MATCH_TYPE + " STRING, "
            + MATCH_LOCATION + " STRING, "
            + MATCH_TIME + " STRING)";

    private static final String SQL_CREATE_TABLE_PLAYERS = "CREATE TABLE " + TABLE_PLAYER + " ("
            + PLAYER_ID + " INTEGER PRIMARY KEY ASC NOT NULL, "
            + PLAYER_CHALLONGE_ID + " INTEGER NOT NULL, "
            + PLAYER_NAME + " STRING, "
            + PLAYER_IGN + " STRING)";


    private static final String SQL_CREATE_TABLE_MATCHS_IN_TOURNAMENT = "CREATE Table " + TABLE_MATCHES_IN_TOURNAMENT + " ("
            + MIT_TOURNAMENT_ID + " INTEGER NOT NULL, "
            + MIT_MATCH_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + MIT_TOURNAMENT_ID + ") REFERENCES " + TABLE_TOURNAMENT + "(" + TOURNAMENT_ID + "), "
            + "FOREIGN KEY (" + MIT_MATCH_ID + ") REFERENCES " + TABLE_MATCH + "(" + MATCH_ID + "), "
            + "PRIMARY KEY (" + MIT_TOURNAMENT_ID + ", " + MIT_MATCH_ID + "))";

    private static final String SQL_CREATE_TABLE_TOURNAMENT_IN_EVENT = "CREATE Table " + TABLE_TOURNAMENT_IN_EVENT + " ("
            + TIE_EVENT_ID + " INTEGER NOT NULL, "
            + TIE_TOURNAMENT_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + TIE_EVENT_ID + ") REFERENCES " + TABLE_EVENT + "(" + EVENT_ID + "), "
            + "FOREIGN KEY (" + TIE_TOURNAMENT_ID + ") REFERENCES " + TABLE_TOURNAMENT + "(" + TOURNAMENT_ID + "), "
            + "PRIMARY KEY (" + TIE_EVENT_ID + ", " + TIE_TOURNAMENT_ID + "))";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mEventTable = new EventTable(context, this);
        mPlayerTable = new PlayerTable(context, this);
        mTournamentTable = new TournamentTable(context, this);
        mMatchTable = new MatchTable(context, this);
        mMatchesInTournamentTable = new MatchesInTournamentTable(context, this);
        mTournamentInEventTable = new TournamentInEventTable(context, this);
        /*this.context = context;
        MatchTrackerOpenHelper openHelper = new MatchTrackerOpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);*/
    }

    public void deleteAll() {
        db.delete(TABLE_EVENT, null, null);
        db.delete(TABLE_TOURNAMENT, null, null);
        db.delete(TABLE_MATCH, null, null);
        db.delete(TABLE_PLAYER, null, null);
        db.delete(TABLE_MATCHES_IN_TOURNAMENT, null, null);
        db.delete(TABLE_TOURNAMENT_IN_EVENT, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_EVENTS);
        db.execSQL(SQL_CREATE_TABLE_PLAYERS);
        db.execSQL(SQL_CREATE_TABLE_MATCHES);
        db.execSQL(SQL_CREATE_TABLE_TOURNAMENTS);
        db.execSQL(SQL_CREATE_TABLE_MATCHS_IN_TOURNAMENT);
        db.execSQL(SQL_CREATE_TABLE_TOURNAMENT_IN_EVENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading the database from version " + oldVersion + " to " + newVersion);

        // TODO (David): Better update method with data retention
        deleteAll();
        onCreate(db);
    }
}