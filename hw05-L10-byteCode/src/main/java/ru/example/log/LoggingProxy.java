package ru.example.log;

import ru.example.log.anotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LoggingProxy implements InvocationHandler {
    private final Object target;

    public LoggingProxy(Object target) {
        this.target = target;
    }

    public Object createProxy() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Log.class)) {
            logMethodCall(method, args);
        }
        return method.invoke(target, args);
    }

    private void logMethodCall(Method method, Object[] args) {
        StringBuilder sb = new StringBuilder("executed method: " + method.getName() + ", params: ");
        for (Object arg : args) {
            sb.append(arg).append(", ");
        }
        if (args.length > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        System.out.println(sb.toString());
    }
}