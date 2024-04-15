package heros;

import java.util.ArrayList; 

public class Party {

    private ArrayList<Hero> heroes; 

    public Party() {
        heroes = new ArrayList<>(); 
    }

    public void addHero(Hero hero) {
    	//checking if hero is exist in party
        if (!heroes.contains(hero)) { 
            heroes.add(hero);
        } else {
            System.out.println(hero.getName() + " is already in the party");
        }
    }

    public void removeHero(int index) {
        if (index >= 0 && index < heroes.size()) {
            heroes.remove(index); 
        }
    }

    public Hero getHero(int index) {
        if (index >= 0 && index < heroes.size()) {
            return heroes.get(index); 
        }
        // if index is out of the range
        return null; 
    }

    public void gainExperience(int experience) {
        System.out.println("The party has gained " + experience + " experience");
        if (!heroes.isEmpty()) {
            // round up.
            int individualExperience = (int) Math.ceil((1.0 * experience) / heroes.size());
            for (Hero hero : heroes) {
                hero.gainExperience(individualExperience); 
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Party:\n");
        for (Hero hero : heroes) {
            sb.append(hero.toString()).append("\n"); 
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        
        Hero h1 = new Hero("Bob");
        h1.setRole("Warrior");
        Hero h2 = new Hero("John");
        h2.setRole("Wizard");
        Hero h3 = new Hero("Jane");
        h3.setRole("Priest");
        Hero h4 = new Hero("Dimitri");
        h4.setRole("Thief");

        Party p = new Party();
        p.addHero(h1);
        p.addHero(h2);
        p.addHero(h3);

        System.out.println(p);
        //remove hero at the index 2
        p.removeHero(2); 

        System.out.println(p);
        p.addHero(h4);

        System.out.println(p);
        //remove hero at the index 0
        p.removeHero(0); 
        System.out.println(p);
        p.addHero(h3);
        System.out.println(p);
        
        p.addHero(h3);
        System.out.println(p);
        
        p.addHero(h1);
        System.out.println(p);


        p.removeHero(1);
        System.out.println(p);

        p.gainExperience(25);
        System.out.println(p);
    }
}
