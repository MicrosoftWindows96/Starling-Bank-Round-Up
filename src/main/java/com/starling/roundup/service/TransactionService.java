package com.starling.roundup.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.starling.roundup.client.StarlingApiClient;
import com.starling.roundup.model.Transaction;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class TransactionService {
    private final StarlingApiClient apiClient;

    public TransactionService(StarlingApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<Transaction> getTransactions(String accountUid, String categoryUid, OffsetDateTime changesSince)
            throws IOException, InterruptedException {
        String endpoint = String.format(
                "/api/v2/feed/account/%s/category/%s?changesSince=%s",
                accountUid, categoryUid, changesSince
        );

        // The API returns a wrapper object with the transactions as a "feedItems" array
        Map<String, List<Transaction>> response = apiClient.getWithType(
                endpoint,
                new TypeReference<Map<String, List<Transaction>>>() {}
        );

        return response.get("feedItems");
    }

    public long calculateTotalRoundUp(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> "OUT".equals(t.getDirection())) // Only consider outgoing transactions
                .mapToLong(Transaction::calculateRoundUp)
                .sum();
    }
}