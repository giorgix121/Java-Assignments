package sport;

public class BaseballPlayer extends BallSportsPlayer {
    private int rbi;

    // Constructor initializing BaseballPlayer with weight, gender, and RBI
    public BaseballPlayer(int weight, Gender gender, int rbi) {
        super(weight, gender);
        this.rbi = rbi;
    }

    // Overrides the toString method to include RBI in the player's description
    @Override
    public String toString() {
        return super.toString() + " RBI: " + rbi;
    }

    // Overrides equals method to compare baseball players based on superclass attributes and RBI
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof BaseballPlayer)) return false;
        BaseballPlayer other = (BaseballPlayer) obj;
        return rbi == other.rbi;
    }

    public int getRbi() { return rbi; }
    public void setRbi(int rbi) { this.rbi = rbi; }
}
