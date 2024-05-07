package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.CounterV1;
import ru.otus.model.CounterV2;
import ru.otus.model.CounterV3;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


public class Hw31Main {
    private static final Logger logger = LoggerFactory.getLogger(CounterV1.class);

    public static void main(String[] args) throws InterruptedException {

        logger.info("##19 run CounterV1");
        runVersion1();
        Thread.sleep(3000);

        logger.info("##23 run CounterV2");
        runVersion2();
        Thread.sleep(2000);

        logger.info("##27 run CounterV1");
        runVersion1();
    }

    private static void runVersion1() {
        final CounterV1 counterV1 = new CounterV1(2);
        Thread thread1 = new Thread(() -> counterV1.run(1), "Thread-1");
        Thread thread2 = new Thread(() -> counterV1.run(2), "Thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join(3000);
            thread2.join(3000);
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted.");
            Thread.currentThread().interrupt();
        }

        checkAndInterrupt(thread1, "Thread-1");
        checkAndInterrupt(thread2, "Thread-2");
    }

    private static void runVersion2() {
        CounterV2 counter = new CounterV2(7000);
        runWithExecutor(counter::run, 6);
    }

    private static void runVersion3() {
        CounterV3 counter = new CounterV3(4000);
        runWithExecutor(counter::run, 6);
    }

    private static void runWithExecutor(Consumer<Integer> counterAction, int timeout) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> counterAction.accept(1));
        executor.submit(() -> counterAction.accept(2));

        executor.shutdown();
        try {
            if (!executor.awaitTermination(timeout, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                System.out.println("Forced shutdown after exceeding allowed run time.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
            System.out.println("Main thread was interrupted.");
        }
    }

    private static void checkAndInterrupt(Thread thread, String threadName) {
        if (thread.isAlive()) {
            thread.interrupt();
            System.out.println("Forced to stop " + threadName + " as it did not finish within 3 seconds.");
        }
    }
}
