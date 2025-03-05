package com.starling.roundup.model;

public class CreateSavingsGoalRequest {
    private String name;
    private String currency;
    private Money target;

    public CreateSavingsGoalRequest(String name, String currency, Money target) {
        this.name = name;
        this.currency = currency;
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public Money getTarget() {
        return target;
    }
}