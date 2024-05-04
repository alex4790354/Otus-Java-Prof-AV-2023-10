package ru.otus.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

// version with Semaphore

public class CounterV3 {

    private static final Logger logger = LoggerFactory.getLogger(CounterV3.class);
    private boolean reverse = false;
    private int currentCount = 1;
    private final Semaphore sem1 = new Semaphore(1);
    private final Semaphore sem2 = new Semaphore(0);
    private volatile int printCounter = 0;  // Counts number of prints for the current number
    private final long startTime;
    private final long duration;

    public CounterV3(long durationMillis) {
        this.duration = durationMillis;
        this.startTime = System.currentTimeMillis();
    }

    public void run(int threadId) {
        while (!Thread.currentThread().isInterrupted() &&
                (System.currentTimeMillis() - startTime < duration)) {
            try {
                if (threadId == 1) {
                    sem1.acquire();
                } else {
                    sem2.acquire();
                }

                logger.info("Thread-{}: {}", threadId, currentCount);
                printCounter++;

                // Ensure both threads have printed the current number before changing it
                if (printCounter == 2) {
                    if (reverse) {
                        currentCount--;
                        if (currentCount <= 1) {
                            reverse = false;
                        }
                    } else {
                        currentCount++;
                        if (currentCount >= 10) {
                            reverse = true;
                        }
                    }
                    printCounter = 0;  // Reset the print counter
                }

                // Release the semaphore for the other thread
                if (threadId == 1) {
                    sem2.release();
                } else {
                    sem1.release();
                }

                // Let some time pass to allow the other thread to pick up
                sleep();

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
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
