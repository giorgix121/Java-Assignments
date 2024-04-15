package sport;

public class TrackPlayer extends RunningSportsPlayer {
    private int distance;

    public TrackPlayer(int weight, Gender gender, int distance) {
        super(weight, gender);
        this.distance = distance;
    }

    @Override
    public String toString() {
        return super.toString() + " Distance: " + distance + " meters";
    }

    // Overrides equals method to compare track players based on superclass attributes and running distance
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof TrackPlayer)) return false;
        TrackPlayer other = (TrackPlayer) obj;
        return distance == other.distance;
    }

    public int getDistance() { return distance; }
    public void setDistance(int distance) { this.distance = distance; }
}
