package ru.example.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ru.example.framework.annotation.After;
import ru.example.framework.annotation.Before;
import ru.example.framework.annotation.Skip;
import ru.example.framework.annotation.Test;

public class ProcessingAnnotation {
    public static void run(String className, Statistic statistic) {
        try {
            Class<?> testClass = Class.forName(className);
            List<Method> beforeMethods = getMethods(testClass, Before.class);
            List<Method> testMethods = getMethods(testClass, Test.class);
            List<Method> afterMethods = getMethods(testClass, After.class);

            for (Method testMethod : testMethods) {
                if (testMethod.isAnnotationPresent(Skip.class)) {
                    String reason = testMethod.getAnnotation(Skip.class).reason();
                    System.out.println("Skipping test: " + testMethod.getName() + " (Reason: " + reason + ")");
                    statistic.skipped();
                    continue;
                }

                Object instanceTest = testClass.getDeclaredConstructor().newInstance();
                try {
                    invokeMethods(instanceTest, beforeMethods);
                    System.out.println("Run method: " + className + "." + testMethod.getName());
                    testMethod.invoke(instanceTest);
                    statistic.passed();
                } catch (InvocationTargetException e) {
                    System.out.println("Test failed: " + testMethod.getName() + ". Error: " + e.getCause());
                    statistic.failed();
                } finally {
                    invokeMethods(instanceTest, afterMethods);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static List<Method> getMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    private static void invokeMethods(Object instance, List<Method> methods)
            throws IllegalAccessException, InvocationTargetException {
        for (Method method : methods) {
            method.setAccessible(true); // Разрешение доступа к методам с любой видимостью
            method.invoke(instance);
        }
    }
}
