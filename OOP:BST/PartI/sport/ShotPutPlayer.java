package sport;

public class ShotPutPlayer extends FieldSportsPlayer {
    private int maxDistance;

    public ShotPutPlayer(int weight, Gender gender, int maxDistance) {
        super(weight, gender);
        this.maxDistance = maxDistance;
    }

    @Override
    public String toString() {
        return super.toString() + " Max Distance: " + maxDistance + " cm";
    }

    // Overrides equals method to compare shot put players based on superclass attributes and maximum throw distance
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof ShotPutPlayer)) return false;
        ShotPutPlayer other = (ShotPutPlayer) obj;
        return maxDistance == other.maxDistance;
    }

    public int getMaxDistance() { return maxDistance; }
    public void setMaxDistance(int maxDistance) { this.maxDistance = maxDistance; }
}
