package pair;

public class Pair<K, V>{

    private K key;
    private V value;

    
    // Constructor to initialize Pair of key and value
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    // Main method for demonstration and testing
    public static void main(String[] args) {
        Pair<Integer, String> pair = new Pair<Integer, String>(2001, "Two Thousand One");
        Pair<String, String> pair2 = new Pair<String, String>("Hello", "Hola");
        System.out.println(pair);
        System.out.println(pair2.toString());
    }

    // Override equals method to compare two Pair objects based on their keys and values
    @SuppressWarnings("rawtypes")
    public boolean equals(Object pairObject) {
        if (this.key == ((Pair) pairObject).key
                && this.value == ((Pair) pairObject).value) {
            return true;
        }
        return false;
    }


    // Override toString method to return string of Pair
    public String toString(){
        return "Key: " + this.key + " Value: " + this.value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
    
	
}
