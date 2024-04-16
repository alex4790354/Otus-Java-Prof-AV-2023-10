package ru.otus.generics;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;


public class CustomerService {

    private final NavigableMap<Customer, String> sortedCustomers;

    public CustomerService() {
        this.sortedCustomers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> origin = sortedCustomers.firstEntry();
        return copy(origin);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        Map.Entry<Customer, String> origin = sortedCustomers.higherEntry(customer);
        return copy(origin);
    }

    public void add(Customer customer, String data) {
        sortedCustomers.put(customer, data);
    }

    private Map.Entry<Customer, String> copy(Map.Entry<Customer, String> origin) {
        if (origin == null) {
            return null;
        }
        return new Map.Entry<>() {
            @Override
            public Customer getKey() {
                return origin.getKey().copy();
            }

            @Override
            public String getValue() {
                return origin.getValue();
            }

            @Override
            public String setValue(String value) {
                return origin.setValue(value);
            }
        };
    }
}
