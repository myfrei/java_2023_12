package ru.example.framework;

public class Statistic {
    private int total;
    private int passed;
    private int failed;

    public Statistic() {
        total = 0;
        passed = 0;
        failed = 0;
    }

    public int getTotal() {
        return total;
    }

    public int getPassed() {
        return passed;
    }

    public void passed() {
        passed++;
        total++;
    }

    public void failed() {
        failed++;
        total++;
    }

    public void printStatistics() {
        System.out.println("Total tests: " + total);
        System.out.println("Passed tests: " + passed + " - " + Math.round((double) passed / total * 100) + "%");
        System.out.println("Failed tests: " + failed + " - " + Math.round((double) failed / total * 100) + "%");
    }
}