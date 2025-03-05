package com.starling.roundup.service;

import com.starling.roundup.client.StarlingApiClient;
import com.starling.roundup.config.ApiConfig;
import com.starling.roundup.model.Money;

import java.io.IOException;

import static com.starling.roundup.RoundupApplication.accessToken;

public class RoundUpServiceTest {
    public static void main(String[] args) {
        String baseUrl = "https://api-sandbox.starlingbank.com";

        ApiConfig config = new ApiConfig(baseUrl, accessToken);
        StarlingApiClient apiClient = new StarlingApiClient(config);

        AccountService accountService = new AccountService(apiClient);
        TransactionService transactionService = new TransactionService(apiClient);
        SavingsGoalService savingsGoalService = new SavingsGoalService(apiClient);

        RoundUpService roundUpService = new RoundUpService(
                accountService, transactionService, savingsGoalService);

        try {
            System.out.println("Performing weekly round-up...");
            Money roundUpAmount = roundUpService.performRoundUp();

            System.out.println("Round-up completed!");
            System.out.println("Total amount rounded up: " + roundUpAmount);

        } catch (IOException | InterruptedException e) {
            System.err.println("Error performing round-up: " + e.getMessage());
            e.printStackTrace();
        }
    }
}