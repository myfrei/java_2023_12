package ru.example.log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.example.log.anotation.Log;

public class LoggingProxy implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoggingProxy.class);
    private final TestLoggingInterface testLoggingInterface;

    public LoggingProxy(TestLoggingInterface testLoggingInterface) {
        this.testLoggingInterface = testLoggingInterface;
    }

    public static TestLoggingInterface createProxy(TestLoggingInterface testLoggingInterface) {
        InvocationHandler handler = new LoggingProxy(testLoggingInterface);
        return (TestLoggingInterface) Proxy.newProxyInstance(
                LoggingProxy.class.getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methodCache.contains(getHashKey(method))) {
            logger.info("executed method: {}, param: {}", method.getName(), getParameters(args));
        }
        return method.invoke(testLoggingInterface, args);
    }

    private static final HashSet<String> methodCache = new HashSet<>();

    static {
        for (Method method : TestLogging.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Log.class)) {
                methodCache.add(getHashKey(method));
            }
        }
    }

    private static String getParameters(Object[] args) {
        return (args == null || args.length == 0) ? "no parameters" : Arrays.toString(args);
    }

    private static String getHashKey(Method method) {
        return method.getName() + Arrays.toString(method.getParameterTypes());
    }
}
