package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
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
        private final Set<String> methodsRequiringLogs;

        DemoInvocationHandler(LogTestInterface testLogging) {
            this.testLogging = testLogging;
            this.methodsRequiringLogs = new HashSet<>();

            // Populate the set with method signatures requiring logging
            for (Method method : testLogging.getClass().getMethods()) {
                if (method.isAnnotationPresent(OtusLog.class)) {
                    String signature = methodSignature(method);
                    methodsRequiringLogs.add(signature);
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Create a signature for the current method call
            String signature = methodSignature(method);

            // Check if this method signature is in the set of methods requiring logs
            if (methodsRequiringLogs.contains(signature)) {
                logger.info("Proxy for method: '{}', param: '{}'", method.getName(), Arrays.toString(args));
            }
            return method.invoke(testLogging, args);
        }

        // Helper method to create a unique signature for a method
        private String methodSignature(Method method) {
            String paramTypes = Arrays.stream(method.getParameterTypes())
                    .map(Class::getTypeName)
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");
            return method.getName() + "(" + paramTypes + ")";
        }
    }
}
