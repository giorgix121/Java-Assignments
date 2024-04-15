package sport;

public abstract class BallSportsPlayer extends SportsPlayer {
	// Constructor forwarding weight and gender to the superclass which is SportsPlayer
	public BallSportsPlayer(int weight, Gender gender) {
        super(weight, gender);
    }
}
