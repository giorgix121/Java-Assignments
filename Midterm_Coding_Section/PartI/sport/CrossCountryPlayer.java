package sport;

public class CrossCountryPlayer extends RunningSportsPlayer {
    private double bestMileTime;

    
    // Constructor initializing CrossCountryPlayer with weight, gender, and best mile time
    public CrossCountryPlayer(int weight, Gender gender, double bestMileTime) {
        super(weight, gender);
        this.bestMileTime = bestMileTime;
    }

    // Overrides toString method to include best mile time in the player's description
    @Override
    public String toString() {
        return super.toString() + " Best Mile Time: " + bestMileTime + " minutes";
    }

    // Overrides equals method to compare cross country players based on superclass attributes and best mile time
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof CrossCountryPlayer)) return false;
        CrossCountryPlayer other = (CrossCountryPlayer) obj;
        return Double.compare(bestMileTime, other.bestMileTime) == 0;
    }

    public double getBestMileTime() { return bestMileTime; }
    public void setBestMileTime(double bestMileTime) { this.bestMileTime = bestMileTime; }
}
