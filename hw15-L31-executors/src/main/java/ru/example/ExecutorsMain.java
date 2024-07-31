package ru.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutorsMain {
    private static final Logger log = LoggerFactory.getLogger(ExecutorsMain.class);
    private static final String THREAD_ONE = "Поток 1";
    private static final String THREAD_TWO = "Поток 2";
    private static final int MIN = 1;
    private static final int MAX = 10;
    private boolean isFirstThreadTurn = true;
    private int counter = 1;
    private boolean isAscending = true;
    private boolean isCompleted = false;

    public static void main(String[] args) {
        ExecutorsMain executorsMain = new ExecutorsMain();
        Thread thread1 = new Thread(executorsMain.new NumberPrinter(true));
        Thread thread2 = new Thread(executorsMain.new NumberPrinter(false));
        thread1.setName(THREAD_ONE);
        thread2.setName(THREAD_TWO);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    class NumberPrinter implements Runnable {
        private final boolean isThreadOne;

        public NumberPrinter(boolean isThreadOne) {
            this.isThreadOne = isThreadOne;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted() && !isCompleted) {
                synchronized (ExecutorsMain.this) {
                    while (isThreadOne != isFirstThreadTurn && !isCompleted) {
                        try {
                            ExecutorsMain.this.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    log.info("{} -> {}", Thread.currentThread().getName(), counter);

                    if (!isThreadOne) {
                        updateCounterAndDirection();
                    }

                    isFirstThreadTurn = !isFirstThreadTurn;
                    ExecutorsMain.this.notifyAll();
                }
            }
        }

        private void updateCounterAndDirection() {
            if (isAscending) {
                if (counter < MAX) {
                    counter++;
                } else {
                    isAscending = false;
                    counter--;
                }
            } else {
                if (counter > MIN) {
                    counter--;
                } else {
                    isCompleted = true;
                }
            }
        }
    }
}