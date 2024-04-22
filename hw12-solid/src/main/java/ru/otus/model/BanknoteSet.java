package ru.otus.model;

import lombok.Data;

@Data
public class BanknoteSet {
    private final Banknote banknote;
    private final int amount;
}
