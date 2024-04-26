package ru.otus.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.model.BanknotesNominal;
import ru.otus.model.BanknoteSet;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;


class AtmImplTest {
    private AtmImpl atm;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        atm = new AtmImpl();
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

        atm.withdrawCash(1001);
        assertThat(outContent.toString()).contains("Unable to dispense the exact amount requested.");
    }

    @Test
    void withdrawCash_Public_shouldHandleRequestGreaterThanAvailableTotal() {

        atm.withdrawCash(10_000_000);
        assertThat(outContent.toString()).contains("Unable to dispense the exact amount requested.");
    }

    @Test
    void withdrawCash_Public_shouldNotChangeBanknotesIfRequestCannotBeFulfilled() {
        // Attempt to withdraw more than the total available amount
        atm.withdrawCash(5_000_001);

        // Assert that the count of each banknote remains the same as initially deposited
        assertThat(atm.getCashBox().get(BanknotesNominal.BANKNOTE_5000)).isEqualTo(5);
        assertThat(atm.getCashBox().get(BanknotesNominal.BANKNOTE_2000)).isEqualTo(5);
        assertThat(atm.getCashBox().get(BanknotesNominal.BANKNOTE_1000)).isEqualTo(5);
        assertThat(atm.getCashBox().get(BanknotesNominal.BANKNOTE_500)).isEqualTo(5);
        assertThat(atm.getCashBox().get(BanknotesNominal.BANKNOTE_100)).isEqualTo(5);
        assertThat(atm.getCashBox().get(BanknotesNominal.BANKNOTE_50)).isEqualTo(5);
    }


    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}