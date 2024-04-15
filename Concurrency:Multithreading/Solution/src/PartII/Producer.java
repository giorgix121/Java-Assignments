package PartII;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

public class Producer implements Runnable {

    public static final int DELAY = 2; // Delay in processing
    public static final int MAX_QUEUE_SIZE = 50; // Max items allowed in queue
    
    private Queue<String> out;  // Hold produced strings
    private ArrayList<String> words; // List of words to be produced
    
    // Constructor loads words from file and shuffles
    public Producer(Queue<String> out) {
        this.out = out;
        this.words = new ArrayList<String>();
        try {
            FileReader f = new FileReader("wordlist.10000.txt");
            BufferedReader r = new BufferedReader(f);
            String inLine = r.readLine();
            while (inLine != null) {
                words.add(inLine);
                inLine = r.readLine();
            }
            Collections.shuffle(words);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    @Override
    public void run() {
        try {
            for (String word : words) {
                synchronized (out) {
                    while (out.size() == MAX_QUEUE_SIZE) {
                        out.wait(); // Wait if queue is full
                    }
                    out.add(word);
                    out.notifyAll();
                }
                // Sleep for random time up to DELAY to simulate work
                Thread.sleep((long) (Math.random() * DELAY));
            }
        } catch (InterruptedException e) {
            System.out.println("Producer interrupted.");
        }
    }
}
