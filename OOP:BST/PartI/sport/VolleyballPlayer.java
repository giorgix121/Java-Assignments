package sport;

public class VolleyballPlayer extends BallSportsPlayer {
    private int maxPoints;

    public VolleyballPlayer(int weight, Gender gender, int maxPoints) {
        super(weight, gender);
        this.maxPoints = maxPoints;
    }

    // Overrides toString method to include maximum points in the player's description
    @Override
    public String toString() {
        return super.toString() + " Max Points: " + maxPoints;
    }

    // Overrides equals method to compare volleyball players based on superclass attributes and maximum points
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof VolleyballPlayer)) return false;
        VolleyballPlayer other = (VolleyballPlayer) obj;
        return maxPoints == other.maxPoints;
    }

    public int getMaxPoints() { return maxPoints; }
    public void setMaxPoints(int maxPoints) { this.maxPoints = maxPoints; }
}
