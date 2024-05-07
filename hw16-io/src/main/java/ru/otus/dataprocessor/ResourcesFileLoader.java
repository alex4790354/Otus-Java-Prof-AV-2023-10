package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class ResourcesFileLoader implements Loader {
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    // reads the file, parses it and returns the result
    @Override
    public List<Measurement> load() {
        // Using try-with-resources to ensure the InputStream is closed after use
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalStateException("File not found in resources: " + fileName);
            }

            // Create a new Gson instance
            Gson gson = new Gson();

            // Define the type of data we expect
            Type listType = new TypeToken<List<Measurement>>() {}.getType();

            // Deserialize JSON from file to List of Measurement using GSON
            return gson.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), listType);
        } catch (Exception e) {
            throw new RuntimeException("Error loading data from file", e);
        }
    }
}
