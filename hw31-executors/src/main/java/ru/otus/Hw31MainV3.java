package ru.otus;

import ru.otus.model.CounterV3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Hw31MainV3 {

    public static void main(String[] args) {
        CounterV3 counter = new CounterV3(4000);  // 5 seconds duration

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> counter.run(1));
        executor.submit(() -> counter.run(2));

        executor.shutdown();
        try {
            if (!executor.awaitTermination(6, TimeUnit.SECONDS)) {  // Allow an extra second for cleanup
                executor.shutdownNow();
                System.out.println("Executor was forcefully shutdown after exceeding allowed running time.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
            System.out.println("Main thread was interrupted.");
        }
    }
}
