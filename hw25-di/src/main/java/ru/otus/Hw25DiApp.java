package ru.otus;

import ru.otus.appcontainer.AppComponentsContainerImpl;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.config.AppConfig;
import ru.otus.services.GameProcessor;


@SuppressWarnings({"squid:S125", "squid:S106"})
public class Hw25DiApp {

    public static void main(String[] args) {
        // Optional options
        // AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig1.class, AppConfig2.class);

        // Here you can use the Reflections library (see dependencies)
        // AppComponentsContainer container = new AppComponentsContainerImpl("ru.otus.config");

        // Required option
        AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);

        // The application must work in each of the following options
        GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);

        // GameProcessor gameProcessor = container.getAppComponent(GameProcessorImpl.class);
        // GameProcessor gameProcessor = container.getAppComponent("gameProcessor");
        gameProcessor.startGame();
    }
}
