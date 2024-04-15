package heros;

public class Hero {

    private String name;
    private String role;
    private int level;
    private int experience;
    private final static int MAX_LEVEL = 100;
    public final static String[] ROLES = {"Warrior", "Priest", "Wizard", "Thief"};
    
    public Hero(String name) {
        this.name = name;
        this.level = 1;
        this.experience = 0;
    }
    
    public void setRole(String role) {
        for (String r : ROLES) {
            if (r.equals(role)) {
                this.role = role;
                return;
            }
        }
        System.out.println("Invalid role");
        this.role = "Unassigned";
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }
    
    public int expToLevelUp() {
        return (int)Math.pow(this.level, 2);
    }
    
    public void gainExperience(int experience) {
        this.experience += experience;
        while ((this.experience >= expToLevelUp()) && (this.level < MAX_LEVEL)) {
            this.experience -= expToLevelUp();
            level++;
            System.out.println(this.name + " is now level " + this.level);
        }
    }
    
    public String toString() {
        return this.name + " the " + this.role + " is level " + this.level +
                " with " + this.experience + " experience.";
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
        Hero h5 = new Hero("Greg");
        h5.setRole("Paladin");
        Hero[] heroes = {h1, h2, h3, h4, h5};
        for (Hero h : heroes) {
            System.out.println(h);
        }
        h1.gainExperience(1);
        System.out.println(h1);
        h1.gainExperience(2);
        System.out.println(h1);
        h1.gainExperience(2);
        System.out.println(h1);
        h1.gainExperience(4);
        System.out.println(h1);
        h1.gainExperience(30);
        System.out.println(h1);
        h1.gainExperience(1000000);
        System.out.println(h1);
        h1.gainExperience(200);
        System.out.println(h1);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
    public static String[] getRoles() {
        return ROLES;
    }
}
