package group8.matchtracker.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by dsoll on 2/19/2016.
 */
public class Tournament {
    private final String TAG = getClass().toString();

    private long id;
    private int challongeId;
    private String name;
    private String url;
    private List<Player> players;
    private List<Match> matches;

    public Tournament() {
        // Empty :)
    }

    public Tournament(long id, int challongeId, String name, String url) {
        this.id = id;
        this.challongeId = challongeId;
        this.name = name;
        this.url = url;
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

    public void setChallongeId(int challongeId) {
        this.challongeId = challongeId;
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
    public long getId() {
        return id;
    }

    public int getChallongeId() {
        return challongeId;
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

    @Override
    public boolean equals(Object o) {
        Tournament t = (Tournament) o;
        return (id == t.getId()
                && challongeId == t.getChallongeId()
                && name.equals(t.getName())
                && url.equals(t.getUrl()));
    }
}
