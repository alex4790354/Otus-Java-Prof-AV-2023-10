package ru.otus.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// version with monitor

public class CounterV1 {
    private static final Logger logger = LoggerFactory.getLogger(CounterV1.class);
    private boolean reverse = false;
    private int currentCount = 1;
    private int nextThread = 1;
    private final int threadsCount;
    private final Object monitor = new Object();

    public CounterV1(int threadsCount) {
        this.threadsCount = threadsCount;
    }

    public void run(int threadId) {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (monitor) {
                try {
                    while (threadId != this.nextThread) {
                        monitor.wait();
                    }

                    logger.info("Thread-{}: {}", threadId, currentCount);
                    if (threadId == threadsCount) {
                        countIncrease();
                    }
                    this.nextThread = this.nextThread % threadsCount + 1;

                    sleep();
                    monitor.notifyAll();

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
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
