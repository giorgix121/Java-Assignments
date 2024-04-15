package PartI;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.DoubleAdder;

public class LeibnizPi {

	// π for comparison
    private static final double PI = Math.PI;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the number of terms for π calculation (e.g., 1000000000): ");
        int numTerms = scanner.nextInt();
        
        System.out.println("Enter the number of threads to use: ");
        int numThreads = scanner.nextInt();
        
        // Adjusting numThreads based on input validation
        numThreads = Math.min(numThreads, Runtime.getRuntime().availableProcessors());
        numThreads = Math.max(numThreads, 1); // Ensuring at least one thread
        
        calculatePi(numTerms, numThreads);
        
        scanner.close();
    }

    private static void calculatePi(int numTerms, int numThreads) {
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        DoubleAdder result = new DoubleAdder(); // Thread-safe adder for accumulating results
        
        long startTime = System.currentTimeMillis();
        
        // Calculating block size for each thread to process
        int blockSize = numTerms / numThreads;
        int remainder = numTerms % numThreads;

        for (int i = 0; i < numThreads; i++) {
            final int start = i * blockSize;
            final int end = (i == numThreads - 1) ? start + blockSize + remainder : start + blockSize;

            // Submitting tasks to executor service
            executorService.submit(() -> {
                for (int j = start; j < end; j++) {
                    result.add(((j % 2 == 0) ? 1.0 : -1.0) / (2 * j + 1));
                }
            });
        }

        executorService.shutdown();
        try {
        	// Awaiting termination of all tasks
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted, Failed to complete calculation");
        }
        // Final value
        double calculatedPi = result.sum() * 4;
        // Absolute error value
        double error = Math.abs(calculatedPi - PI);
        long endTime = System.currentTimeMillis();

        System.out.println("Calculated Pi: " + calculatedPi);
        System.out.println("Actual Pi: " + PI);
        System.out.println("Absolute Error: " + error);
        System.out.println("Number of Threads: " + numThreads);
        System.out.println("Execution Time: " + (endTime - startTime) + " ms");
    }
}
