package com.starling.roundup.service;

import com.starling.roundup.client.StarlingApiClient;
import com.starling.roundup.config.ApiConfig;
import com.starling.roundup.model.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.starling.roundup.RoundupApplication.accessToken;

public class SavingsGoalServiceTest {
    public static void main(String[] args) {
        String baseUrl = "https://api-sandbox.starlingbank.com";

        ApiConfig config = new ApiConfig(baseUrl, accessToken);
        StarlingApiClient apiClient = new StarlingApiClient(config);
        AccountService accountService = new AccountService(apiClient);
        SavingsGoalService savingsGoalService = new SavingsGoalService(apiClient);

        try {
            // Get account information
            Account account = accountService.getPrimaryAccount();
            String accountUid = account.getAccountUid();

            System.out.println("Testing savings goals for account: " + accountUid);

            // Test creating a savings goal
            String goalName = "Round-Up Savings " + System.currentTimeMillis();
            Money targetAmount = new Money(account.getCurrency(), 100000L); // £1000.00

            System.out.println("\nCreating savings goal: " + goalName);
            UUID savingsGoalUid = savingsGoalService.createSavingsGoal(
                    accountUid, goalName, account.getCurrency(), targetAmount);

            System.out.println("Created savings goal: " + savingsGoalUid);

            // Test adding money to the savings goal
            Money transferAmount = new Money(account.getCurrency(), 158L); // £1.58

            System.out.println("\nAdding " + transferAmount + " to savings goal");
            boolean success = savingsGoalService.addMoneyToSavingsGoal(
                    accountUid, savingsGoalUid, transferAmount);

            System.out.println("Transfer " + (success ? "successful" : "failed"));

            // Test retrieving all savings goals
            System.out.println("\nRetrieving all savings goals");
            List<SavingsGoal> savingsGoals = savingsGoalService.getSavingsGoals(accountUid);

            System.out.println("Found " + savingsGoals.size() + " savings goals:");
            for (SavingsGoal goal : savingsGoals) {
                System.out.println("- " + goal);
            }

            System.out.println("\nSavingsGoalService tests completed successfully!");

        } catch (IOException | InterruptedException e) {
            System.err.println("Error testing savings goals: " + e.getMessage());
            e.printStackTrace();
        }
    }
}