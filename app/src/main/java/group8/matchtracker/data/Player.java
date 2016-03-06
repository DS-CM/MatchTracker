package group8.matchtracker.data;

/**
 * Created by dsoll on 2/19/2016.
 */
public class Player {
    private final String TAG = getClass().toString();

    private long id;
    private String name;
    private String ign; // In Game Name

    public Player() {
        // Empty :)
    }

    public Player(long id, String name, String ign) {
        this.id = id;
        this.name = name;
        this.ign = ign;
    }

    /* SETTERS */
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIgn(String ign) {
        this.ign = ign;
    }

    /* GETTERS */
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIgn() {
        return ign;
    }
}
