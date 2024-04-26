package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.otus.exception.WithdrawException;
import ru.otus.model.BanknoteSet;
import ru.otus.model.BanknotesNominal;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AtmImplTransactionalTest {
    private AtmImplTransactional atm;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        atm = new AtmImplTransactional();
        atm.depositCash(BanknotesNominal.BANKNOTE_5000, 5);
        atm.depositCash(BanknotesNominal.BANKNOTE_2000, 5);
        atm.depositCash(BanknotesNominal.BANKNOTE_1000, 5);
        atm.depositCash(BanknotesNominal.BANKNOTE_500, 5);
        atm.depositCash(BanknotesNominal.BANKNOTE_100, 5);
        atm.depositCash(BanknotesNominal.BANKNOTE_50, 5);
    }

    @Test
    void withdrawCash_Public_shouldReturnCorrectBanknotes() {

        List<BanknoteSet> banknoteSets = atm.withdrawCash(13650);
        assertThat(banknoteSets).containsExactly(
                new BanknoteSet(BanknotesNominal.BANKNOTE_5000, 2),
                new BanknoteSet(BanknotesNominal.BANKNOTE_2000, 1),
                new BanknoteSet(BanknotesNominal.BANKNOTE_1000, 1),
                new BanknoteSet(BanknotesNominal.BANKNOTE_500, 1),
                new BanknoteSet(BanknotesNominal.BANKNOTE_100, 1),
                new BanknoteSet(BanknotesNominal.BANKNOTE_50, 1)
        );
    }


    @Test
    void withdrawCash_Public_shouldHandleInvalidAmountRequested() {
        assertThrows(WithdrawException.class, () -> {
            atm.withdrawCash(999); // Assuming 999 is not valid
        }, "Expected withdrawCash to throw, but it did not");
    }


    @Test
    void withdrawCash_Public_shouldHandleRequestGreaterThanAvailableTotal() {
        assertThrows(WithdrawException.class, () -> {
            atm.withdrawCash(10_000_000); // An exaggerated amount that ATM cannot possibly fulfill
        }, "Expected withdrawCash to throw, but it did not");
    }


    @Disabled
    @Test
    void withdrawCash_Public_shouldNotChangeBanknotesIfRequestCannotBeFulfilled() {
        // Attempt to withdraw more than the total available amount
        int initialAmount = atm.getCashBox().get(BanknotesNominal.BANKNOTE_5000); // Capture initial state
        assertThrows(WithdrawException.class, () -> {
            atm.withdrawCash(5_000_001); // Request slightly more than possible
        });

        int postAttemptAmount = atm.getCashBox().get(BanknotesNominal.BANKNOTE_5000);
        assertEquals(initialAmount, postAttemptAmount, "Banknotes should not change if withdrawal fails");
    }


    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}