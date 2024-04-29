package ru.otus.appcontainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.exception.DiException;

@SuppressWarnings({"squid:S1068", "java:S3011"})
public class AppComponentsContainerImpl implements AppComponentsContainer {
    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        findMethodsWithAnnotation(configClass);
        sortMethods();
        Object instance = getConfigInstance(configClass);
        createBeans(instance);
    }

    private void sortMethods() {
        Collections.sort(appComponents, (o1, o2) -> {
            Method m1 = (Method) o1;
            Method m2 = (Method) o2;
            AppComponent a1 = m1.getDeclaredAnnotation(AppComponent.class);
            AppComponent a2 = m2.getDeclaredAnnotation(AppComponent.class);
            return a1.order() - a2.order();
        });
    }

    private void findMethodsWithAnnotation(Class<?> configClass) {
        for (Method method : configClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AppComponent.class)) {
                appComponents.add(method);
            }
        }
    }

    private Object getConfigInstance(Class<?> configClass) {
        Constructor<?> constructor;
        Object instance;
        try {
            constructor = configClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
        } catch (NoSuchMethodException
                | InstantiationException
                | IllegalAccessException
                | InvocationTargetException e) {
            throw new DiException(e);
        }
        return instance;
    }

    private void createBeans(Object instance) {
        for (Object obj : appComponents) {
            Method method = (Method) obj;
            AppComponent annotation = method.getDeclaredAnnotation(AppComponent.class);
            checkThatBeanNotExistWithTheSameName(annotation.name());
            Object[] params = getMethodParams(method);
            makeBean(method, instance, annotation.name(), params);
        }
    }

    private void checkThatBeanNotExistWithTheSameName(String beanName) {
        if (appComponentsByName.get(beanName) != null) {
            throw new DiException("Bean with name: " + beanName + " already exist in container");
        }
    }

    private Object[] getMethodParams(Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        if (paramTypes.length == 0) {
            return paramTypes;
        }
        Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            params[i] = getAppComponent(paramTypes[i]);
        }
        return params;
    }

    private void makeBean(Method method, Object instance, String name, Object... args) {
        try {
            method.setAccessible(true);
            Object bean = method.invoke(instance, args);

            appComponentsByName.put(name, bean);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DiException(e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        Object res = null;
        for (Map.Entry<String, Object> entry : appComponentsByName.entrySet()) {
            Object value = entry.getValue();
            if (componentClass.isInstance(value)) {
                if (res != null) {
                    throw new DiException("Type: " + componentClass.getName() + " match several beans");
                }
                res = value;
            }
        }
        if (res == null) {
            throw new DiException("Bean not found: " + componentClass.getName());
        }
        return (C) res;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object bean = appComponentsByName.get(componentName);
        if (bean == null) {
            throw new DiException("Bean not found: " + componentName);
        }
        return (C) bean;
    }
}
