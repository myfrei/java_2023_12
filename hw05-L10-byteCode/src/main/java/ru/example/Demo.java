package ru.example;

import ru.example.log.LoggingProxy;
import ru.example.log.TestLogging;
import ru.example.log.TestLoggingInterface;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface loggingProxy = (TestLoggingInterface) new LoggingProxy(new TestLogging()).createProxy();

        loggingProxy.calculation(6);
        loggingProxy.calculation(4, 8);
        loggingProxy.calculation(2, 5, "test");
    }
}