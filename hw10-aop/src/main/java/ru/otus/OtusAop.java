package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
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

        DemoInvocationHandler(LogTestInterface testLogging) {
            this.testLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method realMethod = testLogging.getClass().getMethod(method.getName(), method.getParameterTypes());
            if (realMethod.isAnnotationPresent(OtusLog.class)) {
                logger.info("Proxy for method: '{}', param: '{}'", method.getName(), Arrays.toString(args));
            }
            return method.invoke(testLogging, args);
        }
    }
}
