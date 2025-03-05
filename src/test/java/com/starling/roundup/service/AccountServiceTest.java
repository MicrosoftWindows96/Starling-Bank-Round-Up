package com.starling.roundup.service;

import com.starling.roundup.client.StarlingApiClient;
import com.starling.roundup.config.ApiConfig;
import com.starling.roundup.model.Account;

import java.io.IOException;
import java.util.List;

import static com.starling.roundup.RoundupApplication.accessToken;

public class AccountServiceTest {
    public static void main(String[] args) {
        String baseUrl = "https://api-sandbox.starlingbank.com";

        ApiConfig config = new ApiConfig(baseUrl, accessToken);
        StarlingApiClient apiClient = new StarlingApiClient(config);
        AccountService accountService = new AccountService(apiClient);

        try {
            // Test getting all accounts
            System.out.println("Testing getAccounts()...");
            List<Account> accounts = accountService.getAccounts();
            System.out.println("Found " + accounts.size() + " accounts");
            accounts.forEach(account -> System.out.println("- " + account));

            // Test getting primary account
            System.out.println("\nTesting getPrimaryAccount()...");
            Account primaryAccount = accountService.getPrimaryAccount();
            System.out.println("Primary account: " + primaryAccount);

            System.out.println("\nAccount UID: " + primaryAccount.getAccountUid());
            System.out.println("Default Category: " + primaryAccount.getDefaultCategory());

            System.out.println("\nAccountService tests completed successfully!");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error testing account service: " + e.getMessage());
            e.printStackTrace();
        }
    }
}