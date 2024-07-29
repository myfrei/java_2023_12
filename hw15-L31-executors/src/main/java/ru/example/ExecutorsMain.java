package ru.example;

public class ExecutorsMain {

    private static final int MIN = 1;
    private static final int MAX = 10;
    private final Object lock = new Object();
    private boolean isFirstThreadTurn = true;
    private int counter = 1;
    private boolean isAscending = true;
    private boolean isCompleted = false;

    public static void main(String[] args) {
        ExecutorsMain executorsMain = new ExecutorsMain();
        Thread thread1 = new Thread(executorsMain.new NumberPrinter("Поток 1"));
        Thread thread2 = new Thread(executorsMain.new NumberPrinter("Поток 2"));

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
        private final String threadName;

        public NumberPrinter(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while ((threadName.equals("Поток 1") && !isFirstThreadTurn)
                            || (threadName.equals("Поток 2") && isFirstThreadTurn)) {
                        if (isCompleted) {
                            lock.notifyAll();
                            return;
                        }
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    if (isCompleted) {
                        lock.notifyAll();
                        return;
                    }

                    System.out.printf("%s: %d%n", threadName, counter);

                    isFirstThreadTurn = !isFirstThreadTurn;

                    if (threadName.equals("Поток 2")) {
                        updateCounterAndDirection();
                    }

                    lock.notifyAll();
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