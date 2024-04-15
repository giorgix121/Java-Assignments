package sport;

public abstract class RunningSportsPlayer extends SportsPlayer{

    public RunningSportsPlayer(){
        super();
    }

    public RunningSportsPlayer(Integer weight, Gender gender){
        super(weight, gender);
    }

    // Overrides the toString method, currently just uses the implementation from SportsPlayer
    public String toString(){
        return super.toString();
    }

    // Overrides equals method to compare running sports players based on superclass attributes
    public boolean equals(Object runningObject) {

        if(this.equals(runningObject)){
            return true;
        }
        if(runningObject == null || runningObject.getClass() != this.getClass()){
            return false;
        }
        return super.equals(runningObject);
        
    }
    
}
