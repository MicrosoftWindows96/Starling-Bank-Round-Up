package com.starling.roundup.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.starling.roundup.client.StarlingApiClient;
import com.starling.roundup.model.Account;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AccountService {
    private final StarlingApiClient apiClient;

    public AccountService(StarlingApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<Account> getAccounts() throws IOException, InterruptedException {
        Map<String, List<Account>> response = apiClient.getWithType(
                "/api/v2/accounts",
                new TypeReference<Map<String, List<Account>>>() {}
        );
        return response.get("accounts");
    }

    public Account getPrimaryAccount() throws IOException, InterruptedException {
        List<Account> accounts = getAccounts();
        if (accounts == null || accounts.isEmpty()) {
            throw new RuntimeException("No accounts found for customer");
        }
        return accounts.get(0);
    }
}