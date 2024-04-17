package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.service.DummyService;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Hw06Main {

    private static final Logger logger = LoggerFactory.getLogger(Hw06Main.class);

    public static void main(String[] args)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        String className = "ru.otus.test.AnnotationsUseExample";
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (Exception e) {
            logger.error("##Hw06Main. Exception in: '{}' ", className, e);
            return;
        }
        Constructor<?> constructor = DummyService.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        DummyService dummyService = (DummyService) constructor.newInstance();
        Method methodRun = dummyService.getClass().getDeclaredMethod("run", Class.class);
        methodRun.setAccessible(true);
        methodRun.invoke(dummyService, clazz);
    }
}
