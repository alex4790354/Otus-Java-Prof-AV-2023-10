package ru.otus.service;

import ru.otus.model.Banknote;
import ru.otus.model.BanknoteSet;

import java.util.List;

public interface Atm {
    void depositCash(Banknote banknote, int banknotesAmount);
    List<BanknoteSet> withdrawCash(int totalCashValue);
}
