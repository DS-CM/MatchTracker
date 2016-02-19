package group8.matchtracker.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsoll on 2/19/2016.
 */
public class Event {
    private String name;
    private List<Player> players;
    private List<Match> matches;

    public Event(String name) {
        this.name = name;
        this.players = new ArrayList<>();
        this.matches = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void addMatch(Match match) {
        this.matches.add(match);
    }

    /* GETTERS */
    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Match> getMatches() {
        return matches;
    }
}
