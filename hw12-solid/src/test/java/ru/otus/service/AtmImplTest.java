package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.model.Banknote;
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
        atm.depositCash(Banknote.BANKNOTE_5000, 5);
        atm.depositCash(Banknote.BANKNOTE_2000, 5);
        atm.depositCash(Banknote.BANKNOTE_1000, 5);
        atm.depositCash(Banknote.BANKNOTE_500, 5);
        atm.depositCash(Banknote.BANKNOTE_100, 5);
        atm.depositCash(Banknote.BANKNOTE_50, 5);
    }

    @Test
    void withdrawCash_shouldReturnCorrectBanknotes() {

        List<BanknoteSet> banknoteSets = atm.withdrawCash(13650);
        assertThat(banknoteSets).containsExactly(
                new BanknoteSet(Banknote.BANKNOTE_5000, 2),
                new BanknoteSet(Banknote.BANKNOTE_2000, 1),
                new BanknoteSet(Banknote.BANKNOTE_1000, 1),
                new BanknoteSet(Banknote.BANKNOTE_500, 1),
                new BanknoteSet(Banknote.BANKNOTE_100, 1),
                new BanknoteSet(Banknote.BANKNOTE_50, 1)
        );
    }

    @Test
    void withdrawCash_shouldHandleInvalidAmountRequested() {

        atm.withdrawCash(1001);
        assertThat(outContent.toString()).contains("Unable to dispense the exact amount requested.");
    }

    @Test
    void withdrawCash_shouldHandleRequestGreaterThanAvailableTotal() {

        atm.withdrawCash(10_000_000);
        assertThat(outContent.toString()).contains("Unable to dispense the exact amount requested.");
    }

    @org.junit.jupiter.api.AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}