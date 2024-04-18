package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.test.annotations.AfterEach;
import ru.otus.test.annotations.BeforeEach;
import ru.otus.test.annotations.Test;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyService {
    private static final Logger logger = LoggerFactory.getLogger(DummyService.class);

    private void run(Class<?> clazz)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Method> testMethods = paresMethodsAnnotations(clazz, Test.class);
        List<Method> beforeEachMethods = paresMethodsAnnotations(clazz, BeforeEach.class);
        List<Method> afterEachMethods = paresMethodsAnnotations(clazz, AfterEach.class);

        Map<String, String> result = executeTestMethods(testMethods, beforeEachMethods, afterEachMethods, clazz);
        printResult(result);
    }

    private List<Method> paresMethodsAnnotations(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        List<Method> resMethods = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                resMethods.add(method);
            }
        }
        logger.info(
                "Class '{}' has: '{}' methods with annotation : '{}'",
                clazz.getName(),
                resMethods.size(),
                annotationClass.getSimpleName());
        return resMethods;
    }

    private Map<String, String> executeTestMethods(
            List<Method> testMethods, List<Method> beforeEachMethods, List<Method> afterEachMethods, Class<?> clazz)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        logger.info("##DummyService: Service start.");
        Map<String, String> result = new HashMap<>();
        for (Method testMethod : testMethods) {
            Object testObject = clazz.getDeclaredConstructor().newInstance();
            try {
                executeListMethods(beforeEachMethods, testObject);
                testMethod.invoke(testObject);
                result.put(testMethod.getName(), "SUCCESS");
            } catch (Exception e) {
                result.put(testMethod.getName(), "FAILED");
                logger.error("##executeTestMethods: Exception happened in method '{}'", testMethod.getName());
            } finally {
                executeListMethods(afterEachMethods, testObject);
            }
        }
        return result;
    }

    private void printResult(Map<String, String> result) {
        logger.info("## Print result:");
        for (Map.Entry<String, String> entry : result.entrySet()) {
            logger.info("Test '{}' is '{}'", entry.getKey(), entry.getValue());
        }
    }

    private void executeListMethods(List<Method> methods, Object object)
            throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(object);
        }
    }
}
