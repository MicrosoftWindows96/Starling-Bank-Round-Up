package com.starling.roundup.service;

import com.starling.roundup.model.Account;
import com.starling.roundup.model.Money;
import com.starling.roundup.model.SavingsGoal;
import com.starling.roundup.model.Transaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class RoundUpService {
    private static final String ROUND_UP_GOAL_NAME = "Round-Up Savings";
    private static final String PROCESSED_TXN_FILE = "processed_transactions.txt";

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final SavingsGoalService savingsGoalService;
    private final Set<String> processedTransactions;

    public RoundUpService(
            AccountService accountService,
            TransactionService transactionService,
            SavingsGoalService savingsGoalService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.savingsGoalService = savingsGoalService;
        this.processedTransactions = loadProcessedTransactions();
    }

    public Money performRoundUp() throws IOException, InterruptedException {
        // Get account info
        Account account = accountService.getPrimaryAccount();
        String accountUid = account.getAccountUid();
        String categoryUid = account.getDefaultCategory();

        // Calculate date range for the past week
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime weekAgo = now.minus(7, ChronoUnit.DAYS);

        // Get transactions for the past week
        List<Transaction> transactions = transactionService.getTransactions(
                accountUid, categoryUid, weekAgo);

        // Filter out already processed transactions
        List<Transaction> unprocessedTransactions = transactions.stream()
                .filter(t -> !processedTransactions.contains(t.getFeedItemUid()))
                .collect(Collectors.toList());

        System.out.println("Found " + unprocessedTransactions.size() + " new transactions to process");

        // Calculate total round-up amount for unprocessed transactions
        long roundUpAmount = transactionService.calculateTotalRoundUp(unprocessedTransactions);

        if (roundUpAmount <= 0) {
            return new Money(account.getCurrency(), 0L);
        }

        // Find or create a savings goal
        UUID savingsGoalUid = findOrCreateSavingsGoal(accountUid, account.getCurrency());

        // Transfer round-up amount to savings goal
        Money transferAmount = new Money(account.getCurrency(), roundUpAmount);
        boolean success = savingsGoalService.addMoneyToSavingsGoal(
                accountUid, savingsGoalUid, transferAmount);

        if (success) {
            // Mark transactions as processed and save to file
            unprocessedTransactions.forEach(t -> {
                if (t.getFeedItemUid() != null) {
                    processedTransactions.add(t.getFeedItemUid());
                }
            });
            saveProcessedTransactions();
        }

        return transferAmount;
    }

    private Set<String> loadProcessedTransactions() {
        Set<String> txnIds = new HashSet<>();
        Path path = Paths.get(PROCESSED_TXN_FILE);

        if (Files.exists(path)) {
            try {
                txnIds = new HashSet<>(Files.readAllLines(path));
                System.out.println("Loaded " + txnIds.size() + " processed transactions");
            } catch (IOException e) {
                System.err.println("Error loading processed transactions: " + e.getMessage());
            }
        } else {
            System.out.println("No processed transactions file found, creating new tracking");
        }

        return txnIds;
    }

    private void saveProcessedTransactions() {
        Path path = Paths.get(PROCESSED_TXN_FILE);

        try {
            Files.write(
                    path,
                    processedTransactions,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
            System.out.println("Saved " + processedTransactions.size() + " processed transactions");
        } catch (IOException e) {
            System.err.println("Error saving processed transactions: " + e.getMessage());
        }
    }

    private UUID findOrCreateSavingsGoal(String accountUid, String currency)
            throws IOException, InterruptedException {
        // Try finding existing round-up savings goal
        List<SavingsGoal> savingsGoals = savingsGoalService.getSavingsGoals(accountUid);

        for (SavingsGoal goal : savingsGoals) {
            if (ROUND_UP_GOAL_NAME.equals(goal.getName())) {
                return goal.getSavingsGoalUid();
            }
        }

        // Create new savings goal if none exists
        Money target = new Money(currency, 1000000L); // £10,000
        return savingsGoalService.createSavingsGoal(
                accountUid, ROUND_UP_GOAL_NAME, currency, target);
    }
}