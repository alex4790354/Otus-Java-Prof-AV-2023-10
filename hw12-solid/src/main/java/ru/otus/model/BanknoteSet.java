package ru.otus.model;

import lombok.Data;

@Data
public class BanknoteSet {
    private final BanknotesNominal banknote;
    private final int amount;
}
