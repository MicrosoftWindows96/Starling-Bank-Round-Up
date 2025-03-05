package com.starling.roundup.model;

import java.util.UUID;

public class AddMoneyRequest {
    private final UUID transferUid;
    private final Money amount;

    public AddMoneyRequest(Money amount) {
        this.transferUid = UUID.randomUUID();
        this.amount = amount;
    }

    public UUID getTransferUid() {
        return transferUid;
    }

    public Money getAmount() {
        return amount;
    }
}