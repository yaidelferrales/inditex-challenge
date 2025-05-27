package com.inditex.challenge.pricing.domain.model.enums;

import lombok.Getter;

@Getter
public enum Currency {
    USD("US Dollar", "$", 2),
    EUR("Euro", "€", 2),
    JPY("Japanese Yen", "¥", 0);

    private final String displayName;
    private final String symbol;
    private final int decimalPlaces;

    Currency(String displayName, String symbol, int decimalPlaces) {
        this.displayName = displayName;
        this.symbol = symbol;
        this.decimalPlaces = decimalPlaces;
    }

}
