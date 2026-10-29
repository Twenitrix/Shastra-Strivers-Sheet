# Editorial: Best Time to Buy and Sell Stock with Transaction Fee

## 1. Problem Overview

We are given an array `prices`, where `prices[i]` represents the stock price on the \(i\)-th day, and an integer `fee` representing the transaction fee.

We can buy and sell stocks multiple times, but we must sell a stock before buying another one.

Our objective is to maximize the total profit. A transaction fee is charged whenever we sell a stock.

## 2. Approach: Dynamic Programming

At any point, we can be in one of two states:

- **Buy State:** We are allowed to buy a stock because we currently do not hold one.
- **Sell State:** We currently hold a stock and can either sell it or continue holding it.

We maintain two variables:

- `buy`: Maximum profit when we are allowed to buy a stock.
- `sell`: Maximum profit when we currently hold a stock.

Initially, `buy = 0` and `sell = -prices[0]`.

## 3. State Transitions

For each day's stock price, we have two choices.

### Buy State

We can either skip buying today or sell the stock we are currently holding and become eligible to buy again.

\[
buy = \max(buy,\ sell + price - fee)
\]

### Sell State

We can either continue holding the stock or buy a stock at today's price.

\[
sell = \max(sell,\ buy - price)
\]

To avoid using updated values from the same day, we store the previous values of both states before calculating their next values.

## 4. Algorithm Step-by-Step

1. Initialize `buy = 0` and `sell = -prices[0]`.
2. Traverse the prices array starting from index `1`.
3. Store the previous values of `buy` and `sell`.
4. Update `buy` using the maximum of its previous value and the profit obtained by selling the held stock after paying the fee.
5. Update `sell` using the maximum of its previous value and the profit obtained by buying a stock.
6. After processing all days, return `buy`, which represents the maximum profit when no stock is held.

## 5. Java Implementation

```java
class Solution {
    public int maxProfit(int[] prices, int fee) {
        int buy = 0;
        int sell = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            int prevBuy = buy;
            int prevSell = sell;

            buy = Math.max(prevBuy, prevSell + prices[i] - fee);
            sell = Math.max(prevSell, prevBuy - prices[i]);
        }

        return buy;
    }
}
```

## 6. Dry Run

Consider:

`prices = [1, 3, 2, 8, 4, 9]`

`fee = 2`

| Day | Price | Maximum Profit Without Stock (`buy`) | Maximum Profit With Stock (`sell`) |
|---|---:|---:|---:|
| 0 | 1 | 0 | -1 |
| 1 | 3 | 0 | -1 |
| 2 | 2 | 0 | -1 |
| 3 | 8 | 5 | -1 |
| 4 | 4 | 5 | 1 |
| 5 | 9 | 8 | 1 |

On day 3, selling the stock bought at price `1` gives a profit of `8 - 1 - 2 = 5`.

On day 4, buying again at price `4` allows us to hold a stock while retaining the previous profit of `5`.

On day 5, selling at price `9` gives an additional profit of `9 - 4 - 2 = 3`.

The maximum total profit is:

`5 + 3 = 8`

**Final Answer: `8`**

## 7. Complexity Analysis

### Time Complexity

We traverse the prices array once and perform constant-time operations for each day.

\[
\mathcal{O}(n)
\]

### Space Complexity

We use only a constant number of variables to maintain the DP states.

\[
\mathcal{O}(1)
\]

## 8. Key Takeaways

- Dynamic programming helps track the best profit for each stock-holding state.
- The transaction fee is deducted when selling a stock.
- We can optimize space by maintaining only two state variables instead of a complete DP array.
- The algorithm works in linear time and constant auxiliary space.
