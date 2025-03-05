package com.starling.roundup.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.starling.roundup.client.StarlingApiClient;
import com.starling.roundup.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SavingsGoalService {
    private final StarlingApiClient apiClient;

    public SavingsGoalService(StarlingApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<SavingsGoal> getSavingsGoals(String accountUid) throws IOException, InterruptedException {
        String endpoint = String.format("/api/v2/account/%s/savings-goals", accountUid);

        Map<String, List<SavingsGoal>> response = apiClient.getWithType(
                endpoint,
                new TypeReference<Map<String, List<SavingsGoal>>>() {}
        );

        return response.get("savingsGoalList");
    }

    public UUID createSavingsGoal(String accountUid, String name, String currency, Money target)
            throws IOException, InterruptedException {
        String endpoint = String.format("/api/v2/account/%s/savings-goals", accountUid);

        CreateSavingsGoalRequest request = new CreateSavingsGoalRequest(name, currency, target);
        CreateSavingsGoalResponse response = apiClient.put(endpoint, request, CreateSavingsGoalResponse.class);

        if (!response.isSuccess()) {
            throw new IOException("Failed to create savings goal");
        }

        return response.getSavingsGoalUid();
    }

    public boolean addMoneyToSavingsGoal(String accountUid, UUID savingsGoalUid, Money amount)
            throws IOException, InterruptedException {
        String endpoint = String.format(
                "/api/v2/account/%s/savings-goals/%s/add-money/%s",
                accountUid, savingsGoalUid, UUID.randomUUID()
        );

        AddMoneyRequest request = new AddMoneyRequest(amount);
        AddMoneyResponse response = apiClient.put(endpoint, request, AddMoneyResponse.class);

        return response.isSuccess();
    }
}