package csc311_producer_consumer_lab;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;


public class SimpleProducerConsumerDriver {
    private static final Logger log = Logger.getLogger(csc311_producer_consumer_lab.SimpleProducerConsumerDriver.class.getCanonicalName());
    BlockingQueue<Double> blockingQueue = new LinkedBlockingDeque<>(5);

    private void produce() {
        while (true) {
            double value = generateValue();
            try {
                blockingQueue.put(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            log.info(String.format("[%s] Value produced: %f%n", Thread.currentThread().getName(), value));
        }
    }

    private void consume() {
        while (true) {
            Double value;
            try {
                value = blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            // Consume value
            log.info(String.format("[%s] Value consumed: %f%n", Thread.currentThread().getName(), value));
        }
    }

    private double generateValue() {
        return Math.random();
    }

    private void runProducerConsumer() {
        for (int i = 0; i < 2; i++) {
            Thread producerThread = new Thread(this::produce);
            producerThread.start();
        }

        for (int i = 0; i < 3; i++) {
            Thread consumerThread = new Thread(this::consume);
            consumerThread.start();
        }
    }

    public static void main(String[] args) {
        csc311_producer_consumer_lab.SimpleProducerConsumerDriver simpleProducerConsumerDriver = new csc311_producer_consumer_lab.SimpleProducerConsumerDriver();
        simpleProducerConsumerDriver.runProducerConsumer();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }
}