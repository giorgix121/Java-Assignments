package sport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SportsMainClass {

	// Method to calculate the average weight of a list of sports players
    public static double getAverageWeight(List<SportsPlayer> players) {
        if (players.isEmpty()) {
            return 0;
        }

        int totalWeight = 0;
        for (SportsPlayer player : players) {
            totalWeight += player.getWeight();
        }

        return (double) totalWeight / players.size();
    }

    public static void main(String[] args) {

        ArrayList<SportsPlayer> players = new ArrayList<SportsPlayer>();

        //Adding players
        players.add(new BasketballPlayer(80, Gender.FEMALE, 170));
        players.add(new BasketballPlayer(75, Gender.FEMALE, 160));
        
        players.add(new BaseballPlayer(85, Gender.MALE, 240));
        players.add(new BaseballPlayer(75, Gender.MALE, 190));
        
        players.add(new VolleyballPlayer(65, Gender.FEMALE, 160));
        players.add(new VolleyballPlayer(60, Gender.FEMALE, 165));
        
        players.add(new TrackPlayer(70, Gender.FEMALE, 1500));
        players.add(new TrackPlayer(65, Gender.FEMALE, 2500));
        
        players.add(new CrossCountryPlayer(55, Gender.MALE, 5.0));
        players.add(new CrossCountryPlayer(50, Gender.MALE, 5.7));
        
        players.add(new ShotPutPlayer(90, Gender.FEMALE, 300));
        players.add(new ShotPutPlayer(80, Gender.MALE, 400));
        
        players.add(new PoleVaultPlayer(70, Gender.MALE, 320));
        players.add(new PoleVaultPlayer(65, Gender.FEMALE, 290));

        // Sorting and printing the list of players by weight in ascending order
        System.out.println("Sorted by weight in ascending order:");
        Collections.sort(players);
        for (SportsPlayer player : players) {
            System.out.println(player.toString());
        }

        // Sorting and printing the list by weight in descending order
        System.out.println("Sorted by weight in descending order:");
        Collections.sort(players, Collections.reverseOrder());
        for (SportsPlayer player : players) {
            System.out.println(player);
        }

        System.out.println("Average weight of all players: " + getAverageWeight(players) + "kgs");

        
    }

    
    
}
