package PartII;

import java.util.Map;
import java.util.Queue;

public class Consumer implements Runnable {

    public static final int DELAY = 2;
    
    private Queue<String> in;
    private Map<Character, Integer> out;
    
    // Constructor
    public Consumer(Queue<String> in, Map<Character,Integer> out) {
        this.in = in;
        this.out = out;
    }
    
    
    @Override
    public void run() {
        while (true) {
            synchronized (in) { // Locks queue to ensure exclusive access
                while (in.isEmpty()) {
                    try {
                        in.wait(); // Wait until notified of available string
                    } catch (InterruptedException e) {
                        System.out.println("Consumer interrupted.");
                        return;
                    }
                }
                String word = in.poll();
                in.notifyAll();

                synchronized (out) {
                    char firstChar = word.charAt(0);
                    // Updates map with the count of first character
                    out.put(firstChar, out.getOrDefault(firstChar, 0) + 1);
                }
            }

            try {
            	// Random delay to simulate processing time
                Thread.sleep((long) (Math.random() * DELAY));
            } catch (InterruptedException e) {
                System.out.println("Consumer sleep interrupted.");
            }
        }
    }
}
