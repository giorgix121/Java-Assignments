import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import heros.*;

public class ReadHeroFile {

	// Creating Hero object from hero info
    public static Hero createHero(String heroInfo) throws HeroException {
        String[] heroDetails = heroInfo.split(",");
        if (heroDetails.length < 4) throw new HeroException("Incomplete hero data provided: " + heroInfo);
        // Initialize hero
        Hero heroInstance = new Hero(heroDetails[0]);
        String[] availableRoles = heroInstance.getRoles();
        if (!Arrays.asList(availableRoles).contains(heroDetails[1])) {
            throw new HeroException("Role not recognized: " + heroDetails[1]);
        } else {
            heroInstance.setRole(heroDetails[1]);
        }

        int levelExpected = Integer.parseInt(heroDetails[2]);
        int experienceProvided = Integer.parseInt(heroDetails[3]);
        // automatically updating level
        heroInstance.gainExperience(experienceProvided);
        int levelCalculated = heroInstance.getLevel();

        if(levelCalculated != levelExpected) {
            throw new HeroException("Level and experience mismatch for: "+heroDetails[0]);
        } else {
            heroInstance.setExperience(experienceProvided);
            heroInstance.setLevel(levelExpected);
        }
        return heroInstance;
    }

    public static void main(String[] args) {
        List<Hero> roster = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("heroes.txt"))) {
            String heroRecord;
            while ((heroRecord = br.readLine()) != null) {
                try {
                    Hero hero = createHero(heroRecord);
                    roster.add(hero);
                } catch(HeroException ex) {
                    System.out.println("Hero creation failed for record: " + heroRecord + " | Reason: " + ex.getMessage());
                }
            }
        } catch(IOException ex) {
            System.err.println("Error reading file: " + ex.getMessage());
        }
    }
}