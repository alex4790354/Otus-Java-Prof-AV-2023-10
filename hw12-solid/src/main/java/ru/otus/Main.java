package ru.otus;

import ru.otus.service.AtmImpl;
import ru.otus.model.Banknote;
import ru.otus.model.BanknoteSet;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AtmImpl atm = new AtmImpl();
        atm.depositCash(Banknote.BANKNOTE_50, 10);
        atm.depositCash(Banknote.BANKNOTE_100, 10);
        atm.depositCash(Banknote.BANKNOTE_500, 10);
        atm.depositCash(Banknote.BANKNOTE_1000, 10);
        atm.depositCash(Banknote.BANKNOTE_2000, 10);
        atm.depositCash(Banknote.BANKNOTE_5000, 10);

        List<BanknoteSet> cash = atm.withdrawCash(13650);
        for (BanknoteSet set : cash) {
            System.out.println(set.getBanknote() + ", " + set.getAmount());
        }
    }
}
