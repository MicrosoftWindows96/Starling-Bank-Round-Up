package com.starling.roundup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SavingsGoal {
    private UUID savingsGoalUid;
    private String name;
    private Money target;
    private Money totalSaved;
    private String currency;

    public SavingsGoal() {
    }

    public SavingsGoal(String name, String currency, Money target) {
        this.name = name;
        this.currency = currency;
        this.target = target;
    }

    public UUID getSavingsGoalUid() {
        return savingsGoalUid;
    }

    public void setSavingsGoalUid(UUID savingsGoalUid) {
        this.savingsGoalUid = savingsGoalUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getTarget() {
        return target;
    }

    public void setTarget(Money target) {
        this.target = target;
    }

    public Money getTotalSaved() {
        return totalSaved;
    }

    public void setTotalSaved(Money totalSaved) {
        this.totalSaved = totalSaved;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "SavingsGoal{" +
                "savingsGoalUid=" + savingsGoalUid +
                ", name='" + name + '\'' +
                ", target=" + target +
                ", totalSaved=" + totalSaved +
                ", currency='" + currency + '\'' +
                '}';
    }
}