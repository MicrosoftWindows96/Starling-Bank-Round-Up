package com.starling.roundup.client;

import com.starling.roundup.config.ApiConfig;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.starling.roundup.RoundupApplication.accessToken;

public class ApiClientTest {
    public static void main(String[] args) {
        String baseUrl = "https://api-sandbox.starlingbank.com";

        ApiConfig config = new ApiConfig(baseUrl, accessToken);
        StarlingApiClient apiClient = new StarlingApiClient(config);

        try {
            // Test accounts API
            System.out.println("Testing Accounts API...");
            Map<String, Object> accountsResponse = apiClient.get("/api/v2/accounts", Map.class);
            System.out.println(accountsResponse);

            // Extract accounts from the response
            List<Map<String, Object>> accounts = (List<Map<String, Object>>) accountsResponse.get("accounts");
            if (accounts == null || accounts.isEmpty()) {
                System.out.println("No accounts found");
                return;
            }

            String accountUid = (String) accounts.get(0).get("accountUid");
            String categoryUid = (String) accounts.get(0).get("defaultCategory");

            System.out.println("Account UID: " + accountUid);
            System.out.println("Default Category UID: " + categoryUid);

            // Test balance API
            System.out.println("\nTesting Balance API...");
            String balanceEndpoint = "/api/v2/accounts/" + accountUid + "/balance";
            Object balanceResponse = apiClient.get(balanceEndpoint, Object.class);
            System.out.println(balanceResponse);

            System.out.println("API client working correctly");

        } catch (IOException | InterruptedException e) {
            System.err.println("Error testing API client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}