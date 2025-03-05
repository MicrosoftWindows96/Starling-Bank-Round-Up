package com.starling.roundup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    private String feedItemUid;
    private String categoryUid;
    private Money amount;
    private Money sourceAmount;
    private String direction;
    private OffsetDateTime updatedAt;
    private OffsetDateTime transactionTime;
    private OffsetDateTime settlementTime;
    private String source;
    private String status;
    private String counterPartyType;
    private String counterPartyName;
    private String counterPartySubEntityName;
    private String counterPartySubEntityIdentifier;
    private String counterPartySubEntitySubIdentifier;
    private String reference;
    private String country;
    private String spendingCategory;
    private boolean hasAttachment;
    private boolean hasReceipt;

    // Default constructor for deserialization
    public Transaction() {
    }

    public String getFeedItemUid() {
        return feedItemUid;
    }

    public void setFeedItemUid(String feedItemUid) {
        this.feedItemUid = feedItemUid;
    }

    public String getCategoryUid() {
        return categoryUid;
    }

    public void setCategoryUid(String categoryUid) {
        this.categoryUid = categoryUid;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public Money getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(Money sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OffsetDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(OffsetDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public OffsetDateTime getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(OffsetDateTime settlementTime) {
        this.settlementTime = settlementTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCounterPartyType() {
        return counterPartyType;
    }

    public void setCounterPartyType(String counterPartyType) {
        this.counterPartyType = counterPartyType;
    }

    public String getCounterPartyName() {
        return counterPartyName;
    }

    public void setCounterPartyName(String counterPartyName) {
        this.counterPartyName = counterPartyName;
    }

    public String getCounterPartySubEntityName() {
        return counterPartySubEntityName;
    }

    public void setCounterPartySubEntityName(String counterPartySubEntityName) {
        this.counterPartySubEntityName = counterPartySubEntityName;
    }

    public String getCounterPartySubEntityIdentifier() {
        return counterPartySubEntityIdentifier;
    }

    public void setCounterPartySubEntityIdentifier(String counterPartySubEntityIdentifier) {
        this.counterPartySubEntityIdentifier = counterPartySubEntityIdentifier;
    }

    public String getCounterPartySubEntitySubIdentifier() {
        return counterPartySubEntitySubIdentifier;
    }

    public void setCounterPartySubEntitySubIdentifier(String counterPartySubEntitySubIdentifier) {
        this.counterPartySubEntitySubIdentifier = counterPartySubEntitySubIdentifier;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSpendingCategory() {
        return spendingCategory;
    }

    public void setSpendingCategory(String spendingCategory) {
        this.spendingCategory = spendingCategory;
    }

    public boolean isHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
    }

    public boolean isHasReceipt() {
        return hasReceipt;
    }

    public void setHasReceipt(boolean hasReceipt) {
        this.hasReceipt = hasReceipt;
    }

    public long calculateRoundUp() {
        if (!"OUT".equals(direction) || amount == null) {
            return 0;
        }

        if (counterPartyName != null && counterPartyName.contains("Round-Up Savings")) {
            return 0;
        }

        long minorUnits = amount.getMinorUnits();

        if (minorUnits % 100 == 0) {
            return 0;
        }

        return 100 - (minorUnits % 100);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "feedItemUid='" + feedItemUid + '\'' +
                ", amount=" + amount +
                ", direction='" + direction + '\'' +
                ", counterPartyName='" + counterPartyName + '\'' +
                ", transactionTime=" + transactionTime +
                ", status='" + status + '\'' +
                '}';
    }
}