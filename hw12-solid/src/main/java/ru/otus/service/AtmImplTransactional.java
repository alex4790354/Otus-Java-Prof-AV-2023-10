package ru.otus.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.exception.WithdrawException;
import ru.otus.model.BanknotesNominal;
import ru.otus.model.BanknoteSet;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Getter
@AllArgsConstructor
public class AtmImplTransactional implements Atm {
    private final Map<BanknotesNominal, Integer> cashBox;
    private static final Logger logger = LoggerFactory.getLogger(AtmImplTransactional.class);

    public AtmImplTransactional() {
        this.cashBox = new EnumMap<>(BanknotesNominal.class);
        for (BanknotesNominal nominal : BanknotesNominal.values()) {
            cashBox.put(nominal, 0);
        }
    }


    public void depositCash(BanknotesNominal banknotesNominal, int banknotesAmount) {
        int currentAmount = cashBox.getOrDefault(banknotesNominal, 0);
        cashBox.put(banknotesNominal, currentAmount + banknotesAmount);
    }


    @Transactional
    public List<BanknoteSet> withdrawCash(int totalCashValue) {
        List<BanknoteSet> result = new ArrayList<>();
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
}
