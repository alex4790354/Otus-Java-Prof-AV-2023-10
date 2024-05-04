package ru.otus;

import ru.otus.model.CounterV2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Hw31MainV2 {

    public static void main(String[] args) {
        CounterV2 counter = new CounterV2(7000);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> counter.run(1));
        executor.submit(() -> counter.run(2));

        executor.shutdown();
        try {
            if (!executor.awaitTermination(6, TimeUnit.SECONDS)) { // Allow a little extra time for cleanup
                executor.shutdownNow();
                System.out.println("Forced shutdown after exceeding allowed run time.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
            System.out.println("Main thread was interrupted.");
        }
    }
}
