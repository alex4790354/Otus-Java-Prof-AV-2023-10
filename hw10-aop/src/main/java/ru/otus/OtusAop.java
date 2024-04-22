package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotation.OtusLog;

public class OtusAop {
    private static final Logger logger = LoggerFactory.getLogger(OtusAop.class);

    public static LogTestInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new LogTestInterfaceImpl());
        return (LogTestInterface) Proxy.newProxyInstance(
                OtusAop.class.getClassLoader(),
                new Class<?>[]{LogTestInterface.class},
                handler
        );
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final LogTestInterface testLogging;
        private final Map<Method, Boolean> methodCache;

        DemoInvocationHandler(LogTestInterface testLogging) {
            this.testLogging = testLogging;
            this.methodCache = new HashMap<>();

            for (Method method : testLogging.getClass().getMethods()) {
                methodCache.put(method, method.isAnnotationPresent(OtusLog.class));
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method realMethod = testLogging.getClass().getMethod(method.getName(), method.getParameterTypes());
            Boolean requiresLogging = methodCache.get(realMethod);
            if (requiresLogging != null && requiresLogging) {
                logger.info("Proxy for method: '{}', param: '{}'", method.getName(), Arrays.toString(args));
            }
            return method.invoke(testLogging, args);
        }
    }
}