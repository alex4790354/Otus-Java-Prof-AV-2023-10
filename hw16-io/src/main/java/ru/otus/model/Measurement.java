package ru.otus.model;

// Let's say this class is a library class... => cannot be changed
public record Measurement(String name, double value) {

    @Override
    public String toString() {
        return "Measurement{" + "name='" + name + '\'' + ", value=" + value + '}';
    }
}
