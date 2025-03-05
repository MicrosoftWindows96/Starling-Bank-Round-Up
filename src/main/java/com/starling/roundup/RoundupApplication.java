package com.starling.roundup;

import com.starling.roundup.client.StarlingApiClient;
import com.starling.roundup.config.ApiConfig;
import com.starling.roundup.model.Money;
import com.starling.roundup.service.AccountService;
import com.starling.roundup.service.RoundUpService;
import com.starling.roundup.service.SavingsGoalService;
import com.starling.roundup.service.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RoundupApplication {

    public static String accessToken;

    static {
        accessToken = "TOKEN HERE";
    }

    public static void main(String[] args) {
        SpringApplication.run(RoundupApplication.class, args);
    }

    @Bean
    public ApiConfig apiConfig() {
        String baseUrl = "https://api-sandbox.starlingbank.com";

        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalArgumentException("STARLING_ACCESS_TOKEN environment variable must be set");
        }

        return new ApiConfig(baseUrl, accessToken);
    }

    @Bean
    public StarlingApiClient starlingApiClient(ApiConfig apiConfig) {
        return new StarlingApiClient(apiConfig);
    }

    @Bean
    public AccountService accountService(StarlingApiClient apiClient) {
        return new AccountService(apiClient);
    }

    @Bean
    public TransactionService transactionService(StarlingApiClient apiClient) {
        return new TransactionService(apiClient);
    }

    @Bean
    public SavingsGoalService savingsGoalService(StarlingApiClient apiClient) {
        return new SavingsGoalService(apiClient);
    }

    @Bean
    public RoundUpService roundUpService(
            AccountService accountService,
            TransactionService transactionService,
            SavingsGoalService savingsGoalService) {
        return new RoundUpService(accountService, transactionService, savingsGoalService);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoundUpService roundUpService) {
        return args -> {
            System.out.println("Starting Starling Bank Round-Up Service");
            System.out.println("Performing weekly round-up...");

            Money roundUpAmount = roundUpService.performRoundUp();

            System.out.println("Round-up completed successfully!");
            System.out.println("Total amount rounded up: " + roundUpAmount);
        };
    }
}