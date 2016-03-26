package group8.matchtracker.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsoll on 2/19/2016.
 */
public class Tournament {
    private final String TAG = getClass().toString();

    private int id;
    private String name;
    private String url;
    private List<Player> players;
    private List<Match> matches;

    public Tournament() {
        // Empty :)
    }

    public Tournament(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.players = new ArrayList<>();
        this.matches = new ArrayList<>();
    }

    public Tournament(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.players = new ArrayList<>();
        this.matches = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void addMatch(Match match) {
        this.matches.add(match);
    }

    /* SETTERS */
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    /* GETTERS */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Match> getMatches() {
        return matches;
    }
}
