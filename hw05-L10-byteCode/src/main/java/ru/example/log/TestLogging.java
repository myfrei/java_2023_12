package ru.example.log;

import ru.example.log.anotation.Log;

public class TestLogging implements TestLoggingInterface {
    @Log
    public void calculation(int param1) {
        System.out.println("Calculation with one parameter: " + param1);
    }

    @Log
    public void calculation(int param1, int param2) {
        System.out.println("Calculation with two parameters: " + param1 + ", " + param2);
    }

    @Log
    public void calculation(int param1, int param2, String param3) {
        System.out.println("Calculation with three parameters: " + param1 + ", " + param2 + ", " + param3);
    }
}