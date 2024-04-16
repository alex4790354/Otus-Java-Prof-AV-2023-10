package ru.otus;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.util.List;

@SuppressWarnings("java:S106")
public class HelloOtus {
    public static void main(String[] args) {

        List<String> myList = Lists.newArrayList("11", "22", "44", "33");
        String result = Joiner.on(", ").join(myList);
        System.out.println(result);
    }
}
