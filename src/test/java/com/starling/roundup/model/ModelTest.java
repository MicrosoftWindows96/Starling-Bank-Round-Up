package com.starling.roundup.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.starling.roundup.client.StarlingApiClient;
import com.starling.roundup.config.ApiConfig;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.starling.roundup.RoundupApplication.accessToken;

public class ModelTest {
    public static void main(String[] args) {
        String baseUrl = "https://api-sandbox.starlingbank.com";

        ApiConfig config = new ApiConfig(baseUrl, accessToken);
        StarlingApiClient apiClient = new StarlingApiClient(config);

        try {
            // Test Account model
            System.out.println("Testing Account model...");
            Map<String, List<Account>> accountsResponse =
                    apiClient.getWithType("/api/v2/accounts",
                            new TypeReference<Map<String, List<Account>>>() {});

            List<Account> accounts = accountsResponse.get("accounts");
            System.out.println("First account: " + accounts.get(0));

            if (accounts != null && !accounts.isEmpty()) {
                Account account = accounts.get(0);

                // Test Money model with Balance API
                System.out.println("\nTesting Money model with Balance API...");
                String balanceEndpoint = "/api/v2/accounts/" + account.getAccountUid() + "/balance";
                Map<String, Object> balanceResponse =
                        apiClient.get(balanceEndpoint, Map.class);

                Map<String, Object> amountMap = (Map<String, Object>) balanceResponse.get("amount");
                Money amount = new Money(
                        (String) amountMap.get("currency"),
                        Long.valueOf(amountMap.get("minorUnits").toString())
                );

                System.out.println("Balance amount: " + amount);
            }

            System.out.println("Model testing completed successfully!");

        } catch (IOException | InterruptedException e) {
            System.err.println("Error testing models: " + e.getMessage());
            e.printStackTrace();
        }
    }
}