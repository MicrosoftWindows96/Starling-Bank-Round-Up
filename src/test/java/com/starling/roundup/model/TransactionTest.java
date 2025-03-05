package com.starling.roundup.model;

import com.starling.roundup.client.StarlingApiClient;
import com.starling.roundup.config.ApiConfig;
import com.starling.roundup.service.AccountService;
import com.starling.roundup.service.TransactionService;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TransactionTest {
    public static void main(String[] args) {
        String accessToken = "eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_21T23KjMAz9lQ7PVScECJC3vu0P7AcISU48BZuxTbqdnf33NTGEkukb5xxdjizxN9PeZ-cMRw0sg33zAV2vzaVD8_FGdsheMz91MaLkSgkWFTTdsYSS-ATNSWqQjhQdSGFZcAyWP2N2zusyz9u2ObavmcawEHlRzgQS2cmEX7Zncb81L7WrnDqgQ11BWagG2vzA0BwUH45FwyRtrB3sh5iUQW3HXdMwVAdGKFWdQ5cXR6hLVnlXlVXFRcyIY70Tifdbn1MVE4jbGkqkGjrBDvjYtvUpV11-LOeByY4yP0pyCte7VTA4yNkJ8suTEL7GJ0GzmKCVFrfne-3DjlkAs4smz8I6PEBSQkC6DvKI3PCn00FecApX67SPKwNtWN80T9in4A57NLRYI3QMZE1wtk-NZmbRrFHaDRi0NWAVqMnwYoAmH-ywziED6iV7QMMY5MzSS_SxwnvYIAEjwjNFOIsrvmeO-CWySgksRRLYgkAPeFlqJm37hODQeKTZ84OG3lKcfqudCLDzMzyzS5azSvdrq9R7R92jnJDoMeyA30tpHx5vcRUeLnbzseOWUXfcvc53Jg2n4rP_UGITf6i1iakoXYWnXhji2NsZeQkhDjiNCxxxPZP4_8crisdkHX9rv2fXvnv2h3ywn-bBB5kNAPnbMzWyStT3nd5X8bzk7N9_06fPzbIEAAA.jUlcj5be5D7zpG8MMznCGZzZ4P22P_tYqK5BGfNX3zp7__kSZNrfXNKBptjb2j2EuvYE9nalZxxZwjP9FEK_2rCFLGkf2iBnT9dmBSANnfftyh3cJXvB4orEJAmflOqdI7Gu8aAEn4FHXkNiyoyteDxtabTmafh3udzQ81D-W-GBJ_Q5EenqyaQsEbE46x7ZmhK6W0B-LO2fu_CPpTshDEoE2BWZuu4Nbqh85hcKC-Rf33dYYtrLaMFsq-ndsdjmAmPLiNe7bxNYXqWjzeoQkxKgVMpUbDQqUfX7VwZCZrPyNhVXJEfyZa-j8qfDAdlTp1wOnj3qrCIz65xIzpbmzFR5tyrpfPSZk-XNYmjLGAcQFeyvF8uGMpbZfLWC6Q48T9H1iV7ZOFILOjiLi4OHQs07O7M7lV5pTYNFmNb4kdqHNQiBuKK7-1d-JPAT6nQoO3ZKys-HSenC30d2ZYsI--03g39QFHybLF99qXI5LOTNTOe7WfR4Iw6A4wzGW3-y8WmU9bqlN1nnn4cJFXh9pslLwvnlFaTWVHrGTKN31b1mye37Jhk9d9-j2OoqSPkgS_hwz1zQgKjHkl6NWfs2-FxVRsfc10Tq8rbJgmV5NOgy85w5QO-8vW2wjv9TbJf_6S1X1Tp39fkQsAQOnvAzDgbUZoUIUs8Wbf1VIc8OKHc";
        String baseUrl = "https://api-sandbox.starlingbank.com";

        ApiConfig config = new ApiConfig(baseUrl, accessToken);
        StarlingApiClient apiClient = new StarlingApiClient(config);
        AccountService accountService = new AccountService(apiClient);
        TransactionService transactionService = new TransactionService(apiClient);

        try {
            // Get account information
            Account account = accountService.getPrimaryAccount();
            String accountUid = account.getAccountUid();
            String categoryUid = account.getDefaultCategory();

            // Get transactions for the past week
            OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
            OffsetDateTime weekAgo = now.minus(7, ChronoUnit.DAYS);

            System.out.println("Getting transactions since: " + weekAgo);
            List<Transaction> transactions = transactionService.getTransactions(
                    accountUid, categoryUid, weekAgo);

            System.out.println("Found " + transactions.size() + " transactions");

            // Test round-up calculation
            System.out.println("\nTransaction details and round-ups:");
            for (Transaction transaction : transactions) {
                if ("OUT".equals(transaction.getDirection())) {
                    long roundUp = transaction.calculateRoundUp();
                    System.out.printf("- %s: %s, Round-up: %d pence%n",
                            transaction.getCounterPartyName(),
                            transaction.getAmount(),
                            roundUp);
                }
            }

            // Calculate total using service
            long totalRoundUp = transactionService.calculateTotalRoundUp(transactions);
            System.out.println("\nTotal round-up amount: " + totalRoundUp + " pence (£" + (totalRoundUp/100.0) + ")");
            System.out.println("Transaction tests completed successfully!");

        } catch (IOException | InterruptedException e) {
            System.err.println("Error testing transactions: " + e.getMessage());
            e.printStackTrace();
        }
    }
}