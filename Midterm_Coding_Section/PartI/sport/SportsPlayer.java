package sport;

//Abstract class that defines a generic sports player with common attributes and methods
public abstract class SportsPlayer implements Comparable<SportsPlayer>{
    private static Integer nextId = 0;
    private Integer id;
    private Integer weight;
    private Gender gender;

    // Default constructor increments ID counter and assigns new ID
    public SportsPlayer(){
        SportsPlayer.nextId++;
        this.id = SportsPlayer.nextId;
    }

    // Constructor with weight and gender parameters
    public SportsPlayer(int weight, Gender gender) {
        this.id = nextId++;
        this.weight = weight;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return String.format("ID: %d Weight: %d Gender: %s", id, weight, gender);
    }

    // Overrides Object's equals method to compare sports players based on weight and gender
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SportsPlayer)) return false;
        SportsPlayer other = (SportsPlayer) obj;
        return weight == other.weight && gender == other.gender;
    }

    // Implementation of Comparable interface based on player's weight
    @Override
    public int compareTo(SportsPlayer other) {
        return Integer.compare(this.weight, other.weight);
    }

    // getter and setter methods
    public int getId() { return id; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
}
