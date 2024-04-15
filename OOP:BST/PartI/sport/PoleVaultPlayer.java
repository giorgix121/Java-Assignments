package sport;

public class PoleVaultPlayer extends FieldSportsPlayer {
    private int maxHeight;

    // Constructor initializing PoleVaultPlayer with weight, gender, and maximum height
    public PoleVaultPlayer(int weight, Gender gender, int maxHeight) {
        super(weight, gender);
        this.maxHeight = maxHeight;
    }

    // Overrides the toString method to include maximum height in the player's description
    @Override
    public String toString() {
        return super.toString() + " Max Height: " + maxHeight + " cm";
    }

    // Overrides equals method to compare pole vault players based on superclass attributes and maximum height
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof PoleVaultPlayer)) return false;
        PoleVaultPlayer other = (PoleVaultPlayer) obj;
        return maxHeight == other.maxHeight;
    }

    public int getMaxHeight() { return maxHeight; }
    public void setMaxHeight(int maxHeight) { this.maxHeight = maxHeight; }
}
