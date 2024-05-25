package ru.example.log;

import ru.example.log.anotation.Log;

public class TestLogging implements TestLoggingInterface {

    private int sum;

    public TestLogging() {
        sum = 0;
    }

    @Log
    @Override
    public void calculation(int param1) {
        sum += param1;
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        sum += param1;
        sum += param2;
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        sum += param1;
        sum += param2;
        sum += Integer.parseInt(param3);
    }

    @Log
    @Override
    public int getSum() {
        return sum;
    }
}
