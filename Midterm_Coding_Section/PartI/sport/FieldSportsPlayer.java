package sport;

public abstract class FieldSportsPlayer extends SportsPlayer{

    public FieldSportsPlayer(){
        super();
    }

    // Constructor forwarding weight and gender to the superclass (SportsPlayer)
    public FieldSportsPlayer(Integer weight, Gender gender){
        super(weight, gender);
    }

    // Overrides the toString method, currently just uses the implementation from SportsPlayer
    @Override
    public String toString(){
        return super.toString();
    }

 // Overrides equals method to compare field sports players based on superclass attributes
    @Override
    public boolean equals(Object fieldObject) {

        if(this.equals(fieldObject)){
            return true;
        }
        if(fieldObject == null || fieldObject.getClass() != this.getClass()){
            return false;
        }
        return super.equals(fieldObject);

    }
    
}
