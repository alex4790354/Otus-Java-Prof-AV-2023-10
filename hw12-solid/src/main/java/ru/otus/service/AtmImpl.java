package ru.otus.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.exception.WithdrawException;
import ru.otus.model.BanknoteSet;
import ru.otus.model.BanknotesNominal;
import java.util.*;


@Getter
@AllArgsConstructor
public class AtmImpl implements Atm {
    private final Map<BanknotesNominal, Integer> cashBox;
    private static final Logger logger = LoggerFactory.getLogger(AtmImpl.class);

    public AtmImpl() {
        this.cashBox = new EnumMap<>(BanknotesNominal.class);
        for (BanknotesNominal nominal : BanknotesNominal.values()) {
            cashBox.put(nominal, 0);
        }
    }


    public void depositCash(BanknotesNominal banknotesNominal, int banknotesAmount) {
        int currentAmount = cashBox.getOrDefault(banknotesNominal, 0);
        cashBox.put(banknotesNominal, currentAmount + banknotesAmount);
    }


    public List<BanknoteSet> withdrawCash(int totalCashValue) {
        List<BanknoteSet> result = new ArrayList<>();
        int remainingAmount = totalCashValue;
        BanknotesNominal[] banknotes = BanknotesNominal.values();
        Arrays.sort(banknotes, Comparator.comparingInt(BanknotesNominal::getValue).reversed());

        if (!checkWithdrawCash(totalCashValue))
            return result;

        for (BanknotesNominal banknote : banknotes) {
            if (remainingAmount <= 0) break;
            int banknoteValue = banknote.getValue();
            if (cashBox.containsKey(banknote) && cashBox.get(banknote) > 0) {
                int maxPossible = remainingAmount / banknoteValue;
                int count = Math.min(maxPossible, cashBox.get(banknote));

                if (count > 0) {
                    result.add(new BanknoteSet(banknote, count));
                    remainingAmount -= count * banknoteValue;
                    cashBox.put(banknote, cashBox.get(banknote) - count);
                }
            }
        }

        if (remainingAmount > 0) {
            logger.error("Unable to dispense the exact amount requested.");
            throw new WithdrawException("Unable to dispense the exact amount requested.");
        }
        return result;
    }


    private boolean checkWithdrawCash(int totalCashValue) {
        int remainingAmount = totalCashValue;
        BanknotesNominal[] banknotes = BanknotesNominal.values();
        Arrays.sort(banknotes, Comparator.comparingInt(BanknotesNominal::getValue).reversed());

        for (BanknotesNominal banknote : banknotes) {
            if (remainingAmount <= 0) break;
            int banknoteValue = banknote.getValue();
            if (cashBox.containsKey(banknote) && cashBox.get(banknote) > 0) {
                int maxPossible = remainingAmount / banknoteValue;
                int count = Math.min(maxPossible, cashBox.get(banknote));
                if (count > 0) {
                    remainingAmount -= count * banknoteValue;
                }
            }
        }

        if (remainingAmount > 0) {
            logger.error("Unable to dispense the exact amount requested.");
            return false;
        }
        return true;
    }
}
