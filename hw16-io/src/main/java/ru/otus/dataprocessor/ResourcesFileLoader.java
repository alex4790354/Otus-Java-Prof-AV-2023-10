package ru.otus.dataprocessor;

import ru.otus.model.Measurement;
import java.util.Collections;
import java.util.List;


public class ResourcesFileLoader implements Loader {

    public ResourcesFileLoader(String fileName) {}

    @Override
    public List<Measurement> load() {
        // reads the file, parses it and returns the result
        // читает файл, парсит и возвращает результат
        return Collections.emptyList();
    }
}
