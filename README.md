# Starling Bank Round-Up Feature

This project implements a "round-up" feature for Starling Bank customers using the Starling Bank public API. The application rounds up transactions to the nearest pound and transfers the total to a dedicated savings goal, helping customers save for the future.

## Overview

The round-up feature helps customers save by collecting small amounts regularly without effort. For example, with spending of £4.35, £5.20, and £0.87, the round-up would be £1.58 (the sum of 65p, 80p, and 13p). This amount is automatically transferred to a savings goal.

This implementation:
- Retrieves account information
- Fetches transactions for the specified period (default: past week)
- Calculates round-up amounts
- Creates a savings goal if it doesn't exist
- Transfers the round-up amount to the savings goal
- Tracks processed transactions to avoid duplicates

## Features

- Retrieves account information from the Starling API
- Fetches transaction data for a specified time period
- Calculates round-up values for eligible transactions
- Creates and manages savings goals
- Transfers round-up amounts to the savings goal
- Tracks processed transactions to prevent duplicate round-ups
- Excludes internal transfers and savings transactions from round-up calculations

The application will:
1. Connect to the Starling API
2. Retrieve account information
3. Fetch transactions for the past week
4. Calculate round-up amounts
5. Create or use an existing "Round-Up Savings" goal
6. Transfer the total round-up amount to the savings goal
7. Track processed transactions to avoid duplicates

## Usage

Replace the authentication placeholder on line 21 of RoundupApplication.java with an active token.

```
public static String accessToken;

    static {
        accessToken = "TOKEN HERE";
    }
```

Simulate transactions via the Starling Bank developer portal and run the main method or test suite files as needed.

## Smart Processing

To prevent duplicate round-ups, the application tracks processed transactions:

1. Maintains a set of processed transaction IDs
2. Persists this set to a file (`processed_transactions.txt`)
3. Only processes transactions not already in the set
4. Adds transaction IDs to the set after successful processing

## API Endpoints Used

The application uses the following Starling Bank API endpoints:

1. **Accounts API**
   - `GET /api/v2/accounts`
   - Retrieves account information

2. **Transaction Feed API**
   - `GET /api/v2/feed/account/{accountUid}/category/{categoryUid}?changesSince={changesSince}`
   - Retrieves transaction feed items

3. **Savings Goals API**
   - `GET /api/v2/account/{accountUid}/savings-goals`
   - Retrieves existing savings goals
   - `PUT /api/v2/account/{accountUid}/savings-goals`
   - Creates a new savings goal
   - `PUT /api/v2/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}`
   - Transfers money to a savings goal

## Future Improvements

- Scheduled execution (daily/weekly round-ups)
- Web UI for monitoring and configuration
- Support for multiple accounts
- Customisable round-up rules
