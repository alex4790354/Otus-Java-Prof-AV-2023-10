package ru.otus.model;

import lombok.Getter;

@Getter
public enum BanknotesNominal {
    BANKNOTE_50(50),
    BANKNOTE_100(100),
    BANKNOTE_500(500),
    BANKNOTE_1000(1000),
    BANKNOTE_2000(2000),
    BANKNOTE_5000(5000);

    private final int value;

    BanknotesNominal(int value) {
        this.value = value;
    }
}
