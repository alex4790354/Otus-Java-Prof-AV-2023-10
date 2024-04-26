package ru.otus.service;

import ru.otus.model.BanknotesNominal;
import ru.otus.model.BanknoteSet;

import java.util.List;

public interface Atm {
    void depositCash(BanknotesNominal banknote, int banknotesAmount);
    List<BanknoteSet> withdrawCash(int totalCashValue);
}
