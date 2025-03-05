package com.starling.roundup.model;

public class Money {
    private String currency;
    private Long minorUnits;

    public Money() {
    }

    public Money(String currency, Long minorUnits) {
        this.currency = currency;
        this.minorUnits = minorUnits;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getMinorUnits() {
        return minorUnits;
    }

    public void setMinorUnits(Long minorUnits) {
        this.minorUnits = minorUnits;
    }

    public double toMajorUnits() {
        return minorUnits / 100.0;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", toMajorUnits(), currency);
    }
}