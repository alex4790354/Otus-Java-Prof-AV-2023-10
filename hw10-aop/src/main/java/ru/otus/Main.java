package ru.otus;

public class Main {
    public static void main(String[] args) {
        LogTestInterface logTestInterface = OtusAop.createMyClass();
        logTestInterface.calculation(6);
        logTestInterface.calculation(6, 7);
        logTestInterface.calculation(6, 7, "TestString");
    }
}
