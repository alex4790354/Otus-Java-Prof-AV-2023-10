package ru.otus.service;

import lombok.AllArgsConstructor;
import ru.otus.model.Banknote;
import ru.otus.model.BanknoteSet;
import java.util.*;

@AllArgsConstructor
public class AtmImpl implements Atm {
    Map<Banknote, Integer> cashbox;

    public AtmImpl() {
        this.cashbox = new HashMap<>();
    }


    public void depositCash(Banknote banknote, int banknotesAmount) {
        cashbox.merge(banknote, banknotesAmount, Integer::sum);
    }


    public List<BanknoteSet> withdrawCash(int totalCashValue) {
        List<BanknoteSet> result = new ArrayList<>();
        int remainingAmount = totalCashValue;
        Banknote[] banknotes = Banknote.values();
        Arrays.sort(banknotes, (a, b) -> b.getValue() - a.getValue());

        for (Banknote banknote : banknotes) {
            if (remainingAmount <= 0) break;
            int banknoteValue = banknote.getValue();
            if (cashbox.containsKey(banknote) && cashbox.get(banknote) > 0) {
                int maxPossible = remainingAmount / banknoteValue;
                int count = Math.min(maxPossible, cashbox.get(banknote));

                if (count > 0) {
                    result.add(new BanknoteSet(banknote, count));
                    remainingAmount -= count * banknoteValue;
                    cashbox.put(banknote, cashbox.get(banknote) - count);
                }
            }
        }

        if (remainingAmount > 0) {
            System.out.println("Unable to dispense the exact amount requested.");
            return Collections.emptyList(); // Не удалось выдать запрошенную сумму
        }
        return result;
    }
}
