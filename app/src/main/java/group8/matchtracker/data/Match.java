package group8.matchtracker.data;

import java.util.ArrayList;

/**
 * Created by dsoll on 2/19/2016.
 */
public class Match {
    private final String TAG = getClass().toString();
    private final static String VS = " vs ";
    private final static String AND = " & ";

    private long id;
    private int challongeId;
    private long eventId;
    private int round;
    private String identifier;
    private int[] result = new int[2]; // Fix the data type
    private String type;
    private String location;
    private String time;
    private ArrayList<Player> players;


    public Match() {
        // Empty :)
    }

    public Match(long id, int challongeId, long eventId, int round, String identifier, int[] result, String type, String location, String time) {
        this.id = id;
        this.challongeId = challongeId;
        this.eventId = eventId;
        this.round = round;
        this.identifier = identifier;
        this.result = result; // int[2] - with score 1, score 2
        this.type = type;
        this.location = location;
        this.time = time;

        this.players = new ArrayList<>();
    }

    public Match(long id, int challongeId, long eventId, int round, String identifier, int[] result, String type, String location, String time, ArrayList<Player> players) {
        this.id = id;
        this.challongeId = challongeId;
        this.eventId = eventId;
        this.round = round;
        this.identifier = identifier;
        this.result = result; // int[2] - with score 1, score 2
        this.type = type;
        this.location = location;
        this.time = time;

        int size = players.size();
        if (size == 2 || size == 4) {
            this.players = players;
        } else {
            throw new RuntimeException("Error - Match not created with proper player numbers (2 or 4)");
        }
    }

    public String getVersingPlayers() {
        StringBuilder verses = new StringBuilder();

        // TODO - David - Fix so don't have to have check
        if (players == null) {
            return "ERROR";
        }

        if (players.size() == 2) {
            verses.append(players.get(0).getIgn());
            verses.append(VS);
            verses.append(players.get(1).getIgn());
        } else if (players.size() == 4) { // 4 players
            verses.append(players.get(0).getIgn());
            verses.append(AND);
            verses.append(players.get(1).getIgn());
            verses.append(VS);
            verses.append(players.get(2).getIgn());
            verses.append(AND);
            verses.append(players.get(3).getIgn());
        }

        return verses.toString();
    }

    /* SETTERS */
    public void setId(long id) {
        this.id = id;
    }

    public void setChallongeId(int challongeId) {
        this.challongeId = challongeId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setResult(int[] result) {
        this.result = result;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /* GETTERS */
    public long getId() {
        return id;
    }

    public int getChallongeId() {
        return challongeId;
    }

    public int getRound() {
        return round;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int[] getResult() {
        return result;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
