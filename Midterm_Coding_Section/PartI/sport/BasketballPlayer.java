package sport;

public class BasketballPlayer extends BallSportsPlayer {
    private int height; // CM

    // Constructor initializing BasketballPlayer with weight, gender, and height
    public BasketballPlayer(int weight, Gender gender, int height) {
        super(weight, gender);
        this.height = height;
    }

    // Overrides toString method to include height in the player's description
    @Override
    public String toString() {
        return super.toString() + " Height: " + height + "cm";
    }

    // Overrides equals method to compare basketball players based on superclass attributes and height
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof BasketballPlayer)) return false;
        BasketballPlayer other = (BasketballPlayer) obj;
        return height == other.height;
    }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
}
