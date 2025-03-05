package com.starling.roundup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateSavingsGoalResponse {
    private UUID savingsGoalUid;
    private boolean success;

    public UUID getSavingsGoalUid() {
        return savingsGoalUid;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}