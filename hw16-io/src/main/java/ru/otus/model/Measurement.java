package ru.otus.model;

// Let's say this class is a library class, it cannot be changed
// Допустим, этот класс библиотечный, его нельзя менять
public record Measurement(String name, double value) {

    @Override
    public String toString() {
        return "Measurement{" + "name='" + name + '\'' + ", value=" + value + '}';
    }
}
