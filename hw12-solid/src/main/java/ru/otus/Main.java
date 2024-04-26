package ru.otus;

import ru.otus.model.BanknotesNominal;
import ru.otus.model.BanknoteSet;
import ru.otus.service.AtmImplTransactional;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        AtmImplTransactional atm = new AtmImplTransactional();
        atm.depositCash(BanknotesNominal.BANKNOTE_50, 10);
        atm.depositCash(BanknotesNominal.BANKNOTE_100, 10);
        atm.depositCash(BanknotesNominal.BANKNOTE_500, 10);
        atm.depositCash(BanknotesNominal.BANKNOTE_1000, 10);
        atm.depositCash(BanknotesNominal.BANKNOTE_2000, 10);
        atm.depositCash(BanknotesNominal.BANKNOTE_5000, 10);

        List<BanknoteSet> cash = atm.withdrawCash(13_650_000);
        for (BanknoteSet set : cash) {
            System.out.println(set.getBanknote() + ", " + set.getAmount());
        }

        Map<BanknotesNominal, Integer> cashBox = atm.getCashBox();
        for (Map.Entry<BanknotesNominal, Integer> entry : cashBox.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
