package ru.otus;

import ru.otus.model.CounterV1;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Hw31MainV1 {

    public static void main(String[] args) {
        CounterV1 counterV1 = new CounterV1(2);

        // Create an executor service with 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> counterV1.run(1));
        executor.submit(() -> counterV1.run(2));

        // Shutdown the executor after 15 seconds
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();  // Force shutdown if tasks exceed the time limit
                System.out.println("Forced shutdown as tasks did not finish within 15 seconds.");
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();  // Preserve interrupt status
            System.out.println("Thread was interrupted.");
        }
    }

}
