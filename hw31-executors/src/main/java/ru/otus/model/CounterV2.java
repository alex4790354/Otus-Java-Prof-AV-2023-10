package ru.otus.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// version with Lock

public class CounterV2 {

    private static final Logger logger = LoggerFactory.getLogger(CounterV2.class);
    private boolean reverse = false;
    private int currentCount = 1;
    private int printCount = 0;  // Count how many times the currentCount has been printed.
    private final Lock lock = new ReentrantLock(true); // Fair lock to ensure thread order
    private final long startTime;
    private final long duration;

    public CounterV2(long durationMillis) {
        this.duration = durationMillis;
        this.startTime = System.currentTimeMillis();
    }

    public void run(int threadId) {
        while (!Thread.currentThread().isInterrupted() &&
                System.currentTimeMillis() - startTime < duration) {
            lock.lock();
            try {
                if (printCount < 2) { // Ensure both threads have printed the current count
                    logger.info("Thread-{}: {}", threadId, currentCount);
                    printCount++;
                    sleep();
                }

                if (printCount == 2) { // After both threads have printed, update count
                    countIncrease();
                    printCount = 0; // Reset for next number
                }
            } finally {
                lock.unlock();
            }

            // Sleep to allow some time for the other thread to act
            try {
                TimeUnit.MILLISECONDS.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void countIncrease() {
        if (!reverse) {
            currentCount++;
            if (currentCount == 10) {
                reverse = true;
            }
        } else {
            currentCount--;
            if (currentCount == 1) {
                reverse = false;
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
